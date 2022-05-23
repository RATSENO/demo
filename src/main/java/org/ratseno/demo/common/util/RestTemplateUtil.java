package org.ratseno.demo.common.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.ratseno.demo.common.constant.CodeConstants;
import org.ratseno.demo.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.swagger.models.HttpMethod;

@Component
public class RestTemplateUtil {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateUtil.class);

    private final ObjectMapper objectMapper;

    public RestTemplateUtil(){
        this.objectMapper = new ObjectMapper();
    }
    
    /* 
    public ResponseEntity<String> sendGET(String url, HttpHeaders headers, HttpMethod method, String payload){
        this.validateParam(url, headers, method, HttpMethod.GET, payload);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> responseEntity = null;

        Map<String, Object> map = new HashMap<>();
    }
     */

    /* 
    public ResponseEntity<String> sendPOST(String url, HttpHeaders headers, HttpMethod method, String payload){
        this.validateParam(url, headers, method, HttpMethod.POST, payload);
    }

    public ResponseEntity<String> sendFORM(String url, HttpHeaders headers, HttpMethod method, String payload){
        this.validateParam(url, headers, method, HttpMethod.POST, payload);
    }
     */
    private void validateParam(String url, HttpHeaders headers, HttpMethod requestMethod, HttpMethod tagetMethod, String payload){
        if(StringUtil.isEmpty(url)){
            throw new CommonException(CodeConstants.REQUIRED_PARAMETER_ERROR, new String[]{"url"});
        }
        if(headers==null){
            throw new CommonException(CodeConstants.REQUIRED_PARAMETER_ERROR, new String[]{"header"});
        }
        if(requestMethod==null){
            throw new CommonException(CodeConstants.REQUIRED_PARAMETER_ERROR, new String[]{"method"});
        }
        if(requestMethod!=null){
            if(!requestMethod.name().equals(tagetMethod.name())){
                throw new CommonException(CodeConstants.REQUIRED_PARAMETER_ERROR, new String[]{"method"});
            }
        }
        if(StringUtil.isEmpty(payload)){
            throw new CommonException(CodeConstants.REQUIRED_PARAMETER_ERROR, new String[]{"payload"});
        }
        if(!StringUtil.isEmpty(payload)){
            try {
                objectMapper.readValue(payload, new TypeReference<Map<Object, Object>>(){});
            } catch (Exception e) {
                log.info("RestTemplateUtil.message={}", e.getMessage());
                throw new CommonException(CodeConstants.JSON_STR_PARSE_ERROR, new String[]{"payload"});
            }
        }
    }
}
