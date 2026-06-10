package com.famsun.momapi.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public abstract class BaseHandler implements MetaObjectHandler {

    // 抽通用填充方法，给所有子类调用
    protected void fillCommonInsert(MetaObject metaObject) {
        strictInsertFill(metaObject, "addTime", LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        // 你的原有操作人填充逻辑放这里
    }

    protected void fillCommonUpdate(MetaObject metaObject) {
        strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        // 你的原有更新人填充逻辑放这里
    }

    // 父类自身默认实现全局填充
    @Override
    public void insertFill(MetaObject metaObject) {
        fillCommonInsert(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        fillCommonUpdate(metaObject);
    }
}
