package com.kaolee.hotel.filter.jwt;

import com.kaolee.hotel.constant.MessageConstant;
import com.kaolee.hotel.controller.login.UserLoginInfo;
import com.kaolee.hotel.exception.JwtException;
import com.kaolee.hotel.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtService;
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
                Claims claims = jwtService.extractAllClaims(jwtToken);

                UserLoginInfo userLoginInfo = UserLoginInfo.builder()
                        .userId(claims.getId())
                        .email(claims.getSubject())
                        .build();

                MyJwtAuthentication authentication = new MyJwtAuthentication();
                authentication.setJwtToken(jwtToken);
                authentication.setAuthenticated(true); // 上面解析都沒有錯誤，驗證通過。
                authentication.setCurrentUser(userLoginInfo);
                // 驗證通過後，一定要設定到SecurityContextHolder裡面去。
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (ExpiredJwtException e) {
                //jwt過期
                throw new JwtException(MessageConstant.JWT_EXPIRED);
            } catch (Exception e) {
                throw new JwtException("JWT 無效");
            }
        }


     /*   if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.validateToken(jwt, email)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        email, null, null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }*/
        //放行
        filterChain.doFilter(request, response);
    }
}
