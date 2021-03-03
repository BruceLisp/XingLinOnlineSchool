package com.hnucm.xinglinonlineschool.service.impl;

import com.hnucm.xinglinonlineschool.dao.CourseMapper;
import com.hnucm.xinglinonlineschool.dao.TradeMapper;
import com.hnucm.xinglinonlineschool.pojo.Course;
import com.hnucm.xinglinonlineschool.pojo.CourseNode;
import com.hnucm.xinglinonlineschool.pojo.Trade;
import com.hnucm.xinglinonlineschool.service.CourseService;
import com.hnucm.xinglinonlineschool.utils.DateUtils;
import com.hnucm.xinglinonlineschool.utils.DumpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TradeMapper tradeMapper;

    @Override
    public Course queryAllCatalog(int id) {
        //根据课程id查询主目录结构
        Course course = courseMapper.findCourseById(id);
        List<CourseNode> courseFiles = courseMapper.queryCNodeByCid(id);
        for(CourseNode courseFile : courseFiles){
            courseFile.getSonFile(courseMapper);
//            CourseNode courseNode = new CourseNode();
//            courseNode.setId(courseFile.getId());
//            courseNode.getSonFile();
        }
        course.setCourseNodeList(courseFiles);
        return course;
    }

    @Override
    public List<Course> queryCourseBytid(int tid) {
        List<Course> courses = courseMapper.findCourseBytid(tid);
        return courses;
    }

    @Transactional
    @Override
    public int addCourse(Course course) {
        //设置课程创建的时间
        course.setDate(DateUtils.getDate());
        courseMapper.addCourse(course);
        return course.getId();
    }

    @Override
    public List<Course> findCourseBytid(int id) {
        return courseMapper.findCourseBytid(id);
    }

    @Override
    public List<Course> queryAllCourse() {
        return courseMapper.queryAllCourse();
    }

    @Transactional
    @Override
    public int addCourseNode(CourseNode courseNode) {
        courseNode.setDate(DateUtils.getDate());
        courseMapper.addNode(courseNode);
        return courseNode.getId();
    }

    @Transactional
    @Override
    public int updateCoverUrl(Course course) {
        return courseMapper.updateCoverUrl(course);
    }

    @Override
    public int findTidById(int id) {
        return courseMapper.findTidById(id);
    }

    @Transactional
    @Override
    public int updateCourseStatus(int id, int status) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(status);
        return courseMapper.updateCourseStatus(course);
    }

    @Override
    public String findCoverUrlById(int id) {
        return courseMapper.findCoverUrlById(id);
    }

    @Override
    public Course queryCourseById(int id) {
        return courseMapper.findCourseById(id);
    }

    @Override
    public int addTrade(Trade trade) {                  //添加课程订阅记录并返回订单号
        trade.setDate(DateUtils.getDate());
        tradeMapper.addTradeReturnId(trade);
        return trade.getId();
    }

    @Override
    public List<Trade> queryAllTradeByUid(int id) {     //根据用户的id查询所有的订阅课程记录
        return tradeMapper.queryAllTradeByUid(id);
    }

    @Override
    public List<Course> queryAllCourseByUid(int id) {     //根据用户的id查询所有的订阅课程
        return courseMapper.queryAllCourseByUid(id);
    }

    @Override
    public List<Course> queryCourseByName(String name) {        //根据课程名模糊查询课程
        return courseMapper.queryCourseByName(name);
    }

    @Override
    public int deleteNodeByFid(int fid) {
        return courseMapper.deleteNodeByFid(fid);
    }

    //根据上一个结点的id对该文件进行转储，并将转储后的地址存入数据库
    @Override
    public int uploadFileNode(MultipartFile file, int nid) {
        int cid = courseMapper.findTidById(nid);
        int tid = courseMapper.findTidById(cid);
        String src = DumpFile.dumpFile(file,tid,cid);
        CourseNode courseNode = new CourseNode();
        courseNode.setFid(nid);
        courseNode.setCid(cid);
        courseNode.setUrl(src);
        courseNode.setType(1);
        return courseMapper.addNode(courseNode);
    }

    @Override
    public int deleteNode(int id) {
        CourseNode courseNode = courseMapper.findCourseNodeById(id);
        courseNode.getSonFile(courseMapper);
        courseNode.delSonFile(courseMapper);
        return courseMapper.deleteNode(id);
    }

    @Override
    public int updateCourseName(CourseNode courseNode) {
        return courseMapper.updateCNodeName(courseNode);
    }


//    //根据上一个结点的id对该文件进行转储，并将转储后的地址存入数据库
//    @Override
//    public int uploadVideoNode(String src, int nid) {
//        int cid = courseMapper.findTidById(nid);
//        int tid = courseMapper.findTidById(cid);
//        CourseNode courseNode = new CourseNode();
//        courseNode.setFid(nid);
//        courseNode.setCid(cid);
//        courseNode.setUrl(src);
//        courseNode.setType(2);
//        return courseMapper.addNode(courseNode);
//    }


}
