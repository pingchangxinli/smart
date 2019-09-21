package com.lee.common.core;

/**
 * @author haitao.li
 */
public class Contants {
    /**
     * 租户redis hash key
     */
    public static final String TENANT_KEY = "tenant_domain";
    /**
     * 线程&域名 KEY
     */
    public static final String THREAD_DOMAIN_KEY = "THEAD_DOMAIN:";
    /**
     * 放入request header中 tenant id 的 key
     */
    public static final String REQUEST_HEADER_TENANT_ID = "X-TENANT-ID";
    /**
     * 分页查询当前页
     */
    public static final String PARAM_PAGE = "page";
    /**
     * 每页条数
     */
    public static final String PARAM_LIMIT = "limit";
}
