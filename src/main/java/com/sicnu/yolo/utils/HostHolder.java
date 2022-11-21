package com.sicnu.yolo.utils;

import com.sicnu.yolo.entity.User;
import org.springframework.stereotype.Component;

/**
 * @className: HostHolder
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/6 23:07
 */
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();
    public void setUser(User user){
        users.set(user);
    }
    public User getUser(){
        return users.get();
    }
    public void clear(){
        users.remove();
    }

}
