package org.ratseno.demo.common.service;

import java.util.List;

import org.ratseno.demo.common.domain.AccessLog;

public interface AccessLogService {
    public void register(AccessLog paramAccessLog) throws Exception;

    public List<AccessLog> list() throws Exception;
}
