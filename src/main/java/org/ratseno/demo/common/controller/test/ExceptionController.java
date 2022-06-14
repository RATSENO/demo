package org.ratseno.demo.common.controller.test;

import org.ratseno.demo.common.domain.CommonResponse;
import org.ratseno.demo.common.util.CommonResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/exception"})
public class ExceptionController {
    private final CommonResponseUtil commonResponseUtil;

    public ExceptionController(CommonResponseUtil commonResponseUtil){
        this.commonResponseUtil = commonResponseUtil;
    }

    @GetMapping({"/commonresponse"})
    public CommonResponse commonresponse(){
        return commonResponseUtil.commonResponseData("2000", new String[]{});
    }
}
