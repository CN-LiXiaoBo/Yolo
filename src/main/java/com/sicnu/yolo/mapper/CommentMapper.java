package com.sicnu.yolo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.sicnu.yolo.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @className: CommentMapper
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/8 20:13
 */
public interface CommentMapper extends BaseMapper<Comment> {
    @Select({
            "select  * from comment " +
            "where entity_type = #{type} and entity_id = #{id} and status = 0 " +
            "order by create_time desc"
    })
    Page<Comment> selectComment(Integer id, int type);

    List<Comment> selectCommentByIds(@Param("ids") List<Integer> ids,@Param("type") int type);
}
