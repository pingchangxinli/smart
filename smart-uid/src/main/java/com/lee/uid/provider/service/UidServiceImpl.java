package com.lee.uid.provider.service;

import com.baidu.fsg.uid.UidGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lee.li
 */
@Service
public class UidServiceImpl implements UidService {
    @Resource
    private UidGenerator uidGenerator;
    @Override
    public Long getUid() {
        return uidGenerator.getUID();
    }
}
