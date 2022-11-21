package com.sicnu.yolo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sicnu.yolo.entity.LoginTicket;
import com.sicnu.yolo.mapper.LoginTicketMapper;
import com.sicnu.yolo.service.LoginTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @className: LoginTicketServiceImpl
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/5 21:37
 */
@Service
@Transactional
@Deprecated
public class LoginTicketServiceImpl extends ServiceImpl<LoginTicketMapper, LoginTicket> implements LoginTicketService {
    @Autowired
    private LoginTicketMapper mapper;

    @Override
    public void updateStatusByTicket(String ticket,Integer status){
        mapper.updateStatusByTicket(ticket,status);
    }
}
