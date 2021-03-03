package com.hnucm.xinglinonlineschool.service;

import com.hnucm.xinglinonlineschool.pojo.Course;
import com.hnucm.xinglinonlineschool.pojo.CourseNode;
import com.hnucm.xinglinonlineschool.pojo.CourseNode;
import com.hnucm.xinglinonlineschool.pojo.Trade;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    public Course queryAllCatalog(int id);  //查询整个目录结构
    public List<Course> queryCourseBytid(int tid);     //查询该讲师的所有课程信息
    public int addCourse(Course course);      //向数据库添加课程信息
    public List<Course> findCourseBytid(int id);      //根据讲师的id获取他的所有课程
    public List<Course> queryAllCourse();           //查询所有的课程信息
    public int addCourseNode(CourseNode courseNode);      //向数据库添加课程节点信息，并返回id
    public int updateCoverUrl(Course course);        //修改课程的封面
    public int findTidById(int id);                     //根据id查询tid
    public int updateCourseStatus(int id, int status);      //修改课程的状态
    public String findCoverUrlById(int id);             //通过课程id得到coverImg的Url
    public Course queryCourseById(int id);
    public int addTrade(Trade trade);                   //添加订单并返回订单号
    public List<Trade> queryAllTradeByUid(int id);      //根据用户的id查询所有的订阅课程记录
    public List<Course> queryAllCourseByUid(int id);      //根据用户的id查询所有的订阅课程
    public List<Course> queryCourseByName(String name); //根据课程的名字查找课程名
    public int deleteNodeByFid(int fid);                //根据fid删除所有子节点
    public int uploadFileNode(MultipartFile file, int nid);     //根据上一个结点的id对该文件进行转储，并将转储后的地址存入数据库
    public int deleteNode(int id);                      //删除结点及下面的文件及结点
    public int  updateCourseName(CourseNode courseNode);        //修改节点名字

//    public int uploadVideoNode(String src, int nid);     //根据上一个结点的id对该文件进行转储，并将转储后的地址存入数据库

}
