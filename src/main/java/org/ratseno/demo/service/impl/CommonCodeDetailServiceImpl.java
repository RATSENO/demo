package org.ratseno.demo.service.impl;

import java.util.List;

import org.ratseno.demo.domain.CommonCodeDetail;
import org.ratseno.demo.domain.CommonCodeDetailId;
import org.ratseno.demo.repository.CommonCodeDetailRepository;
import org.ratseno.demo.service.CommonCodeDetailService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommonCodeDetailServiceImpl implements CommonCodeDetailService {

    private final CommonCodeDetailRepository codeDetailRepository;

    public CommonCodeDetailServiceImpl(CommonCodeDetailRepository codeDetailRepository) {
        this.codeDetailRepository = codeDetailRepository;
    }

    @Override
    public List<CommonCodeDetail> list() throws Exception {
        return this.codeDetailRepository.findAll(Sort.by(Sort.Direction.ASC, "groupCode", "codeValue"));
    }

    @Override
    public void modify(CommonCodeDetail commonCodeDetail) throws Exception {
        CommonCodeDetailId commonCodeDetailId = new CommonCodeDetailId();
        commonCodeDetail.setGroupCode(commonCodeDetail.getGroupCode());
        commonCodeDetail.setCodeValue(commonCodeDetail.getCodeValue());

        CommonCodeDetail codeDetail = this.codeDetailRepository.getById(commonCodeDetailId);
        codeDetail.setCodeValue(commonCodeDetail.getCodeValue());
        codeDetail.setCodeName(commonCodeDetail.getCodeName());

        this.codeDetailRepository.save(codeDetail);
    }

    @Override
    public CommonCodeDetail read(CommonCodeDetail commonCodeDetail) throws Exception {
        CommonCodeDetailId codeDetailId = new CommonCodeDetailId();
        codeDetailId.setGroupCode(commonCodeDetail.getGroupCode());
        codeDetailId.setCodeValue(commonCodeDetail.getCodeValue());

        return this.codeDetailRepository.getById(codeDetailId);
    }

    @Override
    public void register(CommonCodeDetail commonCodeDetail) throws Exception {
        String groupCode = commonCodeDetail.getGroupCode();

        List<Object[]> rsList = this.codeDetailRepository.getMaxSortSeq(groupCode);

        Integer maxSortSeq = Integer.valueOf(0);

        if (rsList.size() > 0) {
            Object[] maxValues = rsList.get(0);
            if (maxValues != null && maxValues.length > 0) {
                maxSortSeq = (Integer) maxValues[0];
            }
        }
        commonCodeDetail.setSortSeq(maxSortSeq.intValue() + 1);

        this.codeDetailRepository.save(commonCodeDetail);
    }

    @Override
    public void remove(CommonCodeDetail commonCodeDetail) throws Exception {
        CommonCodeDetailId codeDetailId = new CommonCodeDetailId();
        codeDetailId.setGroupCode(commonCodeDetail.getGroupCode());
        codeDetailId.setCodeValue(commonCodeDetail.getCodeValue());

        this.codeDetailRepository.deleteById(codeDetailId);
    }
}
