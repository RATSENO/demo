package org.ratseno.demo.common.service.test.impl;

import org.ratseno.demo.common.domain.test.TestSave;
import org.ratseno.demo.common.repository.test.TestSaveRepository;
import org.ratseno.demo.common.service.test.TestLogService;
import org.ratseno.demo.common.service.test.TestSaveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestSaveServiceImpl implements TestSaveService{

    private final TestSaveRepository testSaveRepository;

    private final TestLogService testLogService;

    public TestSaveServiceImpl(TestSaveRepository testSaveRepository, TestLogService testLogService){
        this.testSaveRepository = testSaveRepository;
        this.testLogService = testLogService;
    }

    @Transactional
    @Override
    public void saveTest() {
        TestSave testSave = new TestSave();
        testSave.setText("테스트");
        testSaveRepository.save(testSave);

        //자식
        testLogService.saveLog();    

        throw new RuntimeException();
    }

    @Transactional
    @Override
    public void saveTest2() {
        //부모에서 예외처리
        try {
            TestSave testSave = new TestSave();
            testSave.setText("테스트");
            testSaveRepository.save(testSave);

            //자식 - @Transactional(propagation = Propagation.REQUIRES_NEW)
            testLogService.saveLog2();    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
    
    @Transactional
    @Override
    public void saveTest3() {
        TestSave testSave = new TestSave();
        testSave.setText("테스트");
        testSaveRepository.save(testSave);

        //자식 - @Transactional(propagation = Propagation.REQUIRES_NEW)
        testLogService.saveLog3();    

        //부모에서 예외 발생
        throw new RuntimeException();
    }      
}
