package com.sicnu.yolo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.yolo.entity.Message;
import com.sicnu.yolo.entity.dto.SendMessage;
import com.sicnu.yolo.entity.vo.MessageVo;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @interfaceName: MessageService
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/9 19:47
 */
public interface MessageService extends IService<Message> {
    MessageVo getUserMessageList(Integer userId,Integer currentPage,Integer pageSize);

    void updateStatus(String conversationId, Integer userId);

    void sendMessage(SendMessage sendMessage);

    Map<String,Long> getSystemMessage(Integer userId);

    List<Message> getMessage(Integer userId, String type,Integer currentPage);
}
