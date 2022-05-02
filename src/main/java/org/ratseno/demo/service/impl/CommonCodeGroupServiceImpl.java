package org.ratseno.demo.service.impl;

import java.util.List;

import org.ratseno.demo.domain.CommonCodeGroup;
import org.ratseno.demo.repository.CommonCodeGroupRepository;
import org.ratseno.demo.service.CommonCodeGroupService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommonCodeGroupServiceImpl implements CommonCodeGroupService {

    private final CommonCodeGroupRepository codeGroupRepository;

    public CommonCodeGroupServiceImpl(CommonCodeGroupRepository codeGroupRepository) {
        this.codeGroupRepository = codeGroupRepository;
    }

    @Override
    public void register(CommonCodeGroup commonCodeGroup) throws Exception {
        this.codeGroupRepository.save(commonCodeGroup);
    }

    @Override
    public void modify(CommonCodeGroup commonCodeGroup) throws Exception {
        CommonCodeGroup codeGroupEntity = (CommonCodeGroup) this.codeGroupRepository
                .getById(commonCodeGroup.getGroupCode());
        codeGroupEntity.setGroupName(commonCodeGroup.getGroupName());
        this.codeGroupRepository.save(codeGroupEntity);
    }

    @Override
    public void remove(String groupCode) throws Exception {
        this.codeGroupRepository.deleteById(groupCode);
    }

    @Override
    public CommonCodeGroup read(String groupCode) throws Exception {
        return this.codeGroupRepository.getById(groupCode);
    }

    @Override
    public List<CommonCodeGroup> list() throws Exception {
        return this.codeGroupRepository.findAll(Sort.by(Sort.Direction.DESC, new String[] { "groupCode" }));
    }
}
