package com.kaolee.hotel.controller.home;

import com.kaolee.hotel.pojo.entity.CulinaryPO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.service.CulinaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/home/culinary")
public class CulinaryController {
    @Autowired
    private CulinaryService culinaryService;

    /**
     * 取得所有美味佳餚
     * @return
     */
    @Operation(tags = {"Home/Culinary - 美味佳餚"},summary = "取得所有美味佳餚")
    @GetMapping
    public Response<List<CulinaryPO>> getAllCulinary(){
        List<CulinaryPO> all = culinaryService.getAll();
        return Response.success(all);
    }

    /**
     * 依據ＩＤ取得美味佳餚
     * @return
     */
    @Operation(tags = {"Home/Culinary - 美味佳餚"},summary = "依據ＩＤ取得美味佳餚")
    @GetMapping("/{id}")
    public Response<CulinaryPO> getAllCulinary(@PathVariable String id){
        CulinaryPO culinaryPO = culinaryService.getById(id);
        return Response.success(culinaryPO);
    }
}
