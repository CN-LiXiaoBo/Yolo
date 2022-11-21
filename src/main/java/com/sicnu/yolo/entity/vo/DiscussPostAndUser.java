package com.sicnu.yolo.entity.vo;

import com.sicnu.yolo.entity.DiscussPost;
import com.sicnu.yolo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @className: DiscussPostAndUser
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/1 21:12
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DiscussPostAndUser {
    private DiscussPost discussPost;
    private User user;
}
