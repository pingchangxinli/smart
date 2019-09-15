package com.lee.common.business.config.tenant;

import com.lee.common.core.Contants;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 租户配置文件,配置租户拦截器,
 * @author haitao.li
 */
public class  TenantConfig extends WebMvcConfigurerAdapter {
    private final String[] excludePathPatterns = new String[]{"/tenant/**"};
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new TenantInterceptor()).excludePathPatterns(excludePathPatterns);
        super.addInterceptors(registry);
    }
}

class TenantInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TenantInterceptor.class);
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean superResult = super.preHandle(request, response, handler);
        if(!superResult) {
            return false;
        }
        String tenantId = request.getHeader(Contants.REQUEST_HEADER_TENANT_ID);
        logger.debug("tenant interceptor,get tenant id from request header:{}",tenantId);
        if (StringUtils.isEmpty(tenantId)) {
            BaseResponse baseResponse = BaseResponse.builder().subCode(String.valueOf(HttpStatus.BAD_REQUEST)).subMsg(
                    "无法获取租户信息").build();
            String baseResponseJson = JsonUtil.toJson(baseResponse);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.getOutputStream().write(baseResponseJson.getBytes("UTF-8"));
            return false;
        } else {
            String threadName = Thread.currentThread().getName();
            String key = Contants.THREAD_DOMAIN_KEY + threadName;
            redisTemplate.opsForValue().set(key,tenantId);
            logger.debug("put redis from request header,tenant id is :{}",redisTemplate.opsForValue().get(key));
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
