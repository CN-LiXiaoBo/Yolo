package com.sicnu.yolo.event;

import com.alibaba.fastjson.JSONObject;
import com.sicnu.yolo.constant.YoloConstant;
import com.sicnu.yolo.entity.DiscussPost;
import com.sicnu.yolo.entity.Event;
import com.sicnu.yolo.entity.EventContent;
import com.sicnu.yolo.entity.Message;
import com.sicnu.yolo.service.MessageService;
import com.sicnu.yolo.service.es.EsService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


/**
 * @className: EventConsumer
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/12 12:45
 */
@Component
public class EventConsumer {
    @Autowired
    private MessageService messageService;
    @Autowired
    private EsService esService;
    @KafkaListener(topics = {YoloConstant.TOPIC_LIKE,YoloConstant.TOPIC_COMMENT})
    public void handle(ConsumerRecord record){
        if(record == null || record.value() == null) return;
        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if(event == null) return;
        Message message = new Message();
        message.setFromId(1);
        message.setToId(event.getEntityUserId());
        message.setConversationId(event.getTopic());
        EventContent content = new EventContent();
        content.setUserId(event.getUserId());
        content.setEntityType(event.getEntityType());
        content.setEntityId(event.getEntityId());
        message.setContent(JSONObject.toJSONString(content));
        messageService.save(message);
    }
    @KafkaListener(topics = {YoloConstant.TOPIC_ES_DISCUSSPOST})
    public void handleEs(ConsumerRecord record){
        if(record == null || record.value() == null) return;
        DiscussPost discussPost = JSONObject.parseObject(record.value().toString(), DiscussPost.class);
        if(discussPost == null)return;
        esService.save(discussPost);
    }

}
