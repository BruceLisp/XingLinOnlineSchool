package com.hnucm.xinglinonlineschool.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnucm.xinglinonlineschool.annotation.PassToken;
import com.hnucm.xinglinonlineschool.pojo.*;
import com.hnucm.xinglinonlineschool.service.CourseService;
import com.hnucm.xinglinonlineschool.service.UserService;
import com.hnucm.xinglinonlineschool.service.VideoService;
import com.hnucm.xinglinonlineschool.service.plus.CourseServices;
import com.hnucm.xinglinonlineschool.service.plus.TradeServices;
import com.hnucm.xinglinonlineschool.service.plus.UserServices;
import com.hnucm.xinglinonlineschool.utils.DumpFile;
import com.hnucm.xinglinonlineschool.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("course")
@PassToken
public class CourseController {

    @Autowired
    CourseService courseService;
    @Autowired
    CourseServices courseServices;
    @Autowired
    VideoService videoService;
    @Autowired
    UserService userService;
    @Autowired
    UserServices userServices;
    @Autowired
    TradeServices tradeServices;


    @RequestMapping("/getCourseByName/{name}")
    public Result getCourseByName(@PathVariable String name){          //通过课程名字模糊查询课程
        List<Course> courses = courseService.queryCourseByName(name);
        if(courses!=null){
            if(courses.size()>0){
                return Result.ok("课程查询成功！").put("data",courses);
            }else{
                return Result.ok("暂无相关课程！").put("data",courses);
            }
        }else{
            return Result.error("查询失败！");
        }
    }

    @RequestMapping("/favorites/{id}")
    public Result favorites(@PathVariable int id){          //收藏课程
        //Todo
        List<Course> courses = courseService.queryAllCourseByUid(id);
        if(courses!=null){
            return Result.ok("收藏课程成功!").put("data",courses);
        }else{
            return Result.error("收藏课程失败!");
        }
    }

    /**
     * 购买课程，并返回订单号
     * @param trade
     * @return
     */
    @RequestMapping("/subscription")
    public Result subscription(@RequestBody Trade trade){       //购买课程
        System.out.println("trade_courseId:"+trade.getCid());
        System.out.println("trade_courseId:"+trade.getUid());
        //处理订单
        trade = userService.handleTrade(trade);
        if(trade==null){
            return Result.error("余额不足!");
        }else{
            if(trade.getId()>0){
                return Result.ok("课程购买成功！").put("data",userServices.getById(trade.getUid()));
            }else{
                return Result.error("购买课程失败，请重试!");
            }
        }
    }

    /**
     * 创建课程，返回新建课程的id
     * @param course
     * @return
     */
    @RequestMapping("/createCourse")
    public Result createCourse(@RequestBody Course course){
        System.out.println("course:"+course);
        //将课程信息存数据库，返回课程id
        int id = courseService.addCourse(course);
        if(id>0) {
            return Result.ok("课程创建成功！").put("id", id);
        }else{
            return Result.error("课程信息添加失败，请重试！");
        }
    }

    /**
     * 上传课程的封面，并绑定课程的id
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/uploadCover/{id}")
    public Result uploadCover(HttpServletRequest request, @PathVariable int id){
        //上传课程封面图片
        System.out.println("id:"+id);
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        int tid = courseService.findTidById(id);
        String src = DumpFile.dumpFile(file,tid,id);        //进行头像图片转储
        //绑定数据库数据
        Course course = new Course();
        course.setId(id);
        course.setCoverurl(src);
        courseService.updateCoverUrl(course);   //修改数据库中的课程的封面的url
        if(src!=null){
            return Result.ok("封面上传成功！");
        }else{
            return Result.error("封面上传失败！");
        }
    }


    /**
     * 通过老师id查询课程信息
     * @param id
     * @return
     */
    @RequestMapping("/getCourseByTid/{id}")
    public Result getCourseByTid(@PathVariable int id){
        //根据老师的id获取到他的创建的所有课程。
        List<Course> courses = courseService.queryCourseBytid(id);
        if(courses!=null){
            return Result.ok("课程信息查询成功！").put("data",courses);
        }else{
            return Result.error("课程信息查询失败！");
        }
    }


