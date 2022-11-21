package com.sicnu.yolo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @className: PageResult
 * @description: 返回分页查询的结果
 * @author: 热爱生活の李
 * @since: 2022/7/1 19:39
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true) // 支持链式
public class PageResult {
    private Long total;
    private List data;
}
