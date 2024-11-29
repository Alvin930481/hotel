package com.kaolee.hotel.interceptor;


import com.kaolee.hotel.constant.JwtClaimsConstant;
import com.kaolee.hotel.context.BaseContext;
import com.kaolee.hotel.properties.JwtProperties;
import com.kaolee.hotel.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * jwt令牌校驗的攔截器
 */
@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校驗jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判斷當前攔截到的是Controller的方法還是其他資源
        if (!(handler instanceof HandlerMethod)) {
            //當前攔截到的不是動態方法，直接放行
            return true;
        }

        //1、從請求頭中獲取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());

        //2、校驗令牌
        try {
            log.info("jwt校驗:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            String userId = claims.get(JwtClaimsConstant.USER_ID).toString();
            log.info("目前用戶id：", userId);
            //把目前登陸的用戶id放到執行序的暫存
            BaseContext.setCurrentId(userId);
            //3、通過，放行
            return true;
        } catch (Exception ex) {
            //4、不通過，響應401狀態
            response.setStatus(401);
            return false;
        }
    }
}
