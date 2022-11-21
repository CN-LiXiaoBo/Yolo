package com.sicnu.yolo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sicnu.yolo.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @className: UserMapper
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/1 20:22
 */
public interface UserMapper extends BaseMapper<User> {
    @Update({
            "update user set header_url = #{path} where id = #{userId}"
    })
    void updateHeaderUrlById(String path, Integer userId);

}
