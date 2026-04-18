package com.copyright.saas.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.copyright.saas.dto.RegisterDTO;
import com.copyright.saas.entity.User;
import com.copyright.saas.mapper.UserMapper;
import cn.hutool.crypto.SecureUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    /**
     * 用户登录
     */
    public User login(String username, String password) {
        User user = baseMapper.findByUsername(username);
        if (user == null) {
            return null;
        }
        
        // 验证密码
        String inputHash = SecureUtil.sha256(password);
        if (!inputHash.equals(user.getPasswordHash())) {
            return null;
        }
        
        // 更新最后登录时间
        user.setLastLoginTime(java.time.LocalDateTime.now());
        baseMapper.updateById(user);
        
        return user;
    }

    /**
     * 用户注册
     */
    @Transactional
    public User register(RegisterDTO dto) {
        // 检查用户名是否已存在
        User existUser = baseMapper.findByUsername(dto.getUsername());
        if (existUser != null) {
            return null;
        }
        
        // 创建用户
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPasswordHash(SecureUtil.sha256(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(1); // 默认企业主账号
        user.setStatus(1);
        
        baseMapper.insert(user);
        
        return user;
    }
}
