package com.sicnu.yolo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @className: EsEvent
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/13 17:36
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EsEvent {
    private String topic;
    private DiscussPost discussPost;
}
