package com.kaolee.hotel.service;

import com.kaolee.hotel.pojo.dto.NewsDTO;
import com.kaolee.hotel.pojo.po.NewsPO;

public interface NewsService {

    /**
     * 新增最新消息
     * @param newsDTO
     * @return
     */
    NewsPO save(NewsDTO newsDTO);
}
