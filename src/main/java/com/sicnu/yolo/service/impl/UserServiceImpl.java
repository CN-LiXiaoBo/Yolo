package com.sicnu.yolo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.yolo.entity.LoginTicket;
import com.sicnu.yolo.entity.User;
import com.sicnu.yolo.entity.exception.MyException;
import com.sicnu.yolo.entity.vo.LoginedUser;
import com.sicnu.yolo.mapper.UserMapper;
import com.sicnu.yolo.service.LoginTicketService;
import com.sicnu.yolo.service.UserService;
import com.sicnu.yolo.utils.MD5Utils;
import com.sicnu.yolo.utils.QiniuUtils;
import com.sicnu.yolo.utils.RedisKeyUtils;
import com.sicnu.yolo.utils.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @className: UserServiceImpl
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/1 20:24
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTicketService ticketService;
    @Value("${qiniu.static-localtion}")
    private String qiniuPath;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @param expired 过期时间(s)
     * @return 登录凭证
     */
    @Override
    public LoginedUser login(String username, String password, int expired) {
        password = MD5Utils.Md5(password,username);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        wrapper.eq("password",password);
        User user = userMapper.selectOne(wrapper);
        if(user == null){
            throw new MyException("账号或密码错误");
        }
        if(user.getStatus() == 0){
            throw new MyException("该账号未激活");
        }
        LoginTicket ticket = new LoginTicket();
        ticket
                .setUserId(user.getId())
                .setStatus(0)
                .setExpired(new Date(System.currentTimeMillis() + ((long) expired)*1000))
                .setTicket(UUIDUtils.generateUUID());

        String s = RedisKeyUtils.getTicket(ticket.getTicket());
        redisTemplate.opsForValue().set(s,ticket);
//        ticketService.save(ticket);
        LoginedUser loginedUser = new LoginedUser();
        BeanUtils.copyProperties(user,loginedUser);
        loginedUser.setTicket(ticket.getTicket());
        return loginedUser;
    }

    @Override
    public void logout(String ticket) {
        String ticket1 = RedisKeyUtils.getTicket(ticket);
        LoginTicket loginTicket = (LoginTicket)redisTemplate.opsForValue().get(ticket1);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(ticket1,loginTicket);
//        ticketService.updateStatusByTicket(ticket,1);
    }

    @Override
    public String uploadImage(MultipartFile imgFile,Integer userId) {
        if(userId == null || userId == 0){
            throw new MyException("上传头像失败");
        }
        String originalFilename = imgFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.') - 1);
        String fileName = UUIDUtils.generateUUID()+extension;
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            String path = qiniuPath+fileName;
            User user = new User();
            user.setId(userId);
            user.setHeaderUrl(path);
            userMapper.updateHeaderUrlById(path,userId);
            return path;
        } catch (Exception e) {
            throw new MyException("上传头像失败");
        }
    }

    @Override
    public void updatePasswordById(Integer userId, String oldPassword,String newPassword) {
        if(userId == null || userId == 0) throw new MyException("更改密码失败");
        if(oldPassword.equals(newPassword)) throw new MyException("原密码和修改后的密码一致");
        User user = userMapper.selectById(userId);
        if(user == null) throw new MyException("更改密码失败");
        String salt = user.getSalt();
        if (!MD5Utils.Md5(oldPassword,salt).equals(user.getPassword())){
            throw new MyException("原密码输入错误");
        }
        user.setPassword(MD5Utils.Md5(newPassword,salt));
        userMapper.updateById(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",s));
    }
}
