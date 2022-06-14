package org.ratseno.demo.common.util;

import java.util.HashMap;
import java.util.Map;

import org.ratseno.demo.common.domain.CommonResponse;
import org.springframework.stereotype.Component;
@Component
public class CommonResponseUtil {

    private final MessageUtil messageUtil;

    public CommonResponseUtil(MessageUtil messageUtil){
        this.messageUtil = messageUtil;
    }

    /**
     * @param messageUtil
     * @param code
     * @param argsMessage
     * @return
     */
    public CommonResponse commonResponseData(String code, Object[] argsMessage) {

        String message = messageUtil.getMessage(code, argsMessage);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(code);
        commonResponse.setMessage(message);

        return commonResponse;
    }

    /**
     * @param messageUtil
     * @param code
     * @param argsMessage
     * @param body
     * @return
     */
    public CommonResponse commonResponseData(String code, Object[] argsMessage, Map<String,Object> body) {

        CommonResponse commonResponse = this.commonResponseData(code, argsMessage);

        if(body == null) {
            body = new HashMap<>();
        }
        commonResponse.setBody(body);
        return commonResponse;
    }    
}
