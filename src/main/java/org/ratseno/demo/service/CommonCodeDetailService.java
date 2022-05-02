package org.ratseno.demo.service;

import java.util.List;

import org.ratseno.demo.domain.CommonCodeDetail;

public interface CommonCodeDetailService {
    public void register(CommonCodeDetail commonCodeDetail) throws Exception;

    public void modify(CommonCodeDetail commonCodeDetail) throws Exception;

    public void remove(CommonCodeDetail commonCodeDetail) throws Exception;

    public CommonCodeDetail read(CommonCodeDetail commonCodeDetail) throws Exception;

    public List<CommonCodeDetail> list() throws Exception;
}
