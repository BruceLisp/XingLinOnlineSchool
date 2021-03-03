package com.hnucm.xinglinonlineschool.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnucm.xinglinonlineschool.pojo.Result;
import com.hnucm.xinglinonlineschool.pojo.SystemImg;
import com.hnucm.xinglinonlineschool.service.plus.SystemImgServices;
import com.hnucm.xinglinonlineschool.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("sm")
public class SystemManageController {

    @Autowired
    SystemImgServices systemImgServices;

    @RequestMapping("/getList")
    public Result getList(){
        List<SystemImg> systemImgList = systemImgServices.list();
        if(systemImgList!=null) {
            return Result.ok("获取数据成功！").put("data",systemImgList);
        }else {
            return Result.error("获取数据失败！");
        }
    }

    @RequestMapping("/setImg/{id}")
    public Result setImg(HttpServletRequest request, @PathVariable int id){
        System.out.println("pasdasd:"+id);
        SystemImg systemImg = systemImgServices.getById(id);
        if(systemImg.getUrl()!=null){
            FileUtils.deleteFile(systemImg.getUrl());       //删除本地文件
        }
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        if(systemImgServices.setImg(file, id)>0) {
            return Result.ok("修改成功！");
        }else {
            return Result.error("修改失败！");
        }
    }

    /**
     * 修改该图片的描述信息
     * @param systemImg
     * @return
     */
    @RequestMapping("/setDescrible")
    public Result setDescrible(@RequestBody SystemImg systemImg){
        SystemImg systemImg1 = systemImgServices.getById(systemImg.getId());
        systemImg1.setType(systemImg.getType());
        systemImg1.setDescrible(systemImg.getDescrible());
        if(systemImgServices.updateById(systemImg1)) {
            return Result.ok("修改成功！");
        }else {
            return Result.error("修改失败！");
        }
    }

    @RequestMapping("/setType")
    public Result setType(@RequestBody SystemImg systemImg){
        SystemImg systemImg1 = systemImgServices.getById(systemImg.getId());
        systemImg1.setType(systemImg.getType());
        if(systemImgServices.updateById(systemImg1)) {
            return Result.ok("修改成功！");
        }else {
            return Result.error("修改失败！");
        }
    }

    @RequestMapping("/addImg")
    public Result addImg(HttpServletRequest request, @RequestBody SystemImg systemImg){
        if(systemImgServices.save(systemImg)) {
            return Result.ok("添加成功！");
        }else {
            return Result.error("添加失败！");
        }
    }

    /*@RequestMapping("/uploadImg/{id}")
    public Result uploadImg(HttpServletRequest request,@PathVariable int id){
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        if(systemImgServices.addImg(file,systemImg)>0) {
            return Result.ok("添加成功！");
        }else {
            return Result.error("添加失败！");
        }
    }*/

    @RequestMapping("/delImg/{id}")
    public Result delImg(@PathVariable int id){
        SystemImg systemImg = systemImgServices.getById(id);
        if(systemImg.getUrl()!=null){
            FileUtils.deleteFile(systemImg.getUrl());       //删除本地文件
        }
        if(systemImgServices.removeById(id)) {
            return Result.ok("删除成功！");
        }else {
            return Result.error("删除失败！");
        }
    }

}
