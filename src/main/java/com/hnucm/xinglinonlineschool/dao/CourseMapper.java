package com.hnucm.xinglinonlineschool.dao;

import com.hnucm.xinglinonlineschool.pojo.Course;
import com.hnucm.xinglinonlineschool.pojo.CourseNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CourseMapper {
    public List<Course> queryAllCourse();               //查询所有的课程信息
    public List<Course> findCourseBytid(int tid);       //根据课程讲师id查询课程信息列表
    public Course findCourseById(int id);               //根据课程id查询课程信息
    public List<CourseNode> queryCNodeByCid(int cid);   //根据课程号查询课程主目录列表
    public List<CourseNode> queryCNodeByFid(int fid);   //根据父目录id查询文件列表
    public int updateCNodeUrl(CourseNode courseFile);   //修改文件的内容
    public int updateCNodeName(CourseNode courseFile);  //修改目录结点的名字
    public int addNode(CourseNode courseFile);          //添加目录或者文件结点
    public int deleteNode(int id);                      //删除文件或目录结点
    public int deleteCourse(int cid);                   //删除整个课程的所有节点
    public int deleteNodeByFid(int fid);                //根据fid删除所有子节点
    public int addCourse(Course course);                //添加课程信息
    public int updateCoverUrl(Course course);           //修改course的cover的url
    public int findTidById(int id);                     //根据id查询tid
    public int updateCourseStatus(Course course);       //修改课程的id
    public String findCoverUrlById(int id);             //通过课程id得到coverImg的Url
    public List<Course> queryAllCourseByUid(int id);    //通过用户的id查询他订阅的所有课程信息
    public List<Course> queryCourseByName(String name); //根据课程的名字查找课程名
    public int findCidByNid(int nid);                   //根据结点id查询课程id
    public CourseNode findCourseNodeById(int id);              //根据id查询节点信息
}
