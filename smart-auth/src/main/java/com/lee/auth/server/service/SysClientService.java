package com.lee.auth.server.service;

import com.lee.auth.server.domain.SysClient;

import java.util.List;

/**
 * @author haitao Li
 */
public interface SysClientService {
    /**
     * 查询出所有的client
     * @return
     */
    List<SysClient> findList();

    /**
     * 通过ID查询单个client
     * @param clientId
     * @return
     */
    SysClient findClientById(String clientId);

    /**
     * 创建Client
     * @param client
     * @return
     */
    Integer createClient(SysClient client);
}
