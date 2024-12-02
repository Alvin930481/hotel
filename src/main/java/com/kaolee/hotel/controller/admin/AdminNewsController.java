package com.kaolee.hotel.controller.admin;

import com.kaolee.hotel.pojo.dto.NewsDTO;
import com.kaolee.hotel.pojo.po.NewsPO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/admin")
public class AdminNewsController {
    @Autowired
    private NewsService newsService;

    /**
     * 新增最新消息
     * @param newsDTO
     * @return
     */
    @Operation( tags = {"Admin/News - 最新消息管理"},summary = "新增最新消息")
    @PostMapping
    public Response<NewsPO> save(NewsDTO newsDTO){
        NewsPO newsPO = newsService.save(newsDTO);
        return Response.success(newsPO);
    }

    /**
     * 取得所有最新消息
     * @return
     */
    @Operation( tags = {"Admin/News - 最新消息管理"},summary = "取得所有最新消息")
    @GetMapping
    public Response<List<NewsPO>> getAllNews(){
        List<NewsPO> newsPOS = newsService.getAll();
        return Response.success(newsPOS);
    }
}
