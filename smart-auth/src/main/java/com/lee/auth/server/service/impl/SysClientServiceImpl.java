package com.lee.auth.server.service.impl;

import com.lee.auth.server.domain.SysClient;
import com.lee.auth.server.mapper.SysClientMapper;
import com.lee.auth.server.service.SysClientService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * @author haitao Li
 */
@Service
public class SysClientServiceImpl implements SysClientService {
    @Resource
    private SysClientMapper sysClientMapper;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public List<SysClient> findList() {
        return sysClientMapper.selectList(null);
    }

    @Override
    public SysClient findClientById(String clientId) {
        return sysClientMapper.selectById(clientId);
    }

    @Override
    public Integer createClient(SysClient client) {
        String secret = passwordEncoder.encode(client.getClientSecret());
        client.setClientSecret(secret);
        return sysClientMapper.insert(client);
    }
}
