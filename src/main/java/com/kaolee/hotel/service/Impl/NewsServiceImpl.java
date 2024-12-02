package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.pojo.dto.NewsDTO;
import com.kaolee.hotel.pojo.po.NewsPO;
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

    @Override
    public NewsPO update(String id, NewsDTO newsDTO) {
        Optional<NewsPO> optionalNewsPO = newsRepository.findById(id);
        NewsPO newsPO = optionalNewsPO.orElseThrow(() -> new RuntimeException("News not found with id: " + id));
        BeanUtils.copyProperties(newsDTO, newsPO);
        newsRepository.save(newsPO);
        return newsRepository.findById(id).get();
    }
}
