package com.hnucm.xinglinonlineschool.service.plus;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnucm.xinglinonlineschool.pojo.AliPayTrade;
import com.hnucm.xinglinonlineschool.pojo.PageReq;
import com.hnucm.xinglinonlineschool.pojo.User;

import java.util.List;

public interface UserServices extends IService<User> {
    public  List<User> queryAllUsers(PageReq pageReq);
    public  List<User> queryAllTeachers(PageReq pageReq);
    public int getUserType(String username);
    public int releaseByUserId(int id);         //收回权限
    public int grantByUserId(int id);         //授权
    public int queryAllTeacherCount();
}
