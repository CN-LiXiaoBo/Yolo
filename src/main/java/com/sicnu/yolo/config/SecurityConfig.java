package com.sicnu.yolo.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sicnu.yolo.entity.User;
import com.sicnu.yolo.service.UserService;
import com.sicnu.yolo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @className: SecurityConfig
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/29 21:22
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略静态资源的访问
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内置的认证规则
        // auth.userDetailsService(userService);
        // 自定义
        auth.authenticationProvider(new AuthenticationProvider() {
            // 用于封装认证信息的接口
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String password = (String) authentication.getCredentials();
                User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
                if(user == null){
                    throw new UsernameNotFoundException("账号不存在");
                }
                if(user.getStatus() == 0){
                    throw new BadCredentialsException("账号未激活");
                }
                password = MD5Utils.Md5(password,user.getSalt());
                if(!password.equals(user.getPassword())){
                    throw new BadCredentialsException("账号或密码错误");
                }
                return new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities());
            }
            @Override
            public boolean supports(Class<?> aClass) {
                return UsernamePasswordAuthenticationToken.class.equals(aClass);
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl("/user/login");
        http.logout()
                .logoutUrl("/user/logout");
    }
}
