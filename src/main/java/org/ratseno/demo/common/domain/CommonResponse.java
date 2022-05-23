package org.ratseno.demo.common.domain;

import java.util.Map;

public class CommonResponse {
    private static final long serialVersionUID = 1638663586857148668L;

    private CommonResponseHeader header;
    private Map<String,Object> body;

    public CommonResponseHeader getHeader() {
        return header;
    }

    public void setHeader(CommonResponseHeader header) {
        this.header = header;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}
