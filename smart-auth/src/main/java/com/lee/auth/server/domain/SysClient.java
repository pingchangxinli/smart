package com.lee.auth.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author lee.li
 */
@Data
@TableName(value = "oauth_client_details")
public class SysClient {
    /**
     *
     */
    @TableId(value = "client_id",type = IdType.INPUT)
    @JsonProperty("client_id")
    private String clientId;
    /**
     *
     */
    @TableField(value = "resource_ids")
    private String resourceIds;
    /**
     *
     */
    @TableField(value = "client_secret")
    @JsonProperty("client_secret")
    private String clientSecret;
    /**
     *
     */
    @TableField(value = "scope")
    private String scope = "all";
    /**
     *
     */
    @TableField(value = "authorized_grant_types")
    private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";
    /**
     *
     */
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;
    /**
     *
     */
    @TableField(value = "authorities")
    private String authorities = "";
    /**
     *
     */
    @TableField(value = "access_token_validity")
    private Integer accessTokenValidity = 18000;
    /**
     *
     */
    @TableField(value = "refresh_token_validity")
    private Integer refreshTokenValidity = 18000;
    /**
     *
     */
    @TableField(value = "additional_information")
    private String additionalInformation = "{}";
    /**
     *
     */
    @TableField(value = "autoapprove")
    private String autoapprove ;
}
