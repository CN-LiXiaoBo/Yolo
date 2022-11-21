package com.sicnu.yolo.service;

import com.sicnu.yolo.entity.dto.Like;

/**
 * @interfaceName: LikeService
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/10 16:10
 */
public interface LikeService {
    void like(Like like);
    // 点赞数量
    long likeCount(Like like);
    //点赞状态
    Boolean likeStatus(Like like);
    // 获取多少人赞过这用户
    long likeUser(Integer userId);
}
