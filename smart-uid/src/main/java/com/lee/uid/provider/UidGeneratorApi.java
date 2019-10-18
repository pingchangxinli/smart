package com.lee.uid.provider;

import com.lee.uid.provider.service.UidService;
import org.apache.ibatis.annotations.ResultType;
import org.slf4j.log;
import org.slf4j.logFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lee.li
 */
@RestController
public class UidGeneratorApi {
    private static final log loggger = logFactory.getlog(UidGeneratorApi.class);
    @Autowired
    private UidService uidService;
    @GetMapping("/uid")
    public Long getUid(){
        Long uid =  uidService.getUid();
        loggger.info("[UID GENERTOR API],getUid:{}",uid);
        return uid;
    }
}
