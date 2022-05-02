package org.ratseno.demo.service;

import java.util.List;

import org.ratseno.demo.domain.CommonCodeGroup;

public interface CommonCodeGroupService {
    public void register(CommonCodeGroup commonCodeGroup) throws Exception;

    public void modify(CommonCodeGroup commonCodeGroup) throws Exception;

    public void remove(String groupCode) throws Exception;

    public CommonCodeGroup read(String groupCode) throws Exception;

    public List<CommonCodeGroup> list() throws Exception;
}
