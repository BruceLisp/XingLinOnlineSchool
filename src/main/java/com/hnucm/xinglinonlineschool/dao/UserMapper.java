package com.hnucm.xinglinonlineschool.dao;

import com.hnucm.xinglinonlineschool.pojo.AliPayTrade;
import com.hnucm.xinglinonlineschool.pojo.Trade;
import com.hnucm.xinglinonlineschool.pojo.User;
import com.hnucm.xinglinonlineschool.pojo.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    public List<User> queryAllUsers();
    public int insertUserLogin(UserLogin user);
    public int updateUserHeadImg(User user);        //修改用户头像
    public int updateMess(User user);
    public int updatePassword(UserLogin user);
    public int selectLastId();
    public int telIsExist(String tel);          //验证电话是否存在
    public int emailIsExist(String email);      //验证邮箱是否存在
    public UserLogin queryUserLoginByUsername(String username);      //根据用户名查找用户信息
    public int  updateUserBalance(Trade trade);     //修改用户的杏林币余额
    public String fingUserheadImgById(int id);      //根据用户的id查找用户的头像的url
    public UserLogin findUserLoginById(int id);     //根据用户的id查找用户的信息
    public User findUserById(int id);               //根据用户的id查找登录信息
    public int insertUser(User users);              //注册用户并返回id
    public int findUserByName(String username);
    public UserLogin findUserLoginByUid(int uid);
    public int queryUserBalanceById(int uid);           //查询用户余额
}