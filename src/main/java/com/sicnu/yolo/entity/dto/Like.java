package com.sicnu.yolo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @className: Like
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/10 16:08
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private Integer userId;
    private Integer entityType;
    private Integer entityId;
    private Integer entityUseId;
}
