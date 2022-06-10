package org.ratseno.demo.common.repository.test;

import org.ratseno.demo.common.domain.test.TestLog;
import org.ratseno.demo.common.domain.test.TestSave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSaveRepository extends JpaRepository<TestSave, Long>{
    
}