    /**
     * 通过课程id得到课程信息，含用户对于该课程的状态
     * @param id
     * @return
     */
    @RequestMapping("/getCourseById/{id}/{uid}")
    public Result getCourseMessById(@PathVariable int id,@PathVariable int uid){
        //根据老师的id获取到他的创建的所有课程。
        Course course = courseService.queryCourseById(id);
        if(course!=null){
            course.setIsPurchase(courseServices.getCourseIsPurchase(id,uid));
            return Result.ok("课程信息查询成功！").put("data",course);
        }else{
            return Result.error("课程信息查询失败！");
        }
    }


    /**
     * 拿到所有课程信息
     * @return*/

    @RequestMapping("/getAllCourse")
    public Result getAllCourse(){
        //根据老师的id获取到他的创建的所有课程。
        List<Course> courses = courseService.queryAllCourse();
        if(courses!=null){
            return Result.ok("课程信息查询成功！").put("data",courses);
        }else{
            return Result.error("课程信息查询失败！");
        }
    }

/*    *//**
     * 拿到所有课程信息
     * @return
     *//*
    @RequestMapping("/getAllCourse")
    public Result getAllCourseMess(){
        //根据老师的id获取到他的创建的所有课程。
        List<Course> courses = courseServices.queryAllCourseMess();
        if(courses!=null){
            return Result.ok("课程信息查询成功！").put("data",courses);
        }else{
            return Result.error("课程信息查询失败！");
        }
    }*/



    /**
     * 拿到用户购买的课程
     * @param id
     * @return
     */
    @RequestMapping("/getSubScriptCourse/{id}")
    public Result getSubScriptCourse(@PathVariable int id){
        //根据用户的id获取到他的购买的所有课程。
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        List<Course> courses = courseServices.getSubscriptCourse(id);
        if(courses!=null){
            return Result.ok("课程信息查询成功！").put("data",courses);
        }else{
            return Result.error("课程信息查询失败！");
        }
    }

    /**
     * 完成课程创建，课程上传后，需要确认
     * @param id
     * @return
     */
    @RequestMapping("/complishCreate/{id}")
    public Result complishCreate(@PathVariable int id){
        //根据课程的id，将课程设置为上传完成
        if(courseService.updateCourseStatus(id, 1)>0){
            return Result.ok("课程上传成功！");
        }else{
            return Result.error("课程上传失败！");
        }
    }

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
     * 创建课程目录结点
     * @param courseNode
     * @return
     */
    @RequestMapping("/createCourseNode")
    public Result createCourseNode(@RequestBody CourseNode courseNode){
        //创建课程的目录结点
        int id = courseService.addCourseNode(courseNode);
        System.out.println("CourseNode:" + id);
        if(id>0){
            return Result.ok("添加成功！").put("id",id);
        }else{
            return Result.error("添加失败！");
        }
    }

    @RequestMapping("/uploadFile/{nid}")
    public Result uploadFile(HttpServletRequest request, @PathVariable int nid){
        /*List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFile("fileName");*/
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        int id = courseService.uploadFileNode(file,nid);
        if(id>0){
            return Result.ok("文件上传成功！").put("nid",id);
        }else{
            return Result.error("文件上传失败！");
        }
    }

    @RequestMapping("/uploadVideo/{nid}")
    public Result uploadVideo(HttpServletRequest request, @PathVariable int cid){
        /*List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFile("fileName");*/
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        if(videoService.dumpVideo(file,cid)>0){
            return Result.ok("文件上传成功！");
        }else{
            return Result.error("文件上传失败！");
        }
    }

    /**
     * 根据id删除course的结点
     * @param id
     * @return
     */
    @RequestMapping("/deleteCourseNode")
    public Result deleteCourseNode(@PathVariable int id){
        //删除课程的目录结点
        if(courseService.deleteNode(id)>0){
            return Result.ok("删除成功！");
        }else{
            return Result.error("删除失败！");
        }
    }

    /**
     * 修改课程目录结点名字
     * @param courseNode
     * @return
     */
    @RequestMapping("/updateCourseNode")
    public Result updateCourseNode(@RequestBody CourseNode courseNode){
        //修改课程的目录结点
        if(courseService.updateCourseName(courseNode)>0){
            return Result.ok("修改成功！");
        }else{
            return Result.error("修改失败！");
        }
    }

}
