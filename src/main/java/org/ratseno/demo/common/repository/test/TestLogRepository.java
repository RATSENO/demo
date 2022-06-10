package org.ratseno.demo.common.repository.test;

import org.ratseno.demo.common.domain.test.TestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestLogRepository extends JpaRepository<TestLog, Long>{
    
}
