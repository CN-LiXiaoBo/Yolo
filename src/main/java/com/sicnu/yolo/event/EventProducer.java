package com.sicnu.yolo.event;

import com.alibaba.fastjson.JSONObject;
import com.sicnu.yolo.entity.EsEvent;
import com.sicnu.yolo.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @className: EventProducer
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/12 12:43
 */
@Component
public class EventProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void fireEvent(Event event){
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
    public void fireEvent(EsEvent event){
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event.getDiscussPost()));
    }
}
