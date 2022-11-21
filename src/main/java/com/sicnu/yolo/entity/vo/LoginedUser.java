package com.sicnu.yolo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @className: LoginedUser
 * @description: 脱敏后的user
 * @author: 热爱生活の李
 * @since: 2022/7/6 23:25
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginedUser {
    private Integer id;
    private String username;
    private String email;
    private Integer type;
    private String ticket;
    private String headerUrl;
}
