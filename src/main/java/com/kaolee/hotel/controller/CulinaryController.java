package com.kaolee.hotel.controller;

import com.kaolee.hotel.pojo.dto.CulinaryDTO;
import com.kaolee.hotel.pojo.po.CulinaryPO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.service.CulinaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/culinary")
public class CulinaryController {
    @Autowired
    private CulinaryService culinaryService;


    /**
     * 新增美味佳餚
     * @param culinaryDTO
     * @return
     */
    @Operation(tags = {"Admin/Culinary - 美味佳餚管理"},summary = "新增美味佳餚")
    @PostMapping
    public Response<CulinaryPO> saveCulinary(@RequestBody CulinaryDTO culinaryDTO){
        CulinaryPO culinaryPO = culinaryService.save(culinaryDTO);
        return Response.success(culinaryPO);
    }
}
