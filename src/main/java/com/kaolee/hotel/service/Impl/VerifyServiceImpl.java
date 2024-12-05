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

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class VerifyServiceImpl implements VerfyService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean verifyEmail(String email) {
        validateEmail(email);
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * 驗證Email格式
     * @param email
     * @return
     */
    public static void validateEmail(String email) {
        final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        boolean isValid = Pattern.compile(EMAIL_REGEX).matcher(email).matches();
        if (!isValid) {
            throw new EmailFormatException(MessageConstant.EMAIL_FORMAT_ERROR);
        }
    }
}
