package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.pojo.dto.NewsDTO;
import com.kaolee.hotel.pojo.po.NewsPO;
import com.kaolee.hotel.repository.NewsRepository;
import com.kaolee.hotel.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;

    /**
     * 新增最新消息
     * @param newsDTO
     * @return
     */
    @Override
    public NewsPO save(NewsDTO newsDTO) {
        NewsPO newsPO = new NewsPO();
        BeanUtils.copyProperties(newsDTO,newsPO);
        newsRepository.save(newsPO);
        newsPO = newsRepository.findByTitle(newsDTO.getTitle());
        return newsPO;
    }
}
