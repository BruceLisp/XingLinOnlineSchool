package com.hnucm.xinglinonlineschool.service;

import com.hnucm.xinglinonlineschool.pojo.Trade;
import com.hnucm.xinglinonlineschool.pojo.User;
import com.hnucm.xinglinonlineschool.pojo.UserLogin;

import java.util.List;


public interface UserService {
    public List<User> findAllUser();
    public int registerUser(UserLogin user);
    public int updateMess(User user);
    public int updatePassword(UserLogin user);
    public boolean emailIsExist(String email);
    public int telIsExist(String tel);          //验证电话是否存在
    public User login(UserLogin user);
    public boolean queryUsername(String username);     //判断用户名是否存在
    public Trade handleTrade(Trade trade);
    public String fingUserheadImgById(int id);         //根据用户的id查找用户的头像的url
    public String getToken(UserLogin userLogin);       //根据用户的信息生成token字符串
    public User findUserById(int id);       //根据用户的id获得用户信息
    public UserLogin findUserLoginById(int id);       //根据用户的id获得用户的登录信息
    public int updateUserHeadImg(String src, int id);          //修改用户的头像的url
    public int findUserByName(String username);             //判断该用户是否存在
    public UserLogin findUserLoginByUid(int id);            //
    public List<Trade> queryAllTrade();         //查询所有订单
    public int resetPwdById(int id);           //重置密码
    public UserLogin findUserLoginByUserName(String username);      //通过用户名得到登录信息
}

