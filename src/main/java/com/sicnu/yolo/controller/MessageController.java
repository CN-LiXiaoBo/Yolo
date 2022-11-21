package com.sicnu.yolo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sicnu.yolo.entity.Message;
import com.sicnu.yolo.entity.dto.SendMessage;
import com.sicnu.yolo.entity.vo.MessageVo;
import com.sicnu.yolo.entity.vo.Result;
import com.sicnu.yolo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
/**
 * @className: MessageController
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/9 19:27
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    // 获取未读私信的总条数

    @GetMapping("/getNoReadCount")
    public Result getNoReadCount(Integer userId){
        long count = messageService.count(
                new QueryWrapper<Message>()
                .eq("to_id", userId)
                .eq("status", 0));
        return Result.SUCCESS().setData(count);
    }
    // 获取别人发的私信页面
    @GetMapping("/getUserMessageList/{userId}/{currentPage}")
    public Result getUserMessage(@PathVariable Integer userId,@PathVariable Integer currentPage){
        MessageVo messageVo = messageService.getUserMessageList(userId,currentPage,10);
        return Result.SUCCESS().setData(messageVo);
    }
    // 进去具体页面查看私信
    @GetMapping("/datail/{conversationId}/{userId}")
    public Result getMessage(@PathVariable String conversationId,@PathVariable Integer userId){
        messageService.updateStatus(conversationId,userId);
        List<Message> messages = messageService.list(new QueryWrapper<Message>()
                .eq("conversation_id", conversationId)
                .ne("status", 2)
                .orderByAsc("create_time"));
        return Result.SUCCESS().setData(messages);
    }
    @PostMapping("/sendMessage")
    public Result sendMessage(@Validated @RequestBody SendMessage sendMessage){
        System.out.println(sendMessage);
        messageService.sendMessage(sendMessage);
        return Result.SUCCESS();
    }

    @GetMapping("/getSystemMessageNoRead/{userId}")
    public Result getSystemMessageCount(@PathVariable Integer userId){
        Map<String,Long> messages = messageService.getSystemMessage(userId);
        return Result.SUCCESS().setData(messages);
    }
    @GetMapping("/getSystemMessage/{userId}/{currentPage}")
    public Result getSystemMessage(@PathVariable Integer userId,String type,@PathVariable Integer currentPage){
        List<Message> messages = messageService.getMessage(userId,type,currentPage);
        return Result.SUCCESS().setData(messages);
    }
}
