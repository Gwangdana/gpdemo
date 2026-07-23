package com.dana.common.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 带雪花 ID 主键的实体基类
 * <p>
 * 继承 {@link BaseEntity} 的审计字段，并增加雪花算法主键。
 * 适用于 MySQL 等需要框架自动生成主键的场景。
 * <p>
 * Oracle 等业务主键为 UUID 的模块，请直接继承 {@link BaseEntity}。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class IdBaseEntity extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
}
