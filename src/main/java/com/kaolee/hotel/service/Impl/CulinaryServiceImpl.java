package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.CulinaryNotFoundException;
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
import java.util.List;
import java.util.Optional;

@Service
public class CulinaryServiceImpl implements CulinaryService {
    @Autowired
    private CulinaryRepository culinaryRepository;

    @Override
    public CulinaryPO save(CulinaryDTO culinaryDTO) {
        //確認必填欄位diningTime
        String diningTime = culinaryDTO.getDiningTime();
        if (diningTime == null || diningTime.isEmpty()) {
            throw new DiningTimeEmptyException(MessageConstant.DINGING_TIME_EMPTY);
        }
        CulinaryPO culinaryPO = new CulinaryPO();
        BeanUtils.copyProperties(culinaryDTO, culinaryPO);
        culinaryRepository.save(culinaryPO);
        culinaryPO = culinaryRepository.findByTitle(culinaryPO.getTitle());
        return culinaryPO;
    }


    /**
     * 查詢所有美味佳餚
     * @return
     */
    @Override
    public List<CulinaryPO> getAll() {
        List<CulinaryPO> all = culinaryRepository.findAll();
        return all;
    }

    /**
     * 更新美味佳餚
     * @param id
     * @param culinaryDTO
     * @return
     */
    @Override
    public CulinaryPO update(String id, CulinaryDTO culinaryDTO) {
        //確認必填欄位diningTime
        String diningTime = culinaryDTO.getDiningTime();
        if (diningTime == null || diningTime.isEmpty()) {
            throw new DiningTimeEmptyException(MessageConstant.DINGING_TIME_EMPTY);
        }
        //確認資料庫是否有這條資料
        Optional<CulinaryPO> optionalCulinaryPO = culinaryRepository.findById(id);
        CulinaryPO culinaryPO =
                optionalCulinaryPO.orElseThrow(() -> new CulinaryNotFoundException(MessageConstant.CULINARY_NOT_FOUND));
        BeanUtils.copyProperties(culinaryDTO, culinaryPO);
        culinaryRepository.save(culinaryPO);
        culinaryPO = culinaryRepository.findById(id).get();
        return culinaryPO;
    }

    /**
     * 刪除美味佳餚
     * @param id
     * @return
     */
    @Override
    public CulinaryPO delete(String id) {
        Optional<CulinaryPO> optionalCulinaryPO = culinaryRepository.findById(id);
        CulinaryPO culinaryPO = optionalCulinaryPO.orElseThrow(() -> new CulinaryNotFoundException(MessageConstant.CULINARY_NOT_FOUND));
        culinaryRepository.deleteById(id);
        return culinaryPO;
    }

    @Override
    public CulinaryPO getById(String id) {
        Optional<CulinaryPO> optionalCulinaryPO = culinaryRepository.findById(id);

        return optionalCulinaryPO
                .orElseThrow(()->new CulinaryNotFoundException(MessageConstant.CULINARY_NOT_FOUND));
    }
}
