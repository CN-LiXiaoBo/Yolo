package com.sicnu.yolo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: UserMessage
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/9 20:14
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {
    // 未读信息总条数
    private long totalNoReadCount;
    private List<MessageAndUser> messageAndUsers;
}
