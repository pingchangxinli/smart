package com.lee;

import com.lee.common.core.Contants;
import com.lee.common.security.annotation.EnableSmartResourceServer;
import com.lee.domain.Tenant;
import com.lee.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

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
@EnableSmartResourceServer
public class SmartUserApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SmartUserApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartUserApplication.class, args);
    }

}

/**
 * 服务启动完毕将租户信息加载进redis
 */
@Slf4j
@Component
class TenantDomainInit implements ApplicationRunner {
    @Resource
    private TenantService service;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void run(ApplicationArguments args){
        List<Tenant> list = service.list();
        if (CollectionUtils.isNotEmpty(list)) {
            log.info("get " +list.size()+ " tenants from database");
            Map<String,Tenant> map = new HashMap<>();
            list.forEach(tenant -> map.put(tenant.getDomain(),tenant));
            if (log.isDebugEnabled()) {
                log.debug("Contants.TENANT_KEY:" +Contants.TENANT_KEY);
            }
            redisTemplate.opsForHash().putAll(Contants.TENANT_KEY,map);
        }

    }
}
