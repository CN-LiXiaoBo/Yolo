package com.sicnu.yolo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @className: SendMessage
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/9 23:33
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendMessage {
    @NotNull(message = "发送人的id不能为空")
    private Integer fromId;
    private Integer toId;
    @NotBlank(message = "接收信息人的名字不能为空")
    private String username;
    @NotBlank(message = "发送信息不能为空")
    private String content;
}
