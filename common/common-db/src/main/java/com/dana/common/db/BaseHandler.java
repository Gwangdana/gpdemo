package com.dana.common.db;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 自动填充抽象基类
 * <p>
 * 各微服务继承此类并注册为 @Component，实现自己的操作人填充逻辑。
 * <ul>
 *   <li>insertFill：插入时自动填充 addTime、updateTime、timeStamp</li>
 *   <li>updateFill：更新时自动填充 updateTime、timeStamp</li>
 *   <li>fillCommonDelete：逻辑删除时手动调用，填充 deleteTime、timeStamp</li>
 * </ul>
 */
public abstract class BaseHandler implements MetaObjectHandler {

    /**
     * 通用插入填充（时间字段）
     */
    protected void fillCommonInsert(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        strictInsertFill(metaObject, "addTime", () -> now, LocalDateTime.class);
        strictInsertFill(metaObject, "updateTime", () -> now, LocalDateTime.class);
        strictInsertFill(metaObject, "timeStamp", System::currentTimeMillis, Long.class);
    }

    /**
     * 通用更新填充（时间字段）
     */
    protected void fillCommonUpdate(MetaObject metaObject) {
        strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        strictUpdateFill(metaObject, "timeStamp", System::currentTimeMillis, Long.class);
    }

    /**
     * 通用删除填充（逻辑删除时调用）
     * <p>
     * MyBatis-Plus 逻辑删除本质是 UPDATE，无法自动触发 delete 填充。
     * 业务层在执行逻辑删除前，手动调用此方法填充删除字段。
     *
     * @param entity    待删除的实体对象
     * @param userId    操作人ID
     * @param userName  操作人姓名
     */
    protected <T extends BaseEntity> void fillCommonDelete(T entity, String userId, String userName) {
        LocalDateTime now = LocalDateTime.now();
        entity.setDeleteTime(now);
        entity.setDeleteUserId(userId);
        entity.setDeleteUserName(userName);
        entity.setTimeStamp(System.currentTimeMillis());
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
