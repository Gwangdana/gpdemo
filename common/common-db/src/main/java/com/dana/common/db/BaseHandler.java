package com.dana.common.db;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 自动填充抽象基类
 * <p>
 * 各微服务继承此类并注册为 @Component，实现自己的操作人填充逻辑
 */
public abstract class BaseHandler implements MetaObjectHandler {

    /**
     * 通用插入填充（时间字段）
     */
    protected void fillCommonInsert(MetaObject metaObject) {
        strictInsertFill(metaObject, "addTime", LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
    }

    /**
     * 通用更新填充（时间字段）
     */
    protected void fillCommonUpdate(MetaObject metaObject) {
        strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        fillCommonInsert(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        fillCommonUpdate(metaObject);
    }
}
