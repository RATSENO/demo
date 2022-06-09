package org.ratseno.demo.controller.test;

import org.ratseno.demo.service.test.TestSaveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/transaction"})
public class TransactionTestController {
    
    private final TestSaveService testSaveService;

    public TransactionTestController(TestSaveService testSaveService){
        this.testSaveService = testSaveService;
    }

    @GetMapping({"/required"})
    public void test(){
        testSaveService.saveTest();
    }
}
