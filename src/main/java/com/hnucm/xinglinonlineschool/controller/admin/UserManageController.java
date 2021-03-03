package com.hnucm.xinglinonlineschool.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnucm.xinglinonlineschool.pojo.*;
import com.hnucm.xinglinonlineschool.service.AliPayTradeService;
import com.hnucm.xinglinonlineschool.service.plus.AliPayTradeServices;
import com.hnucm.xinglinonlineschool.service.plus.TradeServices;
import com.hnucm.xinglinonlineschool.service.UserService;
import com.hnucm.xinglinonlineschool.service.plus.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("um")
public class UserManageController {

    @Autowired
    AliPayTradeServices aliPayTradeServices;
    @Autowired
    UserServices userServices;
    @Autowired
    UserService userService;


    @RequestMapping("/resetPwd/{id}")
    public Result resetPwd(@PathVariable int id){   //分页查询用户的充值记录
        System.out.println("id:"+id);
        if(userService.resetPwdById(id)>0){
            return Result.ok("密码重置成功！");
        }else{
            return Result.error("密码重置失败！");
        }
    }


    @RequestMapping("/getAllUsers")
    public Result getAllUsers(@RequestBody PageReq pageReq){   //分页查询用户的充值记录
        List<User> users = userServices.queryAllUsers(pageReq);
        if(users==null){
            return Result.error("用户信息查询失败！");
        }else{
            pageReq.setTotal(userServices.query().count());
            return Result.ok("用户信息查询成功！").put("data",users).put("page",pageReq);
        }
    }

    @RequestMapping("/getAllRecharges")
    public Result getAllRecharges(@RequestBody PageReq pageReq){   //分页查询用户的充值记录
        System.out.println("pagereq"+pageReq.toString());
        List<AliPayTrade> trades = aliPayTradeServices.queryAllTrades(pageReq.getCurrent(),pageReq.getSize());
        if(trades!=null){
            pageReq.setTotal(aliPayTradeServices.getTradesSize());
            return Result.ok("充值记录查询成功！").put("data",trades).put("page",pageReq);
        }else{
            return Result.error("充值记录查询失败！");
        }
    }

}


    /*@RequestMapping("/getAllRecharge")
    public Result getAllRecharge(){   //查询用户的充值记录
        List<AliPayTrade> trades = aliPayTradeService.queryAllRecharge();
        if(trades==null){
            return Result.error("充值记录查询失败！");
        }else{
            return Result.ok("充值记录查询成功！").put("data",trades.subList(0, 20));
        }
    }
*/