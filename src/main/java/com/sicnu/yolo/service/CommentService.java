package com.sicnu.yolo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.yolo.entity.Comment;
import com.sicnu.yolo.entity.dto.QueryPageBean;
import com.sicnu.yolo.entity.vo.CommentVo;
import com.sicnu.yolo.entity.vo.PageResult;

import java.util.List;

/**
 * @interfaceName: CommentService
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/8 20:14
 */
public interface CommentService extends IService<Comment> {
    PageResult selectComment(Integer discussPostId, QueryPageBean queryPageBean);

    void addComment(Comment comment);
}
