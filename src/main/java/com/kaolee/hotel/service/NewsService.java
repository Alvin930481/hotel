package com.kaolee.hotel.service;

import com.kaolee.hotel.pojo.dto.NewsDTO;
import com.kaolee.hotel.pojo.entity.NewsPO;

import java.util.List;

public interface NewsService {

    /**
     * 新增最新消息
     * @param newsDTO
     * @return
     */
    NewsPO save(NewsDTO newsDTO);

    /**
     * 取得所有最新消息
     * @return
     */
    List<NewsPO> getAll();

    /**
     * 更新最新消息
     * @param newsDTO
     * @return
     */
    NewsPO update(String id,NewsDTO newsDTO);

    /**
     * 刪除最新消息
     * @param id
     * @return
     */
    NewsPO delete(String id);

    /**
     * 根據ＩＤ取得最新消息
     * @param id
     * @return
     */
    NewsPO getById(String id);
}
