package com.hnucm.xinglinonlineschool.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnucm.xinglinonlineschool.dao.CourseMapper;
import com.hnucm.xinglinonlineschool.dao.TradeMapper;
import com.hnucm.xinglinonlineschool.dao.UserMapper;
import com.hnucm.xinglinonlineschool.dao.plus.UserLoginMappers;
import com.hnucm.xinglinonlineschool.pojo.*;
import com.hnucm.xinglinonlineschool.service.UserService;
import com.hnucm.xinglinonlineschool.utils.DateUtils;
import com.hnucm.xinglinonlineschool.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    TradeMapper tradeMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    UserLoginMappers userLoginMappers;

    @Override
    public List<User>  findAllUser() {
        System.out.println("我是service，我执行了。");
        return userMapper.queryAllUsers();
    }

    @Transactional
    @Override
    public int registerUser(UserLogin user) {        //注册用户
        user.setPassword(MD5Util.getMD5Str(user.getPassword()));    //密码加密
        User users = new User();
        users.setEmail(user.getUsername());
        users.setRegisterDate(DateUtils.getDate());
        users.setType(user.getType());
        userMapper.insertUser(users);
        user.setUid(users.getId());
        return userMapper.insertUserLogin(user);
    }


    @Transactional
    @Override
    public int updateMess(User user) {          //更新和完善用户信息
        return userMapper.updateMess(user);
    }

    @Transactional
    @Override
    public int updatePassword(UserLogin user) {      //修改密码
        user.setPassword(MD5Util.getMD5Str(user.getPassword()));    //加密
        return userMapper.updatePassword(user);
    }

    @Override
    public boolean emailIsExist(String email) {     //验证邮箱是否存在
        if(userMapper.emailIsExist(email)>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int telIsExist(String tel) {     //验证邮箱是否存在
        return userMapper.telIsExist(tel);
    }

    @Override
    public User login(UserLogin user) {       //登录验证,并返回带token的User信息
        UserLogin userLogin = userMapper.queryUserLoginByUsername(user.getUsername());
        if(MD5Util.getMD5Str(user.getPassword()).equals(userLogin.getPassword())){
            System.out.println("user:"+user);
            User users = findUserById(userLogin.getUid());
            //Todo  不放在body里面
           /* users.setToken(getToken(userLogin));*/
            return users;
        }else{
            return null;
        }
    }

    @Override
    public boolean queryUsername(String username) {
        if(userMapper.emailIsExist(username)>0){
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    @Override
    public Trade handleTrade(Trade trade) {
        //提交订单信息，并修改用户余额
        //TODO 验证余额
        int balance = userMapper.queryUserBalanceById(trade.getUid());
        Course course = courseMapper.findCourseById(trade.getCid());
        trade.setNumOfXL(course.getPrice());// 得到课程的花销
        if(balance-trade.getNumOfXL()>=0){
            trade.setDate(DateUtils.getDate());
            tradeMapper.addTradeReturnId(trade);
            trade.setNumOfXL(0-trade.getNumOfXL());
            userMapper.updateUserBalance(trade);
            return trade;
        }else{
            return null;
        }
    }

    @Override
    public String fingUserheadImgById(int id) {
        return userMapper.fingUserheadImgById(id);
    }

    @Override
    public String getToken(UserLogin userLogin) {       //根据用户的id生成token
        System.out.println("UserLogin:"+userLogin);
        String token= JWT.create()
                .withAudience(new Integer(userLogin.getId()).toString())
                // 发布时间
                .withIssuedAt(new Date())
                // 到期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800 * 1000))  //半小时过期
                // 选择加密方式
                .sign(Algorithm.HMAC256(userLogin.getPassword()));
        return token;
    }



    @Override
    public User findUserById(int id) {                  //根据用户的id查找用户信息
        return userMapper.findUserById(id);
    }

    @Override
    public UserLogin findUserLoginById(int id) {                  //根据用户的id查找用户的登录信息
        return userMapper.findUserLoginById(id);
    }

    @Override
    public int updateUserHeadImg(String src, int id) {
        User user = new User();
        user.setId(id);
        user.setImgUrl(src);
        return userMapper.updateUserHeadImg(user);
    }



    @Override
    public int findUserByName(String username) {
        return userMapper.findUserByName(username);
    }

    @Override
    public UserLogin findUserLoginByUid(int id) {
        return userMapper.findUserLoginByUid(id);
    }

    @Override
    public List<Trade> queryAllTrade() {
        return tradeMapper.queryAllTrade();
    }

    @Override
    public int resetPwdById(int id) {          //充值密码
        UserLogin userLogin = new UserLogin();
        userLogin.setId(id);
        userLogin.setPassword(MD5Util.getMD5Str("123456"));
        return userMapper.updatePassword(userLogin);
    }

    @Override
    public UserLogin findUserLoginByUserName(String username) {
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return userLoginMappers.selectOne(queryWrapper);
    }

}
