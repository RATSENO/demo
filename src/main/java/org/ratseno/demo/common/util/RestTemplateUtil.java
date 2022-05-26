package org.ratseno.demo.common.util;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.ratseno.demo.common.constant.CodeConstants;
import org.ratseno.demo.common.exception.CommonException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestTemplateUtil {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public RestTemplateUtil(RestTemplate restTemplate){
        this.objectMapper = new ObjectMapper();
        this.restTemplate = restTemplate;
    }

    /**
     * GET 요청
     *    
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * @param url
     * @param headers
     * @param method
     * @param payload
     * @return
     */
    public ResponseEntity<String> sendGET(String url, HttpHeaders headers, String payload){
        this.validateParam(url, headers, payload);

        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> responseEntity = null;

        responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        log.info("ok:responseBody="+responseEntity.getBody());

        return responseEntity;
    }

    /**
     * POST 요청
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * @param url
     * @param headers
     * @param method
     * @param payload
     * @return
     */
    public ResponseEntity<String> sendPOST(String url, HttpHeaders headers, String payload){
        this.validateParam(url, headers, payload);

        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> responseEntity = null;

        responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.info("ok:responseBody="+responseEntity.getBody());

        return responseEntity;        
    }

    /**
     * POST(form) 요청
     * 
     * 
     * @param url
     * @param headers
     * @param method
     * @param payload
     * @return:
     */    
    public ResponseEntity<String> sendFORM(String url, HttpHeaders headers, HttpMethod method, MultiValueMap<String, String> map){
        this.validateParam(url, headers);

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = null;

        responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.info("ok:responseBody="+responseEntity.getBody());

        return responseEntity; 
    }

    /**
     * 
     * 
     * 
     * 
     * @param url
     * @param headers
     * @param payload
     */
    private void validateParam(String url, HttpHeaders headers, String payload){
        if(StringUtil.isEmpty(url)){
            throw new CommonException(CodeConstants.REQUIRED_PARAMETER_ERROR, new String[]{"url"});
        }
        if(headers==null){
            throw new CommonException(CodeConstants.REQUIRED_PARAMETER_ERROR, new String[]{"header"});
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

    private void validateParam(String url, HttpHeaders headers){
        if(StringUtil.isEmpty(url)){
            throw new CommonException(CodeConstants.REQUIRED_PARAMETER_ERROR, new String[]{"url"});
        }
        if(headers==null){
            throw new CommonException(CodeConstants.REQUIRED_PARAMETER_ERROR, new String[]{"header"});
        }
    }    
}
