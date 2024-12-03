package com.kaolee.hotel.service;

import com.kaolee.hotel.pojo.dto.CulinaryDTO;
import com.kaolee.hotel.pojo.po.CulinaryPO;

import java.util.List;

public interface CulinaryService {

    /**
     * 新增美味佳餚
     * @param culinaryDTO
     * @return
     */
    CulinaryPO save(CulinaryDTO culinaryDTO);

    /**
     * 查詢所有美味佳餚
     * @return
     */
    List<CulinaryPO> getAll();
}
