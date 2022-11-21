package com.sicnu.yolo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @className: LoginUser
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/5 23:48
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    private boolean remember;
    public boolean isRemember(){
        return this.remember;
    }
}
