package com.sicnu.yolo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sicnu.yolo.annotation.LoginRequired;
import com.sicnu.yolo.constant.YoloConstant;
import com.sicnu.yolo.entity.User;
import com.sicnu.yolo.entity.dto.LoginUser;
import com.sicnu.yolo.entity.dto.RegisterUser;
import com.sicnu.yolo.entity.exception.MyException;
import com.sicnu.yolo.entity.vo.LoginedUser;
import com.sicnu.yolo.entity.vo.Result;
import com.sicnu.yolo.service.UserService;
import com.sicnu.yolo.utils.MD5Utils;
import com.sicnu.yolo.utils.MailUtils;
import com.sicnu.yolo.utils.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
/**
 * @className: UserController
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/2 17:29
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailUtils mailUtil;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    private String TEXT = "注册激活账号";

    /**
     * 注册
     * @param user
     */
    @PostMapping("/register")
    public Result register(@Validated @RequestBody RegisterUser user){
        if(userService.getOne(new QueryWrapper<User>().eq("email", user.getEmail())) != null){
            throw new MyException("该邮箱已经被注册,请去激活");
        }
        try {
            String uuid = UUIDUtils.generateUUID();
            String md5 = MD5Utils.Md5(user.getPassword(), user.getUsername());
            mailUtil.sendHtmlMail(user.getEmail(),TEXT,uuid);
            User u = new User();
            BeanUtils.copyProperties(user,u);
            u.setActiveCode(uuid);
            u.setPassword(md5);
            u.setSalt(user.getUsername());
            userService.save(u);
        } catch (MessagingException e) {
            throw new MyException("邮件发送失败");
        }
        return Result.SUCCESS().setMessage("激活邮件发送成功");
    }

    /**
     * 激活
     * @param uuid
     */
    @GetMapping("/active")
    @Validated
    public Result active(@NotBlank(message = "uuid不能为空") String uuid){
        User user;
        if((user = userService.getOne(new QueryWrapper<User>().eq("active_code", uuid))) == null){
            throw new MyException("请使用在正常路径");
        }
        try {
            user.setStatus(1);
            userService.updateById(user);
        } catch (Exception e) {
            throw new MyException("激活失败");
        }
        return Result.SUCCESS().setMessage("激活成功");
    }


    /**
     * 登录
     * @param user 登录用户
     * @return 登录凭证
     */
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginUser user, HttpServletResponse response)
    {
        int expired = user.isRemember() ? YoloConstant.LONGEXPIRETIME:YoloConstant.EXPIRETIME;
        LoginedUser loginedUser= userService.login(user.getUsername(),user.getPassword(),expired);
        Cookie cookie = new Cookie("ticket",loginedUser.getTicket());
        cookie.setPath(contextPath);
        cookie.setMaxAge(expired);
        response.addCookie(cookie);
        return Result.SUCCESS().setData(loginedUser);
    }

    /**
     * 退出
     * @param ticket 登录凭证
     * @return
     */
    @LoginRequired
    @PostMapping("/logout")
    public Result logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return Result.SUCCESS().setMessage("退出成功");
    }


    /**
     * 上传头像
     * @param imgFile 图片文件
     * @return
     */
    @LoginRequired
    @PostMapping("/uploadHeadlerImage")
    public Result uploadHeadlerImage(@RequestParam("imgFile")MultipartFile imgFile,Integer userId){
        String path = userService.uploadImage(imgFile,userId);
        return Result.SUCCESS().setData(path);
    }

    /**
     * 修改密码
     * @param userId 用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @LoginRequired
    @PutMapping("/updatePasswordById")
    public Result updatePasswordById(@RequestParam Integer userId,@RequestParam String oldPassword,@RequestParam String newPassword){
        userService.updatePasswordById(userId,oldPassword,newPassword);
        return Result.SUCCESS();
    }
}
