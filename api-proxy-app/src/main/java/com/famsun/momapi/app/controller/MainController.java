package com.famsun.momapi.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class MainController {

    @GetMapping
    public void doGetTest() {
        log.info("GET TEST GOOD.");
    }

    @PostMapping
    public void doPostTest() {
        log.info("POST TEST GOOD.");
    }
}
