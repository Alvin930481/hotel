package com.kaolee.hotel.controller.home;

import com.kaolee.hotel.pojo.entity.NewsPO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.service.NewsService;
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
@RequestMapping("/api/v1/home/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    /**
     * 取得所有最新消息
     * @return
     */
    @Operation(tags = {"Home/News - 最新消息"},summary = "取得所有最新消息")
    @GetMapping
    public Response<List<NewsPO>> getAllNews(){
        List<NewsPO> all = newsService.getAll();
        return Response.success(all);
    }

    /**
     * 依據ＩＤ取得最新消息
     * @return
     */
    @Operation(tags = {"Home/News - 最新消息"},summary = "依據ＩＤ取得最新消息")
    @GetMapping("/{id}")
    public Response<NewsPO> getAllNews(@PathVariable String id){
        NewsPO newsPO = newsService.getById(id);
        return Response.success(newsPO);
    }
}
