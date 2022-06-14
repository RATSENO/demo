package org.ratseno.demo.common.controller.test;

import javax.validation.Valid;

import org.ratseno.demo.common.domain.CommonResponse;
import org.ratseno.demo.common.domain.test.TestPost;
import org.ratseno.demo.common.exception.CommonException;
import org.ratseno.demo.common.util.CommonResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping({"/commonresponse2"})
    public CommonResponse commonresponse2(){
        return commonResponseUtil.commonResponseData("4000", new String[]{"test1", "test2"});
    }    

    @GetMapping({"/common"})
    public CommonResponse common(){

        if(true){
            throw new CommonException("9000", new String[]{"test1"});
        }

        return commonResponseUtil.commonResponseData("2000", new String[]{});
    }

    @PostMapping({"/valid"})
    public CommonResponse valid(@Valid @RequestBody TestPost testPost){
        return commonResponseUtil.commonResponseData("2000", new String[]{});
    }    
}
