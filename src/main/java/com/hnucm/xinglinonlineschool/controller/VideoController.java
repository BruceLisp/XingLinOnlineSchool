package com.hnucm.xinglinonlineschool.controller;

import com.hnucm.xinglinonlineschool.pojo.BriefVideo;
import com.hnucm.xinglinonlineschool.pojo.Result;
import com.hnucm.xinglinonlineschool.pojo.User;
import com.hnucm.xinglinonlineschool.pojo.Video;
import com.hnucm.xinglinonlineschool.service.CourseService;
import com.hnucm.xinglinonlineschool.service.UserService;
import com.hnucm.xinglinonlineschool.service.VideoService;
import com.hnucm.xinglinonlineschool.utils.CheckoutFileType;
import com.hnucm.xinglinonlineschool.utils.DumpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("upload")
public class VideoController  {

    @Autowired
    VideoService videoService;
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;

    @RequestMapping("test2")
    public List<Video> getVideos() {
        List<Video> videos = videoService.queryALlVideos();
        return videos;
    }


    /**
     * 实现文件上传
     * */
    @RequestMapping("videoUpload/{cid}")
    /*public String fileUpload(@RequestParam("fileName") MultipartFile file){*/
    public Result fileUpload(HttpServletRequest request, @PathVariable int cid){
        /*List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFile("fileName");*/
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
        if(videoService.dumpVideo(file,cid)>0){
            return Result.ok("文件上传成功！");
        }else{
            return Result.error("文件上传失败！");
        }
    }


    /**
     * 实现多文件上传
     * */
    @RequestMapping(value="multivideoUpload",method= RequestMethod.POST)
    /* public @ResponseBody String multifileUpload(@RequestParam("fileName") List<MultipartFile> files) {*/
    public Result multifileUpload(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        System.out.println("文件上传开始");
        if(DumpFile.dumpMultiFile(files)){
            return Result.ok("文件上传成功！");
        }else{
            return Result.error("文件上传失败！");
        }
    }

    @RequestMapping("getVideoList")
    public Result getVideoList(){
        List<Video> videos = videoService.queryALlVideos();
        if(videos!=null){
            return Result.ok().put("data", videos);
        }else{
            return Result.error();
        }
    }


    @RequestMapping("getBriefVideoMessList")
    public Result getBriefVideoMessList(){
        List<BriefVideo> videos = videoService.getBriefVideoList();
        if(videos!=null){
            return Result.ok().put("data", videos);
        }else{
            return Result.error();
        }
    }

}
