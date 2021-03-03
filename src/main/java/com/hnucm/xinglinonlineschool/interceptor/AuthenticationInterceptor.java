package com.hnucm.xinglinonlineschool.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hnucm.xinglinonlineschool.annotation.PassToken;
import com.hnucm.xinglinonlineschool.annotation.UserLoginToken;
import com.hnucm.xinglinonlineschool.pojo.User;
import com.hnucm.xinglinonlineschool.pojo.UserLogin;
import com.hnucm.xinglinonlineschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 *  拦截器用于做token验证
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                System.out.println("userid:"+userId);
                UserLogin user = userService.findUserLoginById(new Integer(userId));
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                    //TODO  判断是否需要刷新token
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
        //TODO   判断token是否快过期，快过期就重新设置token
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(o instanceof HandlerMethod)) {
            return ;
        }
        HandlerMethod handlerMethod = (HandlerMethod) o;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return ;
            }
        }
        // 执行认证
        if (token == null) {
            return;
        }else{
            //判断token是否快过期,在判断是否刷新token
            System.out.println("tokenObject:"+JWT.decode(token).getAudience());
            String userId = JWT.decode(token).getAudience().get(0);
            UserLogin user = userService.findUserLoginById(new Integer(userId));
            String newtoken = userService.getToken(user);
            System.out.println("newtoken:"+newtoken);
            httpServletResponse.setHeader("token",newtoken);
            System.out.println("我执行了");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
}