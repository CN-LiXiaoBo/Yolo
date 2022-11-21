package com.sicnu.yolo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.yolo.entity.LoginTicket;
import com.sicnu.yolo.entity.User;
import com.sicnu.yolo.entity.dto.LoginUser;
import com.sicnu.yolo.entity.vo.LoginedUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @interfaceName: UserService
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/1 20:23
 */
public interface UserService extends IService<User>, UserDetailsService {
    LoginedUser login(String username, String password, int expired);

    void logout(String ticket);

    String uploadImage(MultipartFile imgFile,Integer userId);

    void updatePasswordById(Integer userId, String oldPassword,String newPassword);

}
