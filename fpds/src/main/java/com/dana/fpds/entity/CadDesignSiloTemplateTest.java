package com.dana.fpds.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dana.common.db.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * CAD_DESIGN_SILO_TEMPLATE_TEST
 *
 * @author generator
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("CAD_DESIGN_SILO_TEMPLATE_TEST")
public class CadDesignSiloTemplateTest extends BaseEntity {

    @TableId(type = IdType.INPUT)
    private String tmplId;

    private String tmplName;

    private String version;

    private Integer isIscurver;

    private Integer status;
}
