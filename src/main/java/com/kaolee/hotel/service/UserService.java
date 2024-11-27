package com.kaolee.hotel.service;

import com.kaolee.hotel.pojo.dto.LoginInfo;
import com.kaolee.hotel.pojo.po.UserPO;

public interface UserService {

    /**
     * 登入操作
     * @param loginInfo
     */
    UserPO login(LoginInfo loginInfo);
}
