package com.hnucm.xinglinonlineschool.service.impl.plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnucm.xinglinonlineschool.dao.plus.UserLoginMappers;
import com.hnucm.xinglinonlineschool.dao.plus.UserMappers;
import com.hnucm.xinglinonlineschool.pojo.PageReq;
import com.hnucm.xinglinonlineschool.pojo.User;
import com.hnucm.xinglinonlineschool.pojo.UserLogin;
import com.hnucm.xinglinonlineschool.service.plus.UserLoginServices;
import com.hnucm.xinglinonlineschool.service.plus.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpls extends ServiceImpl<UserMappers, User>
        implements UserServices {

    @Autowired
    UserLoginMappers userLoginMappers;
    @Autowired
    UserMappers userMappers;

    @Override
    public List<User> queryAllUsers(PageReq pageReq) {
        Page<User> page = new Page<>(pageReq.getCurrent(),pageReq.getSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        IPage<User> iPage = this.getBaseMapper().selectPage(page,queryWrapper);
        return iPage.getRecords();
    }

    /**
     * 分页得到所有的讲师信息
     * @param pageReq
     * @return
     */
    @Override
    public List<User> queryAllTeachers(PageReq pageReq) {
        Page<User> page = new Page<>(pageReq.getCurrent(),pageReq.getSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",4).or().eq("type",2);
        IPage<User> iPage = this.getBaseMapper().selectPage(page,queryWrapper);
        return iPage.getRecords();
    }

    /**
     * 得到用户的类型，用于做权限判断
     * @param username
     * @return
     */
    @Override
    public int getUserType(String username) {
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return userLoginMappers.selectOne(queryWrapper).getType();
    }


    /**
     * 收回用户权限
     * @param id
     * @return
     */
    @Transactional
    @Override
    public int releaseByUserId(int id) {
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",id);
        UserLogin userLogin = userLoginMappers.selectOne(queryWrapper);
        User user = userMappers.selectById(id);
        user.setType(user.getType()-2);
        userLogin.setType(user.getType()-2);
        if(userLoginMappers.updateById(userLogin)>0&&userLoginMappers.updateById(userLogin)>0){
            return 1;
        }else{
            return 0;
        }
    }


    /**
     *  给用户账号授权
     * @param id
     * @return
     */
    @Transactional
    @Override
    public int grantByUserId(int id) {
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",id);
        UserLogin userLogin = userLoginMappers.selectOne(queryWrapper);
        User user = userMappers.selectById(id);
        user.setType(user.getType()+2);
        userLogin.setType(user.getType()+2);
        if(userLoginMappers.updateById(userLogin)>0&&userLoginMappers.updateById(userLogin)>0){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public int queryAllTeacherCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",4).or().eq("type",2);
        return baseMapper.selectCount(queryWrapper);
    }

}
