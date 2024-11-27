package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.EmailNotFoundException;
import com.kaolee.hotel.pojo.dto.LoginInfo;
import com.kaolee.hotel.pojo.po.UserPO;
import com.kaolee.hotel.repository.UserRepository;
import com.kaolee.hotel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * 用戶登入操作
     *
     * @param loginInfo
     */
    @Override
    public UserPO login(LoginInfo loginInfo) {
        //依照email查詢用戶
        UserPO user = userRepository.findByEmail(loginInfo.getEmail());
        //如果查詢不到，表示沒有該用戶，返回“使用者不存在”
        if (user==null){
            throw new EmailNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        //如果有該用戶
        //密碼不正確，返回“密碼錯誤”
        String password = user.getPassword();
        if (!password.equals(loginInfo.getPassword())){
            throw new EmailNotFoundException(MessageConstant.PASSWORD_IS_WRONG);
        }
        //如果密碼正確，產生jwt並返回
        log.info("使用者登錄密碼正確，產生JWT...");

        return user;
    }
}
