package com.sicnu.yolo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @className: MyMetaObjectHandler
 * @description: MybatisPlus自动注入功能
 * @author: 热爱生活の李
 * @since: 2022/7/1 19:34
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"createTime",Date.class,new Date());
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("update");
    }
}
