package com.hnucm.xinglinonlineschool.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hnucm.xinglinonlineschool.pojo.User;
import com.hnucm.xinglinonlineschool.pojo.UserLogin;

import java.util.Date;

public class JwtUtils {

    /**
     * 根据UserLogin生成token,默认为30分钟过期
     * @param userLogin
     * @return
     */
    public static String newToken(UserLogin userLogin) {
        // 为一个用户设置token并设置默认到期时间
        return newToken(userLogin, 60);  //默认三十分钟
    }

    /**
     *
     * @param userLogin
     * @param expiredSeconds
     * @return
     */
    public static String newToken(UserLogin userLogin, long expiredSeconds) {
        return JWT.create()
                .withAudience(new Integer(userLogin.getId()).toString())
                // 发布时间
                .withIssuedAt(new Date())
                // 到期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + expiredSeconds * 1000))
                // 选择加密方式
                .sign(Algorithm.HMAC256(userLogin.getPassword()));
    }

    /**
     * 校验token是否合法
     *
     * @param token 需要校验的token
     * @return 是否合法
     */
    public static boolean checkToken(UserLogin userLogin, String token) {
        // 根据校验规则HMAC256生成校验对象
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(userLogin.getPassword())).build();
        try {
            // 校验token是否合法
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 刷新token
     *
     * @param token 原token
     * @return 新token
     */
    public static String refreshToken(UserLogin userLogin, String token) {
        // 进行Base64解码
        DecodedJWT jwt = JWT.decode(token);
        // 获得解码后token里的负载中的用户ID
        int userId = jwt.getClaim("id").asInt();
        // 调用上面的创建token的方法创建新的token
        return newToken(userLogin);
    }


    public static int getId(String token) {
        // 进行Base64解码
        DecodedJWT jwt = JWT.decode(token);
        // 获得解码后token里的负载中的用户ID
        int id = jwt.getClaim("id").asInt();
        // 调用上面的创建token的方法创建新的token
        return id;
    }

    /**
     * token到期时间高于80%,就返回给他一个新的token,并做校验
     *
     * @param token
     * @return
     */
    public static String autoRequire(UserLogin userLogin, String token) {
        // 校验token
        boolean check = checkToken(userLogin, token);
        if (check) {
            // 解码
            DecodedJWT jwt = JWT.decode(token);
            // 计算时间是否超过80%
            long current = System.currentTimeMillis() / 1000;
            // 获取开始时间
            Long start = jwt.getClaim("iat").asLong();
            // 获取结束时间
            Long end = jwt.getClaim("exp").asLong();
            if ((current - start) * 1.0 / (end - start) > 0.8) {
                // 时间超过80%返回新的token
                return refreshToken(userLogin, token);
            } else {
                // 返回原来的token
                return token;
            }
        } else {
            throw new JWTVerificationException("token不合法");
        }
    }

    public static void main(String[] args){
        UserLogin userLogin = new UserLogin();
        userLogin.setId(4);
        userLogin.setUsername("2367407292@qq.com");
        userLogin.setPassword("a12345");
        String token = newToken(userLogin);
        System.out.println("token:"+token);

//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0IiwiZXhwIjoxNjA4NzM0MzExLCJpYXQiOjE2MDg3MzQyNTF9.AvB0XXJuxT65HwscBS6oTKJHrb7k_S1TwdO0pYgvaSY";
        boolean isRight = checkToken(userLogin, token);
        System.out.println("isRight:"+isRight);
    }

}
