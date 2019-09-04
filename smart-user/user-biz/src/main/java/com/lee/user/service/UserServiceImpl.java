package com.lee.user.service;

import com.lee.user.domain.User;
import com.lee.user.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author haitao.li
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public void createUser(User user) {
        userMapper.createUser(user);
    }

    @Override
    public User loadUserByUserIdAndSiteId(String userId, Integer siteId) {
        return userMapper.loadUserByUserIdAndSiteId(userId,siteId);
    }
}
