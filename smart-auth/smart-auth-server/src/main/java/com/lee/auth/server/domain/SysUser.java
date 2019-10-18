//package com.lee.auth.server.domain;
//
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
///**
// * @author lee.li
// */
//@Data
//public class SysUser implements UserDetails {
///**/
//    /**
//     * 用户名
//     **/
//    private String username;
//    /**
//     * 密码
//     **/
//    private String password;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//}
