package com.famsun.momapi.app.controller;

import com.famsun.momapi.app.service.BaseAppInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
