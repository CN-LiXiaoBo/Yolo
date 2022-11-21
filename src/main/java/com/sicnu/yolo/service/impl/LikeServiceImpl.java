package com.sicnu.yolo.service.impl;

import com.sicnu.yolo.constant.YoloConstant;
import com.sicnu.yolo.entity.Event;
import com.sicnu.yolo.entity.Message;
import com.sicnu.yolo.event.EventProducer;
import com.sicnu.yolo.service.MessageService;
import com.sicnu.yolo.utils.RedisKeyUtils;
import com.sicnu.yolo.entity.dto.Like;
import com.sicnu.yolo.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @className: LikeServiceImpl
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/10 16:10
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private EventProducer eventProducer;
    @Override
    public void like(Like like) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String key = RedisKeyUtils.getEntityLikeKey(like.getEntityType(), like.getEntityId());
                String userLikeKey = RedisKeyUtils.getUserLike(like.getEntityUseId());
                Boolean isMember = redisOperations.opsForSet().isMember(key, like.getUserId());
                redisOperations.multi();
                if(isMember){
                    redisOperations.opsForSet().remove(key,like.getUserId());
                    redisOperations.opsForValue().decrement(userLikeKey);
                }else{
                    redisOperations.opsForSet().add(key,like.getUserId());
                    redisOperations.opsForValue().increment(userLikeKey);
                    Event event = new Event();
                    event.setEntityType(like.getEntityType())
                            .setEntityUserId(like.getEntityUseId())
                            .setEntityId(like.getEntityId())
                            .setTopic(YoloConstant.TOPIC_LIKE)
                            .setUserId(like.getUserId());
                    eventProducer.fireEvent(event);
                }
                return redisOperations.exec();
            }
        });
    }

    @Override
    public long likeCount(Like like) {
        String key = RedisKeyUtils.getEntityLikeKey(like.getEntityType(), like.getEntityId());
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Boolean likeStatus(Like like) {
        String key = RedisKeyUtils.getEntityLikeKey(like.getEntityType(), like.getEntityId());
        return redisTemplate.opsForSet().isMember(key, like.getUserId());
    }

    @Override
    public long likeUser(Integer userId) {
        String userLikeKey = RedisKeyUtils.getUserLike(userId);
        Object o = redisTemplate.opsForValue().get(userLikeKey );
        if(o == null) return 0;
        return (int)o;
    }

}
