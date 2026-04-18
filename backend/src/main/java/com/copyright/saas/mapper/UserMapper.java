package com.copyright.saas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.copyright.saas.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户 Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE username = #{username} AND status = 1")
    User findByUsername(String username);
}
