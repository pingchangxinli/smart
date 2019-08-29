package com.lee.uid.provider;

import com.lee.uid.provider.service.UidService;
import org.apache.ibatis.annotations.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haitao.li
 */
@RestController
public class UidGeneratorApi {
    private static final Logger loggger = LoggerFactory.getLogger(UidGeneratorApi.class);
    @Autowired
    private UidService uidService;
    @GetMapping("/uid")
    public Long getUid(){
        Long uid =  uidService.getUid();
        loggger.info("[UID GENERTOR API],getUid:{}",uid);
        return uid;
    }
}
