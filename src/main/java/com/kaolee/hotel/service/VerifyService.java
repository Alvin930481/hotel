package com.kaolee.hotel.service;

import com.kaolee.hotel.pojo.dto.EmailDTO;

public interface VerifyService {

    /**
     * 驗證信箱
     */
    boolean verifyEmail(String email);
/*
    *//**
     * 生成信箱驗證碼
     * @param emailDTO
     *//*
    void generateEmailCode(EmailDTO emailDTO);
    */
}
