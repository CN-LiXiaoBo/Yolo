package com.sicnu.yolo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.mybatis.spring.annotation.MapperScan;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @className: Comment
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/8 19:30
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @NotNull(message = "评论类型不能为空")
    private Integer entityType;
    @NotNull(message = "评论对象id不能为空")
    private Integer entityId;
    @NotNull(message = "评论目标人不能为空")
    private Integer targetId;
    @NotBlank(message = "评论内容不能为空")
    private String content;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
