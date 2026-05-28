package com.famsun.momapi.app.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.famsun.momapi.app.entity.BaseAppInfo;
import com.famsun.momapi.app.mapper.BaseAppInfoMapper;
import com.famsun.momapi.core.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BaseAppInfoService extends BaseService<BaseAppInfoMapper, BaseAppInfo> {

    public void doTest() {
        BaseAppInfo d = this.baseMapper.selectOne(new LambdaQueryWrapper<BaseAppInfo>()
                .eq(BaseAppInfo::getAppid, ""));
        List<BaseAppInfo> dList = this.baseMapper.selectList(new LambdaQueryWrapper<>());
    }
}
