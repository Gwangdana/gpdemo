package com.famsun.momapi.app.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.famsun.momapi.app.entity.BaseAppInfo;
import com.famsun.momapi.app.service.BaseAppInfoService;
import com.famsun.momapi.core.constent.ResultCode;
import com.famsun.momapi.core.exception.GException;
import com.famsun.momapi.core.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Gwangdana
 * @since 2026-05-28
 */
@RestController
@RequestMapping("/baseAppInfo")
@RequiredArgsConstructor
public class BaseAppInfoController {

    private final BaseAppInfoService service;

    @GetMapping
    public void doDataText() {
        service.doTest();
    }

    @PostMapping
    public void doDataInsertTest() {
        service.doInsertTest();
    }

    @GetMapping("/listAll")
    public Result getDataList() {
        List<BaseAppInfo> findListAll = service.list();
        return Result.Success().withList(findListAll).build();
    }

    @GetMapping("/page")
    public Result getPageData(Page<BaseAppInfo> page) {
        return Result.Success().withPage(service.page(page)).build();
    }

}
