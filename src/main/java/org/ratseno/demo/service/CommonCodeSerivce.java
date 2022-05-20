package org.ratseno.demo.service;

import java.util.List;

import org.ratseno.demo.dto.CodeLabelValue;

public interface CommonCodeSerivce {
    public List<CodeLabelValue> getCodeGroupList() throws Exception;

    public List<CodeLabelValue> getCodeList(String groupCode) throws Exception;
}
