package com.sicnu.yolo.entity.vo;

import com.sicnu.yolo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @className: CommentVo
 * @description: 评论vo封装
 * @author: 热爱生活の李
 * @since: 2022/7/8 20:03
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    //当前评论
    private CommentAndUser comment;
    //当前评论的子评论
    private List<CommentAndUser> comments;
}
