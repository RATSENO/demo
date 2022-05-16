package org.ratseno.demo.repository;

import java.util.List;

import org.ratseno.demo.domain.CommonCodeDetail;
import org.ratseno.demo.domain.CommonCodeDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommonCodeDetailRepository extends JpaRepository<CommonCodeDetail, CommonCodeDetailId> {

    @Query("SELECT max(cd.sortSeq) FROM CommonCodeDetail cd WHERE cd.groupCode = ?1")
    List<Object[]> getMaxSortSeq(String paramString);

    @Query("SELECT cd FROM CommonCodeDetail cd WHERE cd.groupCode = ?1")
    List<CommonCodeDetail> getCodeList(String paramString);
}
