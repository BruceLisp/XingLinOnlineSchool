package com.hnucm.xinglinonlineschool.service.plus;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnucm.xinglinonlineschool.pojo.Course;
import com.hnucm.xinglinonlineschool.pojo.PageReq;

import java.util.List;

public interface CourseServices extends IService<Course> {
    public List<Course> getSubscriptCourse(int id);
    public int getCourseIsPurchase(int id, int uid);
    public List<Course> getAllCourse(PageReq pageReq);
    public List<Course> getCourseByName(String name, PageReq pageReq);
    public int getCourseByNameSize(String name);
    public List<Course> queryCourseSales(PageReq pageReq);
}
