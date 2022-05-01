package org.ratseno.demo.common.service.impl;

import java.util.List;

import org.ratseno.demo.common.domain.AccessLog;
import org.ratseno.demo.common.repository.AccessLogRepository;
import org.ratseno.demo.common.service.AccessLogService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository repository;

    public AccessLogServiceImpl(AccessLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void register(AccessLog accessLog) throws Exception {
        this.repository.save(accessLog);
    }

    @Override
    public List<AccessLog> list() throws Exception {
        return this.repository.findAll(Sort.by(Sort.Direction.DESC, "logNo"));
    }

}
