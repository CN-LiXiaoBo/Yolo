package com.sicnu.yolo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @className: EventContent
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/12 13:03
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventContent {
    private Integer userId;
    private Integer entityType;
    private Integer entityId;
}
