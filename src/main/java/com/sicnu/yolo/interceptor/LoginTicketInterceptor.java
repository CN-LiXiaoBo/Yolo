package com.sicnu.yolo.interceptor;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sicnu.yolo.annotation.LoginRequired;
import com.sicnu.yolo.entity.LoginTicket;
import com.sicnu.yolo.entity.User;
import com.sicnu.yolo.entity.vo.LoginedUser;
import com.sicnu.yolo.entity.vo.Result;
import com.sicnu.yolo.service.LoginTicketService;
import com.sicnu.yolo.service.UserService;
import com.sicnu.yolo.utils.CookieUtils;;
import com.sicnu.yolo.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @className: LoginTicketInterceptor
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/6 22:46
 */
@Component
public class LoginTicketInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginTicketService loginTicketService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
//    @Autowired
//    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取这个方法
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            LoginRequired annotation = handlerMethod.getMethodAnnotation(LoginRequired.class);
            if(annotation == null){
                // 不需要拦截
                return true;
            }
        }
        OutputStream os = response.getOutputStream();
        // 获取cookie中的登录凭证
        String ticket = CookieUtils.getValue(request, "ticket");
        Result result = null;
        if(ticket != null){
            // 查询凭证是否存在
            ticket = RedisKeyUtils.getTicket(ticket);
            LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(ticket);
            //检测凭证是否有效
            // 存在 && 有效 && 没有过期
            if(loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())){
//                User user = userService.getById(loginTicket.getUserId());
//                hostHolder.setUser(user);
                return true;
            }
            if(loginTicket != null && !loginTicket.getExpired().after(new Date())){
                result = Result.FAIL().setMessage("登录过期了,请重新登录");
            }
        }else{
            // 没有登录
            result = Result.FAIL().setMessage("没有登录,请登录!!!");
        }
        String jsonString = JSON.toJSONString(result);
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        os.write(jsonString.getBytes(StandardCharsets.UTF_8));
        os.flush();
        return false;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        User user = hostHolder.getUser();
//        OutputStream os = response.getOutputStream();
//        Result result;
//        if(user != null){
//            LoginedUser loginedUser = new LoginedUser();
//            BeanUtils.copyProperties(user,loginedUser);
//            result = Result.SUCCESS().setData(user);
//        }else{
//            result = Result.FAIL().setMessage("没有登录,请登录");
//        }
//        String jsonString = JSON.toJSONString(result);
//        response.setContentType("application/json; charset=utf-8");
//        response.setCharacterEncoding("UTF-8");
//        os.write(jsonString.getBytes(StandardCharsets.UTF_8));
//        os.flush();
//
//        if(modelAndView != null) modelAndView.addObject("user",result);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        hostHolder.clear();
//    }
}
