package com.dana.auth.config;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dana.auth.entity.SysUser;
import com.dana.auth.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 数据初始化（启动时自动创建默认管理员账号）
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SysUserMapper sysUserMapper;

    @Override
    public void run(String... args) {
        // 检查 admin 用户是否已存在
        SysUser admin = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, "admin")
        );

        if (admin == null) {
            SysUser newAdmin = new SysUser();
            newAdmin.setUsername("admin");
            newAdmin.setPassword(BCrypt.hashpw("admin123"));
            newAdmin.setNickname("系统管理员");
            newAdmin.setStatus(1);
            sysUserMapper.insert(newAdmin);
            log.info("已自动创建默认管理员账号：admin / admin123");
        }
    }
}
