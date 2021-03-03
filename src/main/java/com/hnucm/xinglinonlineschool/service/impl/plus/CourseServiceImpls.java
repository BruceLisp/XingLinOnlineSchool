package com.hnucm.xinglinonlineschool.service.impl.plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnucm.xinglinonlineschool.dao.plus.CourseMappers;
import com.hnucm.xinglinonlineschool.dao.plus.TradeMappers;
import com.hnucm.xinglinonlineschool.pojo.Course;
import com.hnucm.xinglinonlineschool.pojo.PageReq;
import com.hnucm.xinglinonlineschool.pojo.Trade;
import com.hnucm.xinglinonlineschool.service.plus.CourseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpls extends ServiceImpl<CourseMappers, Course>
        implements CourseServices {

    @Autowired
    CourseMappers courseMappers;
    @Autowired
    TradeMappers tradeMappers;

    @Override
    public List<Course> getSubscriptCourse(int id) {
        return courseMappers.querySubscriptCourse(id);
    }

    @Override
    public int getCourseIsPurchase(int id, int uid) {           //获取用户是否购买了这个课程
        QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",id).eq("uid",uid);
        return tradeMappers.selectCount(queryWrapper);
    }

    @Override
    public List<Course> getAllCourse(PageReq pageReq) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        Page<Course> page = new Page<>(pageReq.getCurrent(),pageReq.getSize());
        IPage<Course> iPage = this.getBaseMapper().selectPage(page,queryWrapper);
        return iPage.getRecords();
    }

    @Override
    public List<Course> getCourseByName(String name, PageReq pageReq) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("theme",name);
        Page<Course> page = new Page<>(pageReq.getCurrent(),pageReq.getSize());
        IPage<Course> iPage = this.getBaseMapper().selectPage(page,queryWrapper);
        return iPage.getRecords();
    }

    @Override
    public int getCourseByNameSize(String name) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("theme",name);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public List<Course> queryCourseSales(PageReq pageReq) {
        List<Course> courses;
        if((pageReq.getDate()==null||pageReq.getDate().equals(""))&&(pageReq.getName()==null||pageReq.getName().equals(""))){
            return courseMappers.queryCourseSales();
        }
        if(pageReq.getDate()==null||pageReq.getDate().equals("")){
            return courseMappers.queryCourseSalesByName(pageReq.getName());
        }
        if(pageReq.getName()==null||pageReq.getName().equals("")){
            return courseMappers.queryCourseSalesByDate(pageReq.getDate());
        }
        return courseMappers.queryCourseSalesDateAndName(pageReq.getDate(),pageReq.getName());
    }


}
