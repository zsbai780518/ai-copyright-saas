package com.copyright.saas.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 权限配置
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 登录接口，放行
            String path = handle.getPath();
            if ("/auth/login".equals(path) || "/auth/register".equals(path)) {
                handle.setCheck(true);
                return;
            }
            
            // 其他接口需要登录
            StpUtil.checkLogin();
        })).addPathPatterns("/**")
          .excludePathPatterns("/auth/**", "/error", "/favicon.ico");
    }
}
