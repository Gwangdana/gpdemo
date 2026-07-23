package com.dana.common.db;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审计字段基类（不含主键）
 * <p>
 * 所有业务实体的公共父类，提供创建/修改/删除审计字段和时间戳。
 * 主键由各业务实体自行定义（如 Oracle 用业务 UUID，MySQL 用雪花 ID）。
 *
 * @see IdBaseEntity 需要雪花 ID 主键的实体继承此类
 */
@Data
public abstract class BaseEntity {

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime addTime;

    @TableField(fill = FieldFill.INSERT)
    private String addUserId;

    @TableField(fill = FieldFill.INSERT)
    private String addUserName;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUserId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUserName;

    private LocalDateTime deleteTime;

    private String deleteUserId;

    private String deleteUserName;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long timeStamp;
}
