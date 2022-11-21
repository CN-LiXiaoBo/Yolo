package com.sicnu.yolo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sicnu.yolo.constant.YoloConstant;
import com.sicnu.yolo.entity.Comment;
import com.sicnu.yolo.entity.User;
import com.sicnu.yolo.entity.dto.QueryPageBean;
import com.sicnu.yolo.entity.exception.MyException;
import com.sicnu.yolo.entity.vo.CommentAndUser;
import com.sicnu.yolo.entity.vo.CommentVo;
import com.sicnu.yolo.entity.vo.PageResult;
import com.sicnu.yolo.mapper.CommentMapper;
import com.sicnu.yolo.service.CommentService;
import com.sicnu.yolo.service.DiscussPostServicee;
import com.sicnu.yolo.service.UserService;
import com.sicnu.yolo.utils.SensitiveUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: CommentServiceImpl
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/8 20:14
 */
@Service
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private DiscussPostServicee discussPostServicee;
    @Autowired
    private SensitiveUtils sensitiveUtils;
    @Override
    public PageResult selectComment(Integer discussPostId, QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        currentPage = currentPage == null || currentPage == 0?1:currentPage;
        pageSize = pageSize == null || pageSize == 0?5:pageSize;
        PageHelper.startPage(currentPage,pageSize);
        // 分页查出回复这条帖子的评论
        Page<Comment> pages= commentMapper.selectComment(discussPostId,YoloConstant.COMMENTDISCUSSPOST);
        long pagesTotal = pages.getTotal();
        // 回复这条帖子的评论
        List<Comment> commentToDiscussPost = pages.getResult();
        // 没有人评论时候
        if(commentToDiscussPost == null) return null;
        List<Integer> userIds = new ArrayList<>();
        // 获取回复帖子评论的id
        List<Integer> ids = new ArrayList<>();
        commentToDiscussPost.stream().mapToInt(e->e.getId()).distinct().sorted().forEach(e->ids.add(e));
        commentToDiscussPost.stream().mapToInt(e->e.getUserId()).distinct().sorted().forEach(e->userIds.add(e));
        List<Comment> commentToComment = commentMapper.selectCommentByIds(ids,YoloConstant.COMMENTCOMMENT);
        // 没有人回复评论时
        if(commentToComment != null && commentToComment.size() > 0){
            commentToComment.stream().mapToInt(e->e.getUserId()).distinct().forEach(e->userIds.add(e));
        }
        //获取到所有人的信息
        List<Integer> userIdList = userIds.stream().distinct().sorted().collect(Collectors.toList());
        List<User> users = userService.listByIds(userIdList);
        List<CommentVo> commentVos = new ArrayList<>();
        //遍历回复帖子的评论
        commentToDiscussPost.stream().forEach(discussPost->{
            CommentVo commentVo = new CommentVo();
            //回复帖子的评论
            CommentAndUser commentAndUser = new CommentAndUser();
            BeanUtils.copyProperties(discussPost,commentAndUser);
            commentAndUser.setCommentId(discussPost.getId());
            //或取这个的用户
            users.stream().filter(user->user.getId() == discussPost.getUserId()).forEach(u->{
                BeanUtils.copyProperties(u,commentAndUser);
                commentAndUser.setUserId(u.getId());
            });
            // 一级评论就完成了
            commentVo.setComment(commentAndUser);
            List<CommentAndUser> comToCom = new ArrayList<>();
            commentVo.setComments(comToCom);
            //找有没有回复这个评论的评论
            List<Comment> comments = commentToComment.stream().filter(e -> e.getEntityId().intValue() == discussPost.getId().intValue() && e.getEntityType() == YoloConstant.COMMENTCOMMENT).collect(Collectors.toList());
            // 说明有人回复了这个评论
            if(comments != null && comments.size() > 0){
                comments.stream().forEach(comment -> {
                    CommentAndUser cau = new CommentAndUser();
                    BeanUtils.copyProperties(comment,cau);
                    cau.setCommentId(comment.getId());
                    users.stream().filter(user->user.getId().intValue() == comment.getUserId().intValue()).forEach(u->{
                        BeanUtils.copyProperties(u,cau);
                        cau.setUserId(u.getId());
                    });
                    comToCom.add(cau);
                });
            }
            commentVos.add(commentVo);
        });
        return new PageResult(pagesTotal,commentVos);
    }

    @Override
    public void addComment(Comment comment) {
        String content = comment.getContent();
        if(content == null)throw new MyException("评论不能为空");
        comment.setContent(sensitiveUtils.filter(content));
        commentMapper.insert(comment);
        if (comment.getEntityType().intValue() == YoloConstant.COMMENTDISCUSSPOST){
            discussPostServicee.updateCommentCountById(comment.getEntityId(),1);
        }
        return;
    }
}
