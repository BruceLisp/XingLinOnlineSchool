package com.hnucm.xinglinonlineschool.controller;

import com.hnucm.xinglinonlineschool.pojo.Result;
import com.hnucm.xinglinonlineschool.pojo.SystemImg;
import com.hnucm.xinglinonlineschool.pojo.Video;
import com.hnucm.xinglinonlineschool.service.CourseService;
import com.hnucm.xinglinonlineschool.service.UserService;
import com.hnucm.xinglinonlineschool.service.VideoService;
import com.hnucm.xinglinonlineschool.service.plus.SystemImgServices;
import com.hnucm.xinglinonlineschool.utils.NonStaticResourceHttpRequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/file")
public class FileRestController {
 
    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    @Autowired
    VideoService videoService;
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;
    @Autowired
    SystemImgServices systemImgServices;
   
    @GetMapping("/video/{id}")
    public void videoPreview(HttpServletRequest request, HttpServletResponse response,@PathVariable int id) throws Exception {
 
        //假如我把视频1.mp4放在了static下的video文件夹里面
        //sourcePath 是获取resources文件夹的绝对地址
        //realPath 即是视频所在的磁盘地址
        /*String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1);
        String realPath = sourcePath +"static/video/1.mp4";
 */
        System.out.println("id:"+id);
        Video video = videoService.getVideoById(id);        //拿到video对象
        String realPath = video.getVideoUrl();
        Path filePath = Paths.get(realPath);
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }

    @GetMapping("/image/{id}")
    public void imagePreview(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) throws Exception {

        //假如我把视频1.mp4放在了static下的video文件夹里面
        //sourcePath 是获取resources文件夹的绝对地址
        //realPath 即是视频所在的磁盘地址
        /*String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1);
        String realPath = sourcePath +"static/video/1.mp4";
 */
        System.out.println("id:"+id);
        Video video = videoService.getVideoById(id);        //拿到video对象
        String realPath = video.getImageUrl();
        System.out.println("url:"+realPath);
        Path filePath = Paths.get(realPath );
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }

    @GetMapping("/courseCover/{id}")
    public void courseCoverPreview(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) throws Exception {

        String realPath = courseService.findCoverUrlById(id);        //拿到CoverImg的url
        Path filePath = Paths.get(realPath );
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }

    @GetMapping("/headImg/{id}")
    public void headImg(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) throws Exception {

        String realPath = userService.fingUserheadImgById(id);       //拿到指定用户的头像的url
        Path filePath = Paths.get(realPath );
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }

    @GetMapping("/systemView/{id}")
    public void systemPreview(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) throws Exception {

        String realPath = systemImgServices.getById(id).getUrl();       //拿到systemView的url
        System.out.println("path:"+realPath);
        Path filePath = Paths.get(realPath );
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }

    @GetMapping("/payImg")
    public void payImg(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<SystemImg> systemImgList = systemImgServices.getPayImg(1);       //拿到payImg的url
        int index = (int) Math.random()*systemImgList.size();
        System.out.println("path:"+systemImgList.get(index));
        Path filePath = Paths.get(systemImgList.get(index).getUrl());
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }

    @RequestMapping("/getTurnImgs")
    public Result getTurnImgs(){        //拿到轮转图列表
        List<SystemImg> systemImgList = systemImgServices.getPayImg(2);
        return Result.ok("获取数据成功！").put("data",systemImgList);
    }


}