package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.DiningTimeEmptyException;
import com.kaolee.hotel.pojo.dto.CulinaryDTO;
import com.kaolee.hotel.pojo.po.CulinaryPO;
import com.kaolee.hotel.repository.CulinaryRepository;
import com.kaolee.hotel.service.CulinaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CulinaryServiceImpl implements CulinaryService {
    @Autowired
    private CulinaryRepository culinaryRepository;

    @Override
    public CulinaryPO save(CulinaryDTO culinaryDTO) {
        //確認必填欄位diningTime
        String diningTime = culinaryDTO.getDiningTime();
        if (diningTime == null || diningTime == "") {
            throw new DiningTimeEmptyException(MessageConstant.DINGING_TIME_EMPTY);
        }
        CulinaryPO culinaryPO = new CulinaryPO();
        BeanUtils.copyProperties(culinaryDTO, culinaryPO);
        culinaryRepository.save(culinaryPO);
        culinaryPO = culinaryRepository.findByTitle(culinaryPO.getTitle());
        return culinaryPO;
    }
}