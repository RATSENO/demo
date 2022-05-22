package org.ratseno.demo.controller;

import java.util.List;

import org.ratseno.demo.domain.CommonCodeGroup;
import org.ratseno.demo.service.CommonCodeGroupService;
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
@RequestMapping({"/codegroups"})
public class CommonCodeGroupController {
    private static final Logger log = LoggerFactory.getLogger(CommonCodeGroupController.class);

    private final CommonCodeGroupService codeGroupService;

    public CommonCodeGroupController(CommonCodeGroupService codeGroupService){
        this.codeGroupService = codeGroupService;
    }

    @GetMapping
    public ResponseEntity<List<CommonCodeGroup>> list()throws Exception{
        return new ResponseEntity<List<CommonCodeGroup>>(this.codeGroupService.list(), HttpStatus.OK);
    }


    @GetMapping({"/{groupCode}"})
    public ResponseEntity<CommonCodeGroup> read(@PathVariable("groupCode") String groupCode) throws Exception{
        return new ResponseEntity<CommonCodeGroup>(this.codeGroupService.read(groupCode), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommonCodeGroup> register(@Validated @RequestBody CommonCodeGroup codeGroup) throws Exception{
        this.codeGroupService.register(codeGroup);
        return new ResponseEntity<CommonCodeGroup>(codeGroup, HttpStatus.OK);
    }

    @DeleteMapping({"/{groupCode}"})
    public ResponseEntity<Void> remove(@PathVariable("groupCode") String groupCode) throws Exception{
        this.codeGroupService.remove(groupCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping()
    public ResponseEntity<CommonCodeGroup> modify(@Validated @RequestBody CommonCodeGroup codeGroup) throws Exception{
        this.codeGroupService.modify(codeGroup);
        return new ResponseEntity<CommonCodeGroup>(codeGroup, HttpStatus.OK);
    }
}
