-- ============================================
-- gpdemo 数据库初始化脚本
-- ============================================

CREATE DATABASE IF NOT EXISTS gpdemo DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE gpdemo;

-- 系统用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    username    VARCHAR(64)  NOT NULL COMMENT '用户名',
    password    VARCHAR(128) NOT NULL COMMENT '密码（BCrypt加密）',
    nickname    VARCHAR(64)  DEFAULT NULL COMMENT '昵称',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 默认管理员账号由 auth 服务的 DataInitializer 自动初始化（密码：admin123）
