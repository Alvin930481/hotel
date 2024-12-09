package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.NewsNotFoundException;
import com.kaolee.hotel.pojo.dto.NewsDTO;
import com.kaolee.hotel.pojo.entity.NewsPO;
import com.kaolee.hotel.repository.NewsRepository;
import com.kaolee.hotel.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;

    /**
     * 新增最新消息
     *
     * @param newsDTO
     * @return
     */
    //TODO：尚未處理重複機制
    @Override
    public NewsPO save(NewsDTO newsDTO) {
        NewsPO newsPO = new NewsPO();
        BeanUtils.copyProperties(newsDTO, newsPO);
        newsRepository.save(newsPO);
        newsPO = newsRepository.findByTitle(newsDTO.getTitle());
        return newsPO;
    }


    /**
     * 取得所有最新消息
     *
     * @return
     */
    @Override
    public List<NewsPO> getAll() {
        List<NewsPO> newsPOS = new ArrayList<>();
        newsPOS = newsRepository.findAll();
        return newsPOS;
    }

    /**
     * 更新最新消息
     *
     * @param id
     * @param newsDTO
     * @return
     */
    @Override
    public NewsPO update(String id, NewsDTO newsDTO) {
        Optional<NewsPO> optionalNewsPO = newsRepository.findById(id);
        NewsPO newsPO = optionalNewsPO.orElseThrow(() -> new RuntimeException("News not found with id: " + id));
        BeanUtils.copyProperties(newsDTO, newsPO);
        newsRepository.save(newsPO);
        return newsRepository.findById(id).get();
    }

    /**
     * 刪除最新消息
     *
     * @param id
     * @return
     */
    @Override
    public NewsPO delete(String id) {
        //查詢要刪除的news是否存在
        Boolean newsExist = newsRepository.existsById(id);
        //不存在，拋出異常;存在，提取用戶並且刪除資料
        NewsPO newsPO = newsRepository
                .findById(id)
                .orElseThrow(() -> new NewsNotFoundException(MessageConstant.NEWS_NOT_FOUND));
        newsRepository.delete(newsPO);
        return newsPO;
    }

    /**
     * 根據ＩＤ取得所有最新消息
     * @param id
     * @return
     */
    @Override
    public NewsPO getById(String id) {
        Optional<NewsPO> optionalNewsPO = newsRepository.findById(id);
        return optionalNewsPO
                .orElseThrow(()->new NewsNotFoundException(MessageConstant.NEWS_NOT_FOUND));
    }
}
