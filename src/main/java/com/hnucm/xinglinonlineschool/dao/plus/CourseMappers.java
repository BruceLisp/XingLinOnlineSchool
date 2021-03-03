package com.hnucm.xinglinonlineschool.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnucm.xinglinonlineschool.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface CourseMappers extends BaseMapper<Course> {

    @Select("select course.* from course where course.id in (select trade.cid from trade where trade.uid = #{id})")
    public List<Course> querySubscriptCourse(int id);

    @Select("select course.*,(select count(*) from trade where trade.cid=course.id)" +
            " as salesvolume from course where course.id in (select trade.cid from trade)")
    public List<Course> queryCourseSales();

    @Select("select course.*,(select count(*) from trade where trade.cid=course.id)" +
            " as salesvolume from course where course.id in (select trade.cid from trade where trade.date like #{date}\"%\")")
    public List<Course> queryCourseSalesByDate(String date);

    @Select("select course.*,(select count(*) from trade where trade.cid=course.id)" +
            " as salesvolume from course where (course.id in (select trade.cid from trade)) and course.theme like \"%\"#{name}\"%\"")
    public List<Course> queryCourseSalesByName(String name);

    @Select("select course.*,(select count(*) from trade where trade.cid=course.id)" +
            " as salesvolume from course where " +
            "(course.id in (select trade.cid from trade where trade.date like #{date}\"%\")) " +
            "and course.theme like \"%\"#{name}\"%\"")
    public List<Course> queryCourseSalesDateAndName(String date, String name);

}
