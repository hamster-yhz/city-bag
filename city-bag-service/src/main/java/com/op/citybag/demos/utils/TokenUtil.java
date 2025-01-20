package com.op.citybag.demos.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    public static final String ACCESS_TOKEN = "access_token";

    public static final String USER_ID = "userId";
    public static final String PHONE = "phone";


    /**
     * 生成AccessToken
     * @param userId 用户的业务id
     * @param phone 用户的手机号
     * @return 返回生成的AccessToken
     */
    public static String getAccessToken(String userId,String phone) {
        //存储数据
        Map<String,Object> claims = new HashMap<>();
        claims.put(USER_ID,userId);
        claims.put(PHONE,phone);

        // 生成JWT令牌
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS384, ACCESS_TOKEN)
                .setClaims(claims)
                .compact();

        return jwt;
    }

    /**
     * 校验AccessToken是否合法
     * @param token 传入的AccessToken
     * @return 返回校验结果
     */
    public static boolean checkAccessToken(String token) {
        // 验证AccessToken
        try {
            Jwts.parser()
                    .setSigningKey(ACCESS_TOKEN)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据AccessToken获取用户
     * @param token 传入的AccessToken
     * @return 返回用户
     */
    public static Claims getClaimsByReflashToken(String token) {
        try {
            return Jwts.parser()
                  .setSigningKey(ACCESS_TOKEN)
                  .parseClaimsJws(token)
                      .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
