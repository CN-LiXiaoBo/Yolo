package com.sicnu.yolo.config;

import com.sicnu.yolo.interceptor.LoginTicketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @className: MyMvcConfig
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/6 23:53
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns(
                        "/**/static/**");
    }
}
