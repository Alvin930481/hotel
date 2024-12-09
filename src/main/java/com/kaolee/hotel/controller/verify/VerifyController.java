package com.kaolee.hotel.controller.verify;

import com.kaolee.hotel.pojo.dto.EmailDTO;
import com.kaolee.hotel.pojo.response.Response;
import com.kaolee.hotel.pojo.vo.IsMailExistVO;
import com.kaolee.hotel.service.VerifyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/Verify")
public class VerifyController {
    @Autowired
    private VerifyService verfyService;

    /**
     * verify email
     * @param email
     * @return
     */
    @Operation(tags = {"Verify - 驗證"}, summary = "驗證信箱是否註冊過")
    @PostMapping("/email")
    public Response<IsMailExistVO> verifyEmail(@RequestBody EmailDTO email){
        log.info("驗證信箱：{}",email.getEmail());
        boolean isMailExist = verfyService.verifyEmail(email.getEmail());
        IsMailExistVO isMailExistVO = new IsMailExistVO(isMailExist);
        return Response.success(isMailExistVO);
    }

/*

    */
/**
     * 生成信箱驗證碼並寄送
     * @param emailDTO
     * @return
     *//*

    @Operation(tags = {"Verify - 驗證"}, summary = "生成信箱驗證碼並寄送")
    @PostMapping("/generateEmailCode")
    public Response genetateEmailCode(@RequestBody EmailDTO emailDTO){
        verfyService.generateEmailCode(emailDTO);
        return Response.success();
    }
*/

}
