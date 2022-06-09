package org.ratseno.demo.repository.test;

import org.ratseno.demo.domain.test.TestLog;
import org.ratseno.demo.domain.test.TestSave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSaveRepository extends JpaRepository<TestSave, Long>{
    
}
