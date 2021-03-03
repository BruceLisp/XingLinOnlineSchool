package com.hnucm.xinglinonlineschool.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnucm.xinglinonlineschool.pojo.*;
import com.hnucm.xinglinonlineschool.service.CourseService;
import com.hnucm.xinglinonlineschool.service.UserService;
import com.hnucm.xinglinonlineschool.service.VideoService;
import com.hnucm.xinglinonlineschool.service.plus.CourseServices;
import com.hnucm.xinglinonlineschool.service.plus.TradeServices;
import com.hnucm.xinglinonlineschool.service.plus.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("cm")
public class CourseManageController {

    @Autowired
    CourseService courseService;
    @Autowired
    CourseServices courseServices;
    @Autowired
    UserService userService;
    @Autowired
    UserServices userServices;
    @Autowired
    TradeServices tradeServices;

    /**
     * 得到课程的资料的目录列表
     * @param id
     * @return
     */
    @RequestMapping("/getCourseDetail/{id}")
    public Result getCourseDetail(@PathVariable int id){
        //创建课程的目录结点
        Course course = courseService.queryAllCatalog(id);
        if(course.getCourseNodeList()!=null){
            return Result.ok("获取成功！").put("data",course);
        }else{
            return Result.error("获取失败！");
        }
    }

    /**
     * 拿到所有课程信息
     * @return*/
    @RequestMapping("/getAllCourse")
    public Result getAllCourse(@RequestBody PageReq pageReq){
        //根据老师的id获取到他的创建的所有课程。
        List<Course> courses = courseServices.getAllCourse(pageReq);
        if(courses!=null){
            pageReq.setTotal(courseServices.count());
            return Result.ok("课程信息查询成功！").put("data",courses).put("page",pageReq);
        }else{
            return Result.error("课程信息查询失败！");
        }
    }

    /**
     * 模糊查询课程
     * @param name
     * @return
     */
    @RequestMapping("/getCourseByName/{name}")
    public Result getCourseByName(@PathVariable String name, @RequestBody PageReq pageReq){          //通过课程名字模糊查询课程
        List<Course> courses = courseServices.getCourseByName(name,pageReq);
        pageReq.setTotal(courseServices.getCourseByNameSize(name));
        if(courses!=null){
            if(courses.size()>0){
                return Result.ok("课程查询成功！").put("data",courses).put("page",pageReq);
            }else{
                return Result.ok("暂无相关课程！").put("data",courses).put("page",pageReq);
            }
        }else{
            return Result.error("查询失败！");
        }
    }

    @RequestMapping("/getAllTrades")
    public Result getAllTrades(@RequestBody PageReq pageReq){   //查询用户的充值记录
        List<Trade> trades = tradeServices.queryAllTrades(pageReq.getCurrent(), pageReq.getSize());
        for(Trade trade:trades){
            trade.setUser(userServices.getById(trade.getUid()));
            trade.setCourse(courseServices.getById(trade.getCid()));
        }
        if(trades!=null){
            pageReq.setTotal(tradeServices.count());
            return Result.ok("数据获取成功！").put("data",trades).put("page",pageReq);
        }else{
            return Result.error("数据获取失败！");
        }
    }

    /**
     * 通过课程id得到课程信息
     * @param id
     * @return
     */
    @RequestMapping("/getCourseById/{id}")
    public Result getCourseById(@PathVariable int id){
        //根据老师的id获取到他的创建的所有课程。
        System.out.println("Cid："+id);
        Course course = courseService.queryCourseById(id);
        if(course!=null){
            return Result.ok("课程信息查询成功！").put("data",course);
        }else{
            return Result.error("课程信息查询失败！");
        }
    }


    @RequestMapping("/getTradeCount")
    public Result getTradeCount(@RequestBody PageReq pageReq){
        //对课程的销售量做一个统计
        System.out.println("page:"+pageReq);
        List<Course> courses = courseServices.queryCourseSales(pageReq);
        int from = pageReq.getSize()*(pageReq.getCurrent()-1);
        int to = from+pageReq.getSize();
        pageReq.setTotal(courses.size());
        if(to>pageReq.getTotal()){
            to = pageReq.getTotal();
        }
        return Result.ok("获取数据成功！").put("data",courses.subList(from,to)).put("page",pageReq);
    }


}

