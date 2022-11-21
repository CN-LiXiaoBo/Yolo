package com.sicnu.yolo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.sicnu.yolo.entity.DiscussPost;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @interfaceName: DiscussPostMapper
 * @description: 帖子mapper
 * @author: 热爱生活の李
 * @since: 2022/7/1 19:18
 */
public interface DiscussPostMapper extends BaseMapper<DiscussPost> {
    Page<DiscussPost> findPageByUserId(String queryString);
    @Select({
            "select * from discuss_post where id = #{discussPostId} and status != 2"
    })
    DiscussPost findDiscussPostById(Integer discussPostId);

    @Update({
            "update discuss_post set comment_count = comment_count + #{num} where id = #{discussPostId}"
    })
    void updateCommentCountById(Integer discussPostId, int num);
}
