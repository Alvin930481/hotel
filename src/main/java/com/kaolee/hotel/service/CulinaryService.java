package com.kaolee.hotel.service;

import com.kaolee.hotel.pojo.dto.CulinaryDTO;
import com.kaolee.hotel.pojo.po.CulinaryPO;

public interface CulinaryService {

    /**
     * 新增美味佳餚
     * @param culinaryDTO
     * @return
     */
    CulinaryPO save(CulinaryDTO culinaryDTO);
}
