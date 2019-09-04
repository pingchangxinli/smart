package com.lee.user.mapper;

import com.lee.user.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author haitao.li
 */
@Mapper
public interface UserMapper {
    /**
     * 新增User
     *
     * @param user 用户
     */
    @Insert("INSERT INTO users (" +
            "  id," +
            "  user_id," +
            "  username," +
            "  password," +
            "  email," +
            "  phone," +
            "  site_id," +
            "  shop_id," +
            "  enabled" +
            ") " +
            "VALUES" +
            "  (" +
            "    #{user.id}," +
            "    #{user.userId}," +
            "    #{user.username}," +
            "    #{user.password}," +
            "    #{user.email}," +
            "    #{user.phone}," +
            "    #{user.siteId}," +
            "    #{user.shopId}," +
            "    #{user.enabled}" +
            "  )" +
            "")
    void createUser(@Param("user") User user);

    /**
     * 用户信息
     * @param userId 用户ID
     * @param siteId 站点ID
     * @return 用户信息
     */
    @Select("select * from users t where t.user_id = #{userId} and t.site_id = #{siteId}")
    User loadUserByUserIdAndSiteId(@Param("userId") String userId, @Param("siteId") Integer siteId);
}
