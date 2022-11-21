package com.sicnu.yolo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @className: LoginTicket
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/5 21:33
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginTicket {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String ticket;
    private Integer status;
    private Date expired;
}
