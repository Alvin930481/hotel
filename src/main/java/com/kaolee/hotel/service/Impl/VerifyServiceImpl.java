package com.kaolee.hotel.service.Impl;

import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.exception.EmailFormatException;
import com.kaolee.hotel.pojo.dto.EmailDTO;
import com.kaolee.hotel.pojo.entity.UserPO;
import com.kaolee.hotel.repository.UserRepository;
import com.kaolee.hotel.service.VerifyService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.regex.Pattern;

@Service
@Slf4j
public class VerifyServiceImpl implements VerifyService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private JavaMailSender mailSender;


    /**
     * 驗證信箱是否註冊
     *
     * @param email
     * @return
     */
    @Override
    public boolean verifyEmail(String email) {
        validateEmail(email);
        return userRepository.findByEmail(email).isPresent();
    }

/*

    @Override
    public void generateEmailCode(EmailDTO emailDTO) {
//        生成附帶驗證碼的ＪＷＴ（依原本為六碼大小寫英文及數字）
        String code = generateRandomCode();
//        修改該用戶資料（驗證碼）
        UserPO userPO = userRepository.findByEmail(emailDTO.getEmail()).get();
        userPO.setVerificationToken(code);
        userRepository.save(userPO);
//        寄送驗證碼
        String mailTo="alvin930481@gmail.com";
        String subject="Test";
        String body = "TestEmail";
        sendEmail(mailTo,subject,body);
    }
*/

    /**
     * 驗證Email格式
     *
     * @param email
     * @return
     */
    private static void validateEmail(String email) {
        final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        boolean isValid = Pattern.compile(EMAIL_REGEX).matcher(email).matches();
        if (!isValid) {
            throw new EmailFormatException(MessageConstant.EMAIL_FORMAT_ERROR);
        }
    }

    /**
     * 生成隨機碼
     * @return
     */
    private static String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = null;
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(62);
            code.append(characters.charAt(randomIndex));
        }
        return code.toString();
    }
/*
    *//**
     * 寄送郵件
     * @param to
     * @param subject
     * @param body
     *//*
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("alvin930487@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);  // 設置為 HTML 格式郵件

            mailSender.send(message);
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
        }
    }
    */
}
