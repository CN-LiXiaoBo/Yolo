package com.sicnu.yolo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sicnu.yolo.entity.Message;
import com.sicnu.yolo.entity.User;
import com.sicnu.yolo.entity.dto.SendMessage;
import com.sicnu.yolo.entity.exception.MyException;
import com.sicnu.yolo.entity.vo.MessageAndUser;
import com.sicnu.yolo.entity.vo.MessageVo;
import com.sicnu.yolo.mapper.MessageMapper;
import com.sicnu.yolo.mapper.UserMapper;
import com.sicnu.yolo.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: MessageServiceImpl
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/9 19:48
 */
@Service
@Transactional
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public MessageVo getUserMessageList(Integer userId,Integer current,Integer pageSize) {
        if(userId == null) throw new MyException("用户id不能为空");
        MessageVo messageVo = new MessageVo();
        Long count = messageMapper.selectCount(new QueryWrapper<Message>()
                .eq("status", 0)
                .eq("to_id", userId)
                .ne("from_id", 1));
        messageVo.setTotalNoReadCount(count);
        PageHelper.startPage(current,pageSize);
        Page<Message> messageList = messageMapper.getUserMessageList(userId);
        List<Message> messages = messageList.getResult();
        List<MessageAndUser> messageAndUsers = new ArrayList<>();
        messageVo.setMessageAndUsers(messageAndUsers);
        // 查看这一页中每个对话的未读的情况
        messages.stream().forEach(message->{
            MessageAndUser messageAndUser = new MessageAndUser();
            BeanUtils.copyProperties(message,messageAndUser);
            messageAndUser.setMessageId(message.getId());
            // 查下这个对话里面有几个未读信息
            Long aLong = messageMapper.selectCount(new QueryWrapper<Message>()
                    .eq("conversation_id", message.getConversationId())
                    .eq("status", 0)
                    .eq("to_id",userId));
            messageAndUser.setNoReadCount(aLong);
            int targetId = message.getFromId().intValue()==userId.intValue()?message.getToId():message.getFromId();
            User user = userMapper.selectById(targetId);
            BeanUtils.copyProperties(user,messageAndUser);
            messageAndUser.setUserId(user.getId());
            messageAndUsers.add(messageAndUser);
        });
        return messageVo;
    }

    @Override
    public void updateStatus(String conversationId, Integer userId) {
        messageMapper.updateStatus(conversationId,userId);
    }

    @Override
    public void sendMessage(SendMessage sendMessage) {
        Integer toId = sendMessage.getToId();
        System.out.println(sendMessage);
        if(toId ==null || toId == 0) {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", sendMessage.getUsername()));
            if(user == null) throw new MyException("接收消息的人不存在");
            if(user.getStatus() == 0)throw new MyException("接收消息的人还未激活");
            toId = user.getId();
        }
        Message message = new Message();
        BeanUtils.copyProperties(sendMessage,message);
        String conversationId = sendMessage.getFromId().intValue() < toId.intValue()
                ? sendMessage.getFromId()+"_"+toId:toId+"_"+sendMessage.getFromId();
        message.setConversationId(conversationId);
        message.setStatus(0);
        message.setToId(toId);
        messageMapper.insert(message);
    }

    @Override
    public Map<String,Long> getSystemMessage(Integer userId) {
        Long noReadLike = messageMapper.selectCount(new QueryWrapper<Message>()
                .eq("from_id", 1)
                .eq("to_id", userId)
                .eq("conversation_id", "like")
                .eq("status", 0));
        Long noReadComment = messageMapper.selectCount(new QueryWrapper<Message>()
                .eq("from_id", 1)
                .eq("to_id", userId)
                .eq("conversation_id", "comment")
                .eq("status", 0));
        HashMap<String,Long> map = new HashMap<>();
        map.put("noReadLike",noReadLike);
        map.put("noReadComment",noReadComment);
        return map;
    }

    @Override
    public List<Message> getMessage(Integer userId, String type,Integer currentPage) {
        PageHelper.startPage(currentPage,10);
        Page<Message> page = messageMapper.selectSystemMessage(userId,type);
        List<Message> result = page.getResult();
        return result;
    }
}
