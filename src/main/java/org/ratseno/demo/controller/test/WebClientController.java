package org.ratseno.demo.controller.test;

import java.util.List;

import org.ratseno.demo.domain.test.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping({"/webclient"})
public class WebClientController {

    private final WebClient webClient;

    public WebClientController(WebClient webClient){
        this.webClient = webClient;
    }

    @GetMapping("/sample/get")
    public Mono<String> getTest(){
        String url = "https://jsonplaceholder.typicode.com/posts";

        return webClient.get().uri(url).retrieve().bodyToMono(String.class);
    }

    @GetMapping("/sample/get2")
    public Mono<ResponseEntity<List<Test>>> getTest2(){
        String url = "https://jsonplaceholder.typicode.com/posts";

        return webClient.get().uri(url).retrieve().toEntityList(Test.class);
    }

    @GetMapping("/sample/get3/{id}")
    public Mono<String> getTest3(@PathVariable(name = "id") Long id){
        String url = "https://jsonplaceholder.typicode.com/posts";

        return webClient.get()
                        .uri(url+"/{id}", id)
                        .retrieve()
                        .bodyToMono(String.class);
    }    
}
