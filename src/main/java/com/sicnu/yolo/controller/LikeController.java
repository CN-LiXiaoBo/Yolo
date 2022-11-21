package com.sicnu.yolo.controller;

import com.sicnu.yolo.entity.dto.Like;
import com.sicnu.yolo.entity.vo.Result;
import com.sicnu.yolo.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: LikeController
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/10 16:07
 */
@RestController
public class LikeController {
    @Autowired
    private LikeService likeService;
    @PostMapping("/like")
    public Result likeOrNoLike(@RequestBody Like like){
        likeService.like(like);
        return Result.SUCCESS().setMessage("点赞成功");
    }
    @GetMapping("/likeCount")
    public Result likeCount(Integer entityType,Integer entityId){
        Like like = new Like();
        like.setEntityType(entityType);
        like.setEntityId(entityId);
        long count = likeService.likeCount(like);
        return Result.SUCCESS().setData(count);
    }
    @GetMapping("/likeStatus")
    public Result likeStatus(Integer userId,Integer entityType,Integer entityId){
        Like like = new Like(userId, entityType, entityId,null);
        return Result.SUCCESS().setData(likeService.likeStatus(like));
    }
    @GetMapping("/getLikeCount")
    public Result getLikeCount(Integer userId){
        long l = likeService.likeUser(userId);
        return Result.SUCCESS().setData(l);
    }
}
