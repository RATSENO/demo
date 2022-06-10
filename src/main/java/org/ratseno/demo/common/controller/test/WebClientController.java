package org.ratseno.demo.common.controller.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ratseno.demo.common.domain.test.Test;
import org.ratseno.demo.common.domain.test.TestPost;
import org.ratseno.demo.common.exception.CommonException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @GetMapping("/sample/get3_1/{id}")
    public Mono<ResponseEntity<String>> getTest3_1(@PathVariable(name = "id") Long id){
        String url = "https://jsonplaceholder.typicode.com/posts";

        return webClient.get()
                        .uri(url+"/{id}", id)
                        .retrieve()
                        .toEntity(String.class);
    }
    
    @GetMapping("/sample/get4/{id}")
    public Mono<String> getTest4(@PathVariable(name = "id") Long id){
        String url = "https://jsonplaceholder.typicode.com";
        String uri = "/post/{id}?test={test}&test2={test2}";

        Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("id", "1");
        queryParamMap.put("test", "test1");
        queryParamMap.put("test2", "test2");

        return webClient.mutate()
                        .baseUrl(url)
                        .build()
                        .get()
                        .uri(uri, queryParamMap)
                        .retrieve()
                        .onStatus(HttpStatus::isError, clientResponse->clientResponse.bodyToMono(String.class).map(body->new CommonException("500", new String[]{"1111", "2222"})))
                        .bodyToMono(String.class);
    }

    @GetMapping("/sample/get5/{id}")
    public Mono<String> getTest5(@PathVariable(name = "id") Long id){
        String url = "https://jsonplaceholder.typicode.com";
        String uri = "/posts/{id}?test={test}&test2={test2}";

        Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("id", "1");
        queryParamMap.put("test", "test1");
        queryParamMap.put("test2", "test2");

        return webClient.mutate()
                        .baseUrl(url)
                        .build()
                        .get()
                        .uri(uri, queryParamMap)
                        .retrieve()
                        .bodyToMono(String.class);
    }    

    @ApiOperation(value = "WebClient POST 요청", notes = "WebClient를 이용하여 POST요청 테스트, REQUEST BODY에 JSON 데이터")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "500", description = "서버에러")
    })
    @PostMapping("/sample/post")
    public Mono<TestPost> postTest(@RequestBody TestPost post){
        String url = "https://jsonplaceholder.typicode.com";
        String uri = "/posts";

        return webClient.mutate()
                        .baseUrl(url)
                        .build()
                        .post()
                        .uri(uri)
                        .bodyValue(post)
                        .retrieve()
                        .bodyToMono(TestPost.class);
    }

}
