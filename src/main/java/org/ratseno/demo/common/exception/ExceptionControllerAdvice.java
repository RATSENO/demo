package org.ratseno.demo.common.exception;

import javax.servlet.http.HttpServletResponse;

import org.ratseno.demo.common.domain.CommonResponse;
import org.ratseno.demo.common.util.CommonResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    private final CommonResponseUtil commonResponseUtil;

    public ExceptionControllerAdvice(CommonResponseUtil commonResponseUtil){
        this.commonResponseUtil = commonResponseUtil;
    }

    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody CommonResponse defaultException(HttpServletResponse response, CommonException e) {
        return commonResponseUtil.commonResponseData(e.getCode(), e.getArgs());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody CommonResponse handleMethodArgumentNotValidException(HttpServletResponse response, MethodArgumentNotValidException e) {
        String message = "";

        String field = "";
        String defaultMessage = "";
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) error;
            field = fieldError.getField();
            defaultMessage = fieldError.getDefaultMessage();
            
            message = field + "/" + defaultMessage;
            break;
        }

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode("4001");
        commonResponse.setMessage(message);

        return commonResponse;
    }
}
