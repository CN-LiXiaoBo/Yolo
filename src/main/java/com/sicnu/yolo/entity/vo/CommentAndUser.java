package com.sicnu.yolo.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @className: CommentAndUser
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/8 21:44
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentAndUser {
    private Integer userId;
    private String username;
    private String email;
    private Integer type;
    private String headerUrl;

    private Integer commentId;
    private Integer targetId;
    private String content;
    private Date createTime;
}
