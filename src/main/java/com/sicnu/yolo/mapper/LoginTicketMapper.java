package com.sicnu.yolo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * @interfaceName: LoginTickeet
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/5 21:35
 */
@Deprecated
public interface LoginTicketMapper extends BaseMapper<com.sicnu.yolo.entity.LoginTicket> {
    @Update({
            "update login_ticket set status = #{status} where ticket = #{ticket}"
    })
    void updateStatusByTicket(String ticket, Integer status);
}
