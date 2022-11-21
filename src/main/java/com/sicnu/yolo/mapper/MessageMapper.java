package com.sicnu.yolo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.sicnu.yolo.entity.Message;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @interfaceName: MessageMapper
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/9 19:47
 */
public interface MessageMapper extends BaseMapper<Message> {
    @Select({
            "select * from message where id in ("+
           "select max(id) from message "+
           "where status != 2 and from_id != 1 "+
           "and (from_id = #{userId} or to_id = #{userId}) "+
                   "group by conversation_id "+
                   ")order by status ,create_time desc "
    })
    Page<Message> getUserMessageList(Integer userId);

    @Update({
            "update message set status = 1 where status = 0 and conversation_id = #{conversationId} and to_id = #{userId}"
    })
    void updateStatus(String conversationId, Integer userId);

    @Select({
            "select * from message where conversation_id = #{type} and from_id = 1 and to_id = #{userId} and status != 2" +
                    "order by status ,create_time desc"
    })
    Page<Message> selectSystemMessage(Integer userId, String type);
}
