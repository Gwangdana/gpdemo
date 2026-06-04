package com.famsun.momapi.app.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.famsun.momapi.app.entity.BaseAppInfo;
import com.famsun.momapi.app.mapper.BaseAppInfoMapper;
import com.famsun.momapi.core.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class BaseAppInfoService extends BaseService<BaseAppInfoMapper, BaseAppInfo> {

    public void doTest() {
        BaseAppInfo d = this.baseMapper.selectOne(new LambdaQueryWrapper<BaseAppInfo>()
                .eq(BaseAppInfo::getAppid, "1"));
        List<BaseAppInfo> dList = this.baseMapper.selectList(new LambdaQueryWrapper<>());
        log.info("TEST GOOD.");
    }

    public void doInsertTest() {
        BaseAppInfo d = new BaseAppInfo();
        d.setId(IdWorker.getId());
        d.setAppid("2");
        d.setAppName("gp_user_2");
        d.setAppkey("123456");
        d.setStatus("0");
        this.save(d);
    }
}
