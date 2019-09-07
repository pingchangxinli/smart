package com.lee.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author haitao.li
 * 用户信息类
 */
@Data
public class User implements UserDetails {
    /**
     * 自生成ID
     **/
    private Long id;
    /**
     * 用户自定义ID
     */
    @JsonProperty("user_id")
    private String userId;
    /**
     * 用户名
     **/
    private String username;
    /**
     * 密码
     **/
    private String password;
    /**
     * 邮箱
     **/
    private String email;
    /**
     * 手机号
     **/
    private String phone;
    /**
     * 客户站点编号
     **/
    @JsonProperty("site_id")
    private Integer siteId;
    /**
     * 客户门店编号
     **/
    @JsonProperty("shop_id")
    private Integer shopId;

    /**
     * 是否可用
     **/
    private boolean enabled;

    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
