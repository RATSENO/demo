package org.ratseno.demo.controller.test;

import org.ratseno.demo.service.test.TestSaveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/transaction"})
public class TransactionTestController {
    
    private final TestSaveService testSaveService;

    public TransactionTestController(TestSaveService testSaveService){
        this.testSaveService = testSaveService;
    }

    @ApiOperation(
        value = "트랜잭션 테스트1"
        , notes = "자식에서 예외발생, 부모에서 처리")
    @GetMapping({"/1"})
    public void test(){
        testSaveService.saveTest();
    }

    @ApiOperation(
        value = "트랜잭션 테스트2"
        , notes = "부모@Transactional, 자식@Transactional(propagation = Propagation.REQUIRES_NEW) : 자식에서 예외발생, 부모에서 예외 처리 부모의 트랙잭션은 커밋")
    @GetMapping({"/2"})
    public void test2(){
        testSaveService.saveTest2();
    }    

    @ApiOperation(
        value = "트랜잭션 테스트3"
        , notes = "부모@Transactional, 자식@Transactional(propagation = Propagation.REQUIRES_NEW) : 부모에서 예외 발생, 부모 롤백 자식 커밋")
    @GetMapping({"/3"})
    public void test3(){
        testSaveService.saveTest3();
    } 
}
