package org.ratseno.demo.repository.test;

import org.ratseno.demo.domain.test.TestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestLogRepository extends JpaRepository<TestLog, Long>{
    
}
