package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.EmailFormatException;
import com.kaolee.hotel.pojo.po.UserPO;
import com.kaolee.hotel.repository.UserRepository;
import com.kaolee.hotel.service.UserService;
import com.kaolee.hotel.service.VerfyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class VerifyServiceImpl implements VerfyService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean verifyEmail(String email) {
        //驗證email格式
        boolean validEmail = isValidEmail(email);
        if (!validEmail){
            throw new EmailFormatException(MessageConstant.EMAIL_FORMAT_ERROR);
        }
        //用email查詢用戶資料
        UserPO user = userRepository.findByEmail(email);
        if (user==null){
            return false;
        }
        return true;
    }

    /**
     * 驗證Email格式
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
