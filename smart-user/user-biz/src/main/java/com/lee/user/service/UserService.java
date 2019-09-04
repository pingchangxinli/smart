package com.lee.user.service;

import com.lee.user.domain.User;

/**
 * @author haitao.li
 */
public interface UserService {
    /**
     * 新增用户
     * @param user 用户信息
     */
    void createUser(User user);

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @param siteId 站点ID
     * @return
     */
    User loadUserByUserIdAndSiteId(String userId, Integer siteId);
}
