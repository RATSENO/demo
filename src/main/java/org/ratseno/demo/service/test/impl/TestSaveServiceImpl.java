package org.ratseno.demo.service.test.impl;

import org.ratseno.demo.domain.test.TestSave;
import org.ratseno.demo.repository.test.TestSaveRepository;
import org.ratseno.demo.service.test.TestLogService;
import org.ratseno.demo.service.test.TestSaveService;
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
        try {
            TestSave testSave = new TestSave();
            testSave.setText("테스트");
            testSaveRepository.save(testSave);
            testLogService.saveLog();    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
