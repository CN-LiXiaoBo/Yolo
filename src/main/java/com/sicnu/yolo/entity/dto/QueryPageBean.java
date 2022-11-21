package com.sicnu.yolo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @className: QueryPageBean
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/1 19:30
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryPageBean {
    private Integer currentPage;
    private Integer pageSize;
    private String queryString;
}
