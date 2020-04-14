package com.lee.common.business.config.tenant;

import com.lee.common.core.Contants;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 *
 * @author haitao Li
 */
@Slf4j
public class TenantConfig extends WebMvcConfigurerAdapter {
    private final String[] excludePathPatterns = new String[]{"/tenant/**"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new TenantInterceptor()).excludePathPatterns(excludePathPatterns);
        super.addInterceptors(registry);
    }
}

@Slf4j
class TenantInterceptor extends HandlerInterceptorAdapter {
    private static final String NOT_FOUND_TENANT_INFO = "租户信息缺失";
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean superResult = super.preHandle(request, response, handler);
        if (!superResult) {
            return false;
        }
        String tenantId = request.getHeader(Contants.REQUEST_HEADER_TENANT_ID);
        if (log.isDebugEnabled()) {
            log.debug("[TenantConfig preHandler],tenant interceptor,get tenant id from request header:" + tenantId);
        }
        if (StringUtils.isEmpty(tenantId)) {
            log.info("[TenantConfig preHandler],uri:" + request.getRequestURI() + " can not find tenant in header");
            BaseResponse baseResponse = BaseResponse.error(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    NOT_FOUND_TENANT_INFO);
            String baseResponseJson = JsonUtil.toJson(baseResponse);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.getOutputStream().write(baseResponseJson.getBytes("UTF-8"));
            return false;
        } else {
            String threadName = Thread.currentThread().getName();
            String key = Contants.THREAD_DOMAIN_KEY + threadName;
            redisTemplate.opsForValue().set(key, tenantId);
            if (log.isDebugEnabled()) {
                log.debug("[TenantConfig preHandler],put redis from request header,tenant id is :{}", redisTemplate.opsForValue().get(key));
            }
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
