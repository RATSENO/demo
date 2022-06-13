package org.ratseno.demo.common.repository.test;

import org.ratseno.demo.common.domain.test.TestPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestPostRepository extends JpaRepository<TestPost, Long>{
    
}
