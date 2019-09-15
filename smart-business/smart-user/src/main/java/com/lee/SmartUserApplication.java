package com.lee;

import com.lee.common.core.Contants;
import com.lee.tenant.domain.Tenant;
import com.lee.tenant.service.TenantService;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lee
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@MapperScan({"com.lee.*.mapper"})
public class SmartUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartUserApplication.class, args);
    }

}

/**
 * 服务启动完毕将租户信息加载进redis
 */
@Component
class TenantDomainInit implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(TenantDomainInit.class);
    @Resource
    private TenantService service;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void run(ApplicationArguments args){
        List<Tenant> list = service.list();
        logger.info("get {} tenants from database",list.size());
        Map<String,Tenant> map = new HashMap<>();
        list.forEach(tenant -> map.put(tenant.getDomain(),tenant));
        redisTemplate.opsForHash().putAll(Contants.TENANT_KEY,map);
    }
}
