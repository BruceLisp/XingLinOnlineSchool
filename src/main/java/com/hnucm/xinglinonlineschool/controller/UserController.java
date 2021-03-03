package com.hnucm.xinglinonlineschool.controller;

import com.hnucm.xinglinonlineschool.annotation.UserLoginToken;
import com.hnucm.xinglinonlineschool.pojo.*;
import com.hnucm.xinglinonlineschool.service.AliPayTradeService;
import com.hnucm.xinglinonlineschool.service.plus.AliPayTradeServices;
import com.hnucm.xinglinonlineschool.service.plus.TradeServices;
import com.hnucm.xinglinonlineschool.service.UserService;
import com.hnucm.xinglinonlineschool.service.plus.UserServices;
import com.hnucm.xinglinonlineschool.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AliPayTradeService aliPayTradeService;
    @Autowired
    AliPayTradeServices aliPayTradeServices;
    @Autowired
    TradeServices tradeServices;
    @Autowired
    UserServices userServices;

    @RequestMapping("/authcode")
    public Result authcode(@RequestBody Map<String,String> user) throws Exception {   //得到验证码

        System.out.println("UserLogin:"+user);
        if(user.get("module").equals("login")){
            if(userService.findUserByName(user.get("username"))>0){
                String authCode = AuthCode.getAuthCode();//随机生成验证码
                Email.email(user.get("username"), authCode);//转发验证码至邮箱
                return Result.ok("获取验证码成功！").put("authcode",authCode);
            }else{
                return Result.ok("该用户不存在！");
            }
        }else{
            if(userService.emailIsExist(user.get("username"))){
                return Result.error(508,"该邮箱已被注册！");
            }else{
                String authCode = AuthCode.getAuthCode();//随机生成验证码
                Email.email(user.get("username"), authCode);//转发验证码至邮箱
                return Result.ok("获取验证码成功！").put("authcode",authCode);
            }
        }
    }

    @RequestMapping("/teacher/register")
    public Result registerUser(@RequestBody UserLogin user){
        user.setType(4);
        if(userService.registerUser(user)>=0){        //待审核审核中
            return Result.ok("注册成功！");
        }else{
            return Result.error("注册失败");
        }
    }

    @RequestMapping("/user/register")
    public Result registerTeacher(@RequestBody UserLogin user){
        user.setType(1);
        if(userService.registerUser(user)>=0){
            return Result.ok("注册成功！");
        }else{
            return Result.error("注册失败");
        }
    }

    @RequestMapping("/admin/register")
    public Result registerAdmin(@RequestBody UserLogin user){
        user.setType(5);
        if(userService.registerUser(user)>=0){        //待审核审核中
            return Result.ok("注册成功！");
        }else{
            return Result.error("注册失败");
        }
    }

    @RequestMapping("/login")
    public Result login(HttpServletRequest request, HttpServletResponse response, @RequestBody UserLogin user){   //登录验证
        User users = userService.login(user);
        if(users!=null){   //查询用户，对比其密码
            user = userService.findUserLoginByUserName(user.getUsername());
            response.setHeader("token", userService.getToken(user));
            return Result.ok().put("data",users);
        }else{
            return Result.error("登录失败，密码错误或者用户名不存在！");
        }
    }

    @RequestMapping("/user/login")
    public Result userLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody UserLogin user){   //登录验证
        int type = userServices.getUserType(user.getUsername());
        if(type==1){
            User users = userService.login(user);
            if(users!=null){   //查询用户，对比其密码
                user = userService.findUserLoginByUserName(user.getUsername());
                response.setHeader("token", userService.getToken(user));
                //users.setToken(null);
                return Result.ok().put("data",users);
            }else{
                return Result.error("登录失败，密码错误或者用户名不存在！");
            }
        }else{
            return Result.error("登录失败，权限不足！");
        }
    }


    @RequestMapping("/teacher/login")
    public Result teacherLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody UserLogin user){   //登录验证
        int type = userServices.getUserType(user.getUsername());
        if(type==2||type==4){
            User users = userService.login(user);
            if(users!=null){   //查询用户，对比其密码
                user = userService.findUserLoginByUserName(user.getUsername());
                response.setHeader("token", userService.getToken(user));
                //users.setToken(null);
                return Result.ok().put("data",users);
            }else{
                return Result.error("登录失败，密码错误或者用户名不存在！");
            }
        }else{
            return Result.error("登录失败，权限不足！");
        }
    }

    @RequestMapping("/admin/login")
    public Result adminLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody UserLogin user){   //登录验证
        int type = userServices.getUserType(user.getUsername());
        if(type==3||type==5){
            User users = userService.login(user);
            if(users!=null){   //查询用户，对比其密码
                user = userService.findUserLoginByUserName(user.getUsername());
                response.setHeader("token", userService.getToken(user));
                //users.setToken(null);
                return Result.ok().put("data",users);
            }else{
                return Result.error("登录失败，密码错误或者用户名不存在！");
            }
        }else{
            return Result.error("登录失败，权限不足！");
        }
    }


    @RequestMapping("/updatePwd")
    public Result updatePwd(@RequestBody UserLogin user){   //修改密码
        System.out.println("user:"+user);
        UserLogin userLogin = userService.findUserLoginByUserName(user.getUsername());
        if(userLogin.getPassword().equals(MD5Util.getMD5Str(user.getOldPwd()))){
            if(userService.updatePassword(user)>=0){    //修改密码，返回修改结果
                //TODO token失效
                return Result.ok("密码修改成功！");
            }else{
                return Result.error("密码修改错误！");
            }
        }else{
            return Result.error("原密码错误！");
        }
    }

    @RequestMapping("/updateMess")
    public Result updateMess(@RequestBody User user){   //修改用户信息
        //修改用户信息，返回结果
        if(userService.updateMess(user)>0){
            return Result.ok("修改信息成功！").put("data",user);
        }else{
            return Result.error("修改信息失败！");
        }
    }

    @UserLoginToken
    @RequestMapping("/getMess/{id}")
    public Result getMess(@PathVariable int id){   //根据用户id查询用户信息
        //查找用户信息，返回结果
        User user = userService.findUserById(id);
        if(user!=null){
            return Result.ok("查询用户信息成功！").put("data",user);
        }else{
            return Result.error("查询用户信息失败！");
        }
    }

    @RequestMapping("/trade")
    public Result trade(@RequestBody Trade trade){   //购买课程
        //处理订单
        trade = userService.handleTrade(trade);
        if(trade==null){
            return Result.error("余额不足!");
        }else{
            if(trade.getId()>0){
                return Result.ok("课程购买成功！").put("data",trade);
            }else{
                return Result.error("购买课程失败，请重试");
            }
        }
    }




    @RequestMapping("/uploadHeadImg/{id}")
    public Result uploadCover(HttpServletRequest request, @PathVariable int id){
        //删除之前的头像
        String oldSrc = userService.fingUserheadImgById(id);
        if(oldSrc!=null){
            FileUtils.deleteFile(oldSrc);
        }
        //上传课程封面图片
        System.out.println("id:"+id);
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        String src = DumpFile.dumpFile(file,id);        //进行头像图片转储
        //绑定数据库数据
        if(userService.updateUserHeadImg(src,id)>0){ //修改数据库中的用户头像的url
            return Result.ok("头像上传成功！");
        }else{
            return Result.error("头像上传失败！");
        }
    }

    @RequestMapping("/getUserTrade/{id}")
    public Result getTradeById(@PathVariable int id,@RequestBody PageReq pageReq){   //修改用户信息
        //修改用户信息，返回结果
        List<Trade> trades = tradeServices.queryTradeById(id,pageReq);
        if(trades!=null){
            return Result.ok("获取数据成功！").put("data",trades);
        }else{
            return Result.error("获取数据失败！");
        }
    }

    @RequestMapping("/getUserTradeSize/{id}")
    public Result getTradeById(@PathVariable int id){   //修改用户信息
        //修改用户信息，返回结果
        return Result.ok("获取数据成功！").put("date",tradeServices.queryUserTradeSize(id));
    }
}
