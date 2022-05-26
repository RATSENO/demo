package org.ratseno.demo.controller;

import org.ratseno.demo.common.util.RestTemplateUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/rest"})
public class RestTemplateTestController {
    
    private final RestTemplateUtil restTemplateUtil;

    public RestTemplateTestController(RestTemplateUtil restTemplateUtil){
        this.restTemplateUtil = restTemplateUtil;
    }

    @GetMapping("/sample/get")
    public ResponseEntity<?> getTest(){
        String url = "https://jsonplaceholder.typicode.com/posts";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> responseEntity = restTemplateUtil.sendGET(url, headers, "");

        return new ResponseEntity(responseEntity.getBody(), HttpStatus.OK);
    }
}
