package com.kaolee.hotel.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

/**
 * JWT令牌生成及解析
 */

public class JwtUtil {
    /**
     * 生成jwt
     * 使用Hs256算法, 私匙使用固定密鑰
     *
     * @param secretKey jwt密鑰
     * @param ttlMillis jwt過期時間(毫秒)
     * @param claims    自定義的資訊
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定簽章的时候使用的簽章算法，也就是header那部分
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的時間
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        // 設置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有聲明，一定要先設定這個自己創建的私有的聲明，這個是給builder的claim賦值，一旦寫在標準的聲明賦值之後，就是覆蓋了那些標準的聲明的
                .setClaims(claims)
                //設定簽名使用的簽名演算法和簽名使用的秘鑰
                .signWith(key)
                //設定過期時間
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * Token解密
     *
     * @param secretKey jwt密鑰 此密鑰一定要保留好在伺服器端, 不能暴露出去, 否则sign就可以被偽造, 如果對接多個客户端建議改造成多個
     * @param token     加密後的token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) throws NoSuchAlgorithmException {
        /*// 得到DefaultJwtParser
        Claims claims = Jwts.parser()
                // 設置簽章的密鑰
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                // 設置需要解析的jwt
                .parseClaimsJws(token).getBody();*/

        // 直接使用符合長度要求的密鑰
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 使用新 API 解析 JWT
        return Jwts.parserBuilder()
                .setSigningKey(key) // 設置簽名密鑰
                .build() // 構建解析器
                .parseClaimsJws(token) // 解析 JWT
                .getBody();
    }


}
