package com.famsun.momapi.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public abstract class BaseHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 新增时间：addTime（对应你的BaseEntity字段）
        this.strictInsertFill(metaObject, "addTime", LocalDateTime::now, LocalDateTime.class);
        // 更新时间：updateTime（新增时和创建时间一致）
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
    }

    /**
     * 更新操作：自动更新更新时间
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
    }
}
