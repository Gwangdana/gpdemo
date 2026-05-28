package com.famsun.momapi.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.famsun.momapi.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gwangdana
 * @since 2026-05-28
 */
@Getter
@Setter
@ToString
@TableName("base_app_info")
public class BaseAppInfo extends BaseEntity {

    private String name;

    private String code;

    private String appid;

    private String appkey;

    private String appName;

    private String status;

    private String remark;

    private LocalDateTime updatdeTime;
}
