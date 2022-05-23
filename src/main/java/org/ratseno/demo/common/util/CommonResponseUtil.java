package org.ratseno.demo.common.util;

import java.util.HashMap;
import java.util.Map;

import org.ratseno.demo.common.domain.CommonResponse;
import org.ratseno.demo.common.domain.CommonResponseHeader;

public class CommonResponseUtil {
    public static CommonResponse commonResponseData(MessageUtil messageUtil, String messageId, Object[] argsMessage) {

        String []messageArray = messageUtil.getMessage(messageId, argsMessage).split(";");
        String statusCode = messageArray[0];
        String message = null;
        String description = null;
        if(messageArray.length >= 2) {
            message = messageArray[1];
            if(messageArray.length >= 3) {
                description = messageArray[2];
            }
        }

        CommonResponseHeader CommonResponseHeader = new CommonResponseHeader();
        CommonResponseHeader.setMessage(message);
        CommonResponseHeader.setCode(messageId);
        CommonResponseHeader.setDescription(description);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setHeader(CommonResponseHeader);

        return commonResponse;
    }

    /**
     * pageInfo 가 필요없는 Response Type
     * @param messageUtil
     * @param messageId
     * @param argsMessage
     * @param body
     * @return
     */
    public static CommonResponse commonResponseData(MessageUtil messageUtil, String messageId, Object[] argsMessage, Map<String,Object> body) {

        CommonResponse commonResponse = commonResponseData(messageUtil, messageId, argsMessage);

        if(body == null) {
            body = new HashMap<>();
        }
        commonResponse.setBody(body);
        return commonResponse;
    }    
}
