package com.dana.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dana.auth.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户 Mapper
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
