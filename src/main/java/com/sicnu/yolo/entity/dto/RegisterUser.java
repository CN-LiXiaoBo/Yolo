package com.sicnu.yolo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


/**
 * @className: RegisterUser
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/2 17:32
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @Email(message = "邮箱格式错误")
    private String email;
}
