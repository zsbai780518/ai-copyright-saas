package com.copyright.saas.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.copyright.saas.dto.LoginDTO;
import com.copyright.saas.dto.R;
import com.copyright.saas.dto.RegisterDTO;
import com.copyright.saas.entity.User;
import com.copyright.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public R<?> login(@Validated @RequestBody LoginDTO dto) {
        User user = userService.login(dto.getUsername(), dto.getPassword());
        if (user == null) {
            return R.error("用户名或密码错误");
        }
        
        // 生成 Token
        StpUtil.login(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        
        return R.ok(tokenInfo);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public R<?> register(@Validated @RequestBody RegisterDTO dto) {
        User user = userService.register(dto);
        if (user == null) {
            return R.error("注册失败，用户名已存在");
        }
        return R.ok("注册成功");
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public R<?> logout() {
        StpUtil.logout();
        return R.ok("登出成功");
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/current")
    public R<?> getCurrentUser() {
        Long userId = StpUtil.getLoginIdAsLong();
        User user = userService.getById(userId);
        if (user != null) {
            user.setPasswordHash(null);
        }
        return R.ok(user);
    }
}
