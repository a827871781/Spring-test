package com.test.demo.utils;

import cn.hutool.core.bean.BeanUtil;
import com.test.demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class TokenUtil {

    private static final String secret = "www.taihuiyuan.com";

    public static final String USERNAME = "user";


    public static final long TOKEN_EXPIRE_TIME = 86400000;


    public static String generateToken(String userId, Long expTime, Map<String, Object> claims) {
        long expireTime = System.currentTimeMillis() + TOKEN_EXPIRE_TIME;
        if (expTime != null) {
            expireTime = expTime.longValue();
        }
        Date expireDate = new Date(expireTime);

        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ", "JWT");
        builder.setSubject(userId);
//        builder.setIssuer("");
        builder.setIssuedAt(new Date());
        builder.setExpiration(expireDate);
        builder.signWith(SignatureAlgorithm.HS256, secret);
        if (claims != null && claims.size() > 0) {
            builder.addClaims(claims);
        }

        return builder.compact();
    }

    public static String generateToken(String userId, Long expTime) {
        return generateToken(userId, expTime, null);
    }


    public static String generateToken(String userId) {
        return generateToken(userId, null);
    }

    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 验证token是否过期失效
     *
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token) {
        return (parseToken(token) == null) ? true : parseToken(token).getExpiration().before(new Date());
    }

    /**
     * 获取token失效时间
     *
     * @param token
     * @return
     */
    public static Date getExpirationDateFromToken(String token) {
        return isTokenExpired(token) ? null : parseToken(token).getExpiration();
    }

    /**
     * 获取用户名从token中
     */
    public static String getUserIdFromToken(String token) {
        return isTokenExpired(token) ? null : parseToken(token).getSubject();
    }

    public static User getUserFromToken(String token) {
        return isTokenExpired(token) ? null : BeanUtil.toBean(parseToken(token).get(USERNAME), User.class);


    }


    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAtDateFromToken(String token) {
        return isTokenExpired(token) ? null : parseToken(token).getIssuedAt();
    }

}
