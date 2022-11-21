package com.sicnu.yolo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * @className: User
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/1 20:20
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private Integer type;
    private Integer status;
    private String activeCode;
    private String headerUrl;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(()->{
            switch (type){
                case 0:
                    return "USER"; // 普通用户
                case 1:
                    return "ADMIN"; // 管理员
                case 2:
                    return "MODERATOR"; // 版主
                default:
                    return "USER";
            }
        });
        return list;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
