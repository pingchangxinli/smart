//package com.lee.common.core.config;
//
//import com.baomidou.mybatisplus.core.parser.ISqlParser;
//import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
//import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.LongValue;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author haitao.li
// */
//@Configuration
//@AutoConfigureAfter(DataSource.class)
//public class MybatisPlusConfig {
//    @Bean
//    public SqlExplainInterceptor sqlExplainInterceptor(){
//        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
//        TenantSqlParser tenantSqlParser = new TenantSqlParser();
//        tenantSqlParser.setTenantHandler(new TenantHandler() {
//            /**
//             * 获取租户 ID 值表达式，支持多个 ID 条件查询
//             * <p>
//             * 支持自定义表达式，比如：tenant_id in (1,2) @since 2019-8-2
//             *
//             * @param where 参数 true 表示为 where 条件 false 表示为 insert 或者 select 条件
//             * @return 租户 ID 值表达式
//             */
//            @Override
//            public Expression getTenantId(boolean where) {
//                if(where) {
//                    return  null;
//                } else {
//                    return null;
//                }
//            }
//            /**
//             * 获取租户字段名
//             *
//             * @return 租户字段名
//             */
//            @Override
//            public String getTenantIdColumn() {
//                return "tenant_id";
//            }
//            private Expression singleTenantIdCondition() {
//                return new LongValue(1);//ID自己想办法获取到
//            }
//            /**
//             * 根据表名判断是否进行过滤
//             *
//             * @param tableName 表名
//             * @return 是否进行过滤, true:表示忽略，false:需要解析多租户字段
//             */
//            @Override
//            public boolean doTableFilter(String tableName) {
//                return !"user".equalsIgnoreCase(tableName);
//            }
//        });
//        List<ISqlParser> sqlParserList = new ArrayList<>();
//        sqlParserList.add(tenantSqlParser);
//        sqlExplainInterceptor.setSqlParserList(sqlParserList);
//        return sqlExplainInterceptor;
//    }
//}
