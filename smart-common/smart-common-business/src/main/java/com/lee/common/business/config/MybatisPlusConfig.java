package com.lee.common.business.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haitao Li
 */
@Configuration
@Slf4j
@MapperScan(basePackages = {"com.lee.mapper", "com.lee.*.mapper", "com.lee.*.*.mapper", "com.lee.*.*.*.mapper"})
public class MybatisPlusConfig {
    @Resource
    private HttpServletRequest request;
    /**
     * 分页插件
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        /*
//         * 【测试多租户】 SQL 解析处理拦截器<br>
//         * 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（ 注意观察 SQL ）<br>
//         */
//        List<ISqlParser> sqlParserList = new ArrayList<>();
//        TenantSqlParser tenantSqlParser = new TenantSqlParser();
//        tenantSqlParser.setTenantHandler(new TenantHandler() {
//            @Override
//            public Expression getTenantId(boolean where) {
//                // 从请求中获取租户ID
//                String tenantId = request.getHeader("X-TENANT-ID");
//                if (StringUtils.isEmpty(tenantId)) {
//                    log.error("[MybatisPlusConfig] service can not get tenant id ");
//                }
//                log.info("Tenant interceptor get tenant id from request header:{}", tenantId);
//
//                return new LongValue(tenantId);
//            }
//
//            @Override
//            public String getTenantIdColumn() {
//                return "tenant_id";
//            }
//
//            @Override
//            public boolean doTableFilter(String tableName) {
//                // 这里可以判断是否过滤表
//                if ("sys_tenant".equals(tableName)) {
//                    return true;
//                }
//                return false;
//            }
//        });
//        sqlParserList.add(tenantSqlParser);
//        paginationInterceptor.setSqlParserList(sqlParserList);
//        return paginationInterceptor;
//    }
}
