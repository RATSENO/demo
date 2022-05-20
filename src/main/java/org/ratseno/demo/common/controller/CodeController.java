package org.ratseno.demo.common.controller;

import java.util.List;

import org.ratseno.demo.dto.CodeLabelValue;
import org.ratseno.demo.service.CommonCodeSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/codes"})
public class CodeController {
    private static final Logger log = LoggerFactory.getLogger(CodeController.class);

    private final CommonCodeSerivce codeSerivce;

    public CodeController(CommonCodeSerivce codeSerivce){
        this.codeSerivce = codeSerivce;
    }

    @GetMapping({"/codegroup"})
    public ResponseEntity<List<CodeLabelValue>> codeGroupList() throws Exception{
        log.info("===codeGroupList===");
        return new ResponseEntity<List<CodeLabelValue>>(this.codeSerivce.getCodeGroupList(), HttpStatus.OK);
    }

    @GetMapping({"/codeList/{groupCode}"})
    public ResponseEntity<List<CodeLabelValue>> codeList(@PathVariable("groupCode") String groupCode) throws Exception{
        log.info("===codeList===");
        return new ResponseEntity<List<CodeLabelValue>>(this.codeSerivce.getCodeList(groupCode), HttpStatus.OK);
    }
}
