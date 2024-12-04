package com.kaolee.hotel.controller;

import com.kaolee.hotel.pojo.dto.CulinaryDTO;
import com.kaolee.hotel.pojo.po.CulinaryPO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.service.CulinaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 查詢美味佳餚
     * @return
     */
    @Operation(tags = {"Admin/Culinary - 美味佳餚管理"},summary = "查詢美味佳餚")
    @GetMapping
    public Response<List<CulinaryPO>> getAll(){
        List<CulinaryPO> culinaryPOS = culinaryService.getAll();
        return Response.success(culinaryPOS);
    }

    /**
     * 更新美味佳餚
     * @param id
     * @param culinaryDTO
     * @return
     */
    @Operation(tags = {"Admin/Culinary - 美味佳餚管理"},summary = "更新美味佳餚")
    @PutMapping("/{id}")
    public Response<CulinaryPO> updateCulinary(@PathVariable String id, @RequestBody CulinaryDTO culinaryDTO){
        CulinaryPO culinaryPO = culinaryService.update(id,culinaryDTO);
        return Response.success(culinaryPO);
    }

    @Operation(tags = {"Admin/Culinary - 美味佳餚管理"},summary = "刪除美味佳餚")
    @DeleteMapping("/{id}")
    public Response<CulinaryPO> deleteCulinary(@PathVariable String id){
        CulinaryPO culinaryPO = culinaryService.delete(id);
        return Response.success(culinaryPO);
    }

}
