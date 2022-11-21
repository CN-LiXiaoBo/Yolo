package com.sicnu.yolo.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @className: MessageAndUser
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/9 20:17
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageAndUser {
    private Integer messageId;
    private long noReadCount;
    private Integer fromId;
    private Integer toId;
    private String conversationId;
    private String content;
    private Integer status;
    private Date createTime;

    private Integer userId;
    private String username;
    private String headerUrl;
}
