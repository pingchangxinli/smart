package com.lee.auth.server.service.impl;

import com.lee.auth.server.domain.SysClient;
import com.lee.auth.server.mapper.SysClientMapper;
import com.lee.auth.server.service.SysClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haitao.li
 */
@Service
public class SysClientServiceImpl implements SysClientService {
    @Resource
    private SysClientMapper sysClientMapper;
    @Override
    public List<SysClient> findList() {
        return sysClientMapper.selectList(null);
    }

    @Override
    public SysClient findClientById(String clientId) {
        return sysClientMapper.selectById(clientId);
    }
}
