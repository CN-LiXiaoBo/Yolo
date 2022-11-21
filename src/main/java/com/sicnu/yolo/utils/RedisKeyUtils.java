package com.sicnu.yolo.utils;

/**
 * @className: RedisKeyUtils
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/10 15:26
 */
public class RedisKeyUtils {
    private static final String  SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    private static final String PREFIX_FOLLOWEE = "followee";
    private static final String PREFIX_FOLLOWER = "follower";
    private static final String PREFIX_TICKET = "ticket";

    public static String getEntityLikeKey(int entityType,int entityId){
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getUserLike(int userId){
        return PREFIX_USER_LIKE+SPLIT+userId;
    }

    // 某个用户关注的实体
    //followee:userId:entityType - > zset(entityId,now)
    public static String getFollowee(int userId,int entityType){
        return PREFIX_FOLLOWEE+SPLIT+userId+SPLIT+entityType;
    }
    //某个实体被那些人关注
    //follower:entityType:entityId - > zser(userId,now)
    public static String getFollower(int entityType,int entityId){
        return PREFIX_FOLLOWER+SPLIT+entityType+SPLIT+entityId;
    }
    public static String getTicket(String ticket){
        return PREFIX_TICKET+SPLIT+ticket;
    }
}
