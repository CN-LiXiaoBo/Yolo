package com.sicnu.yolo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.yolo.entity.DiscussPost;
import com.sicnu.yolo.entity.dto.QueryPageBean;
import com.sicnu.yolo.entity.vo.DiscussPostAndUser;
import com.sicnu.yolo.entity.vo.PageResult;

/**
 * @interfaceName: DiscussPostServicee
 * @description: 帖子服务
 * @author: 热爱生活の李
 * @since: 2022/7/1 19:25
 */
public interface DiscussPostServicee extends IService<DiscussPost> {
    /**
     * 分页查询帖子
     */
    PageResult findPageByUserId(QueryPageBean queryPageBean);

    void addDiscussPost(DiscussPost post);

    DiscussPostAndUser selectDiscussPostById(Integer discussPostId);

    void updateCommentCountById(Integer discussPostId, int num);
}
