package com.famsun.momapi.core.handler;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class GlobalMetaHandler extends BaseHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 仅执行通用填充
        fillCommonInsert(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 仅执行通用填充
        fillCommonUpdate(metaObject);
    }
}
