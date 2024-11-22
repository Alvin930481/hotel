package com.kaolee.hotel.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class User {
    @Operation(description = "測試用的",summary = "this is summary", method = "this is method", tags = {"tag1","tag2"})
    @GetMapping
    public void test(){
        log.info("test");
    }
}
