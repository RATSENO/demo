package org.ratseno.demo.service.test.impl;

import org.ratseno.demo.domain.test.TestLog;
import org.ratseno.demo.repository.test.TestLogRepository;
import org.ratseno.demo.service.test.TestLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestLogServiceImpl implements TestLogService {

    private final TestLogRepository testLogRepository;

    public TestLogServiceImpl(TestLogRepository testLogRepository) {
        this.testLogRepository = testLogRepository;
    }

    @Transactional
    @Override
    public void saveLog() {
        TestLog testLog = new TestLog();
        testLog.setLogText("로그 저장");
        testLogRepository.save(testLog);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveLog2() {
        TestLog testLog = new TestLog();
        testLog.setLogText("로그 저장");
        testLogRepository.save(testLog);

        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveLog3() {
        TestLog testLog = new TestLog();
        testLog.setLogText("로그 저장");
        testLogRepository.save(testLog);
    }
}
