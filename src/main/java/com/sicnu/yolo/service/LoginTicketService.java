package com.sicnu.yolo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.yolo.entity.LoginTicket;

/**
 * @interfaceName: LoginTicketService
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/5 21:37
 */
@Deprecated
public interface LoginTicketService extends IService<LoginTicket> {
    void updateStatusByTicket(String ticket,Integer status);
}
