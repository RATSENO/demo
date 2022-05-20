package org.ratseno.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.classfile.Code;
import org.ratseno.demo.domain.CommonCodeDetail;
import org.ratseno.demo.domain.CommonCodeGroup;
import org.ratseno.demo.dto.CodeLabelValue;
import org.ratseno.demo.repository.CommonCodeDetailRepository;
import org.ratseno.demo.repository.CommonCodeGroupRepository;
import org.ratseno.demo.service.CommonCodeSerivce;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CommonCodeSerivceImpl implements CommonCodeSerivce{

    private final CommonCodeGroupRepository codeGroupRepository;

    private final CommonCodeDetailRepository codeDetailRepository;

    public CommonCodeSerivceImpl(CommonCodeGroupRepository codeGroupRepository, CommonCodeDetailRepository codeDetailRepository){
        this.codeGroupRepository = codeGroupRepository;
        this.codeDetailRepository = codeDetailRepository;
    }

    @Override
    public List<CodeLabelValue> getCodeGroupList() throws Exception {
        List<CommonCodeGroup> codeGroups = this.codeGroupRepository.findAll(Sort.by(Direction.ASC, "groupCode"));
        List<CodeLabelValue> codeGroupList = new ArrayList<>();
        for (CommonCodeGroup commonCodeGroup : codeGroups) {
            codeGroupList.add(new CodeLabelValue(commonCodeGroup.getGroupCode(), commonCodeGroup.getGroupName()));
        }
        return codeGroupList;
    }

    @Override
    public List<CodeLabelValue> getCodeList(String groupCode) throws Exception {
        List<CommonCodeDetail> codeDetails = this.codeDetailRepository.getCodeList(groupCode);
        List<CodeLabelValue> codeList = new ArrayList<>();
        for (CommonCodeDetail commonCodeDetail : codeDetails) {
            codeList.add(new CodeLabelValue(commonCodeDetail.getCodeValue(), commonCodeDetail.getCodeName()));
        }
        return codeList;
    }
}
