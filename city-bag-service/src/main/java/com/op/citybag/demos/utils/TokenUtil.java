package com.op.citybag.demos.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TokenUtil {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "reflash_token";

    public static final String USER_ID = "userId";
    public static final String TYPE = "type";
    public static final String ID = "id";

    /**
     * 生成AccessToken
     *
     * @param userId 用户的业务id
     * @return 返回生成的AccessToken
     */
    public static String getAccessToken(String userId) {
        //存储数据
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, userId);
        claims.put(TYPE, ACCESS_TOKEN);
        claims.put(ID, SnowflakeIdWorker.nextId());

        long expiration = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);

        // 生成JWT令牌
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS384, ACCESS_TOKEN)
                .setClaims(claims)
                .setExpiration(new Date(expiration))
                .compact();

        return jwt;
    }

    /**
     * 用于生成RefreshToken
     *
     * @param userId 用户的业务id
     * @return 返回生成的RefreshToken
     */
    public static String getRefreshToken(String userId) {
        //存储数据
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, userId);
        claims.put(TYPE, REFRESH_TOKEN);
        claims.put(ID, SnowflakeIdWorker.nextId());

        long expiration = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30);
        // 生成JWT令牌
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS384, REFRESH_TOKEN)
                .setClaims(claims)
                .setExpiration(new Date(expiration))
                .compact();

        return jwt;
    }

    /**
     * 校验AccessToken是否合法
     *
     * @param token 传入的AccessToken
     * @return 返回校验结果
     */
    public static boolean checkAccessToken(String token) {
        // 验证AccessToken
        try {
            Jwts.parser().setSigningKey(ACCESS_TOKEN).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 校验ReflashToken是否合法
     *
     * @param token     传入的ReflashToken
     * @param autoLogin 是否为自动登录
     * @return 返回校验结果
     */

    public static boolean checkReflashToken(String token, Boolean autoLogin) {
        // 验证ReflashToken
        try {
            Jwts.parser().setSigningKey(REFRESH_TOKEN).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析AccessToken
     *
     * @param token 传入的AccessToken
     * @return 返回解析结果
     */
    public static Claims getClaimsByAccessToken(String token) {
        try {
            return Jwts.parser().setSigningKey(ACCESS_TOKEN).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解析ReflashToken
     *
     * @param token 传入的ReflashToken
     * @return 返回解析结果
     */
    public static Claims getClaimsByReflashToken(String token) {
        try {
            return Jwts.parser().setSigningKey(REFRESH_TOKEN).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

}
