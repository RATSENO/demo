package org.ratseno.demo.controller;

import java.util.List;

import org.ratseno.demo.domain.CommonCodeDetail;
import org.ratseno.demo.service.CommonCodeDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping({"/codedetails"})
public class CommonCodeDetailController {
    private final static Logger log = LoggerFactory.getLogger(CommonCodeDetailController.class);

    private final CommonCodeDetailService codeDetailService;

    public CommonCodeDetailController(CommonCodeDetailService codeDetailService){
        this.codeDetailService = codeDetailService;
    }

    @GetMapping({"/{groupCode}/{codeValue}"})
    public ResponseEntity<CommonCodeDetail> read(@PathVariable("groupCode") String groupCode, @PathVariable("codeValue") String codeValue) throws Exception{
        CommonCodeDetail codeDetail = new CommonCodeDetail();
        codeDetail.setGroupCode(groupCode);
        codeDetail.setCodeValue(codeValue);
        
        return new ResponseEntity<CommonCodeDetail>(this.codeDetailService.read(codeDetail), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommonCodeDetail>> list() throws Exception{
        return new ResponseEntity<List<CommonCodeDetail>>(this.codeDetailService.list(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommonCodeDetail> register(@Validated @RequestBody CommonCodeDetail codeDetail) throws Exception{
        this.codeDetailService.register(codeDetail);
        return new ResponseEntity<CommonCodeDetail>(codeDetail, HttpStatus.OK);
    }

    @DeleteMapping({"/{groupCode}/{codeValue}"})
    public ResponseEntity<Void> remove(@PathVariable("groupCode")String groupCode, @PathVariable("codeValue") String codeValue) throws Exception{
        CommonCodeDetail codeDetail = new CommonCodeDetail();
        codeDetail.setGroupCode(groupCode);
        codeDetail.setCodeValue(codeValue);
        this.codeDetailService.remove(codeDetail);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<CommonCodeDetail> modify(@Validated @RequestBody CommonCodeDetail codeDetail) throws Exception{
        this.codeDetailService.modify(codeDetail);
        return new ResponseEntity<CommonCodeDetail>(codeDetail, HttpStatus.OK);
    }

}
