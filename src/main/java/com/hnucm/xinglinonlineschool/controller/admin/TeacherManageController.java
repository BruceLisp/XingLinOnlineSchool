package com.hnucm.xinglinonlineschool.controller.admin;

import com.hnucm.xinglinonlineschool.pojo.AliPayTrade;
import com.hnucm.xinglinonlineschool.pojo.PageReq;
import com.hnucm.xinglinonlineschool.pojo.Result;
import com.hnucm.xinglinonlineschool.pojo.User;
import com.hnucm.xinglinonlineschool.service.plus.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tm")
@CrossOrigin
public class TeacherManageController {

    @Autowired
    UserServices userServices;


    @RequestMapping("/getAllUsers")
    public Result getAllUsers(@RequestBody PageReq pageReq){   //分页查询讲师的用户信息
        List<User> users = userServices.queryAllTeachers(pageReq);
        if(users==null){
            return Result.error("讲师信息查询失败！");
        }else{
            pageReq.setTotal(userServices.queryAllTeacherCount());
            return Result.ok("讲师信息查询成功！").put("data",users).put("page",pageReq);
        }
    }

    @RequestMapping("/grant/{id}")
    public Result grant(@PathVariable int id){   //给讲师授权
        User user = userServices.getById(id);
        if(user.getType()==2){
            if(userServices.grantByUserId(id)>0){
                return Result.ok("授权成功！");
            }else{
                return Result.error("授权失败！");
            }
        }else{
            return Result.error("已获得该权限！");
        }
    }

    @RequestMapping("/release/{id}")
    public Result release(@PathVariable int id){   //给讲师授权
        User user = userServices.getById(id);
        if(user.getType()==4){
            if(userServices.releaseByUserId(id)>0){
                return Result.ok("取消权限成功！");
            }else{
                return Result.error("取消权限失败！");
            }
        }else{
            return Result.error("没有该权限！");
        }
    }


}
