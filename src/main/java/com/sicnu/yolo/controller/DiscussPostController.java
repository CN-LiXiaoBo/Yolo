package com.sicnu.yolo.controller;

import com.sicnu.yolo.annotation.LoginRequired;
import com.sicnu.yolo.constant.YoloConstant;
import com.sicnu.yolo.entity.Comment;
import com.sicnu.yolo.entity.DiscussPost;
import com.sicnu.yolo.entity.Event;
import com.sicnu.yolo.entity.dto.QueryPageBean;
import com.sicnu.yolo.entity.exception.MyException;
import com.sicnu.yolo.entity.vo.DiscussPostAndUser;
import com.sicnu.yolo.entity.vo.PageResult;
import com.sicnu.yolo.entity.vo.Result;
import com.sicnu.yolo.event.EventProducer;
import com.sicnu.yolo.service.CommentService;
import com.sicnu.yolo.service.DiscussPostServicee;
import com.sicnu.yolo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @className: DiscussPostController
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/7 23:51
 */
@RestController
@RequestMapping("/discussPost")
public class DiscussPostController {
    @Autowired
    private DiscussPostServicee discussPostServicee;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EventProducer eventProducer;
    @LoginRequired
    @PostMapping("/add")
    public Result add(@RequestBody DiscussPost post){
        discussPostServicee.addDiscussPost(post);
        return Result.SUCCESS();
    }
    //查询帖子
    @GetMapping("/detail/{discussPostId}")
    public Result detail(@PathVariable Integer discussPostId){
        if(discussPostId == null || discussPostId == 0) throw new MyException("参数不能为空");
        DiscussPostAndUser discussPostAndUser = discussPostServicee.selectDiscussPostById(discussPostId);
        return Result.SUCCESS().setData(discussPostAndUser);
    }
    // 查询帖子的评论
    @GetMapping("/detail/comment/{discussPostId}/{pageSize}/{currentPage}")
    public Result comment(@PathVariable Integer discussPostId,@PathVariable Integer pageSize,@PathVariable Integer currentPage){
        QueryPageBean queryPageBean = new QueryPageBean(currentPage, pageSize, null);
        PageResult commentVos = commentService.selectComment(discussPostId,queryPageBean);
        return Result.SUCCESS().setData(commentVos);
    }
    //增加评论
    @PostMapping("/addComment")
    public Result addComment(@Validated @RequestBody Comment comment){
        commentService.addComment(comment);
        Event event = new Event();
        event.setTopic(YoloConstant.TOPIC_COMMENT);
        event.setUserId(comment.getUserId());
        event.setEntityType(comment.getEntityType());
        event.setEntityId(comment.getEntityId());
        event.setEntityUserId(comment.getTargetId());
        eventProducer.fireEvent(event);
        return Result.SUCCESS().setMessage("评论成功");
    }
}
