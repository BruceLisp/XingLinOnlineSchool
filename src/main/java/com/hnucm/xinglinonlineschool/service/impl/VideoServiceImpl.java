package com.hnucm.xinglinonlineschool.service.impl;

import com.hnucm.xinglinonlineschool.dao.CourseMapper;
import com.hnucm.xinglinonlineschool.dao.VideoMapper;
import com.hnucm.xinglinonlineschool.pojo.BriefVideo;
import com.hnucm.xinglinonlineschool.pojo.CourseNode;
import com.hnucm.xinglinonlineschool.pojo.Video;
import com.hnucm.xinglinonlineschool.service.VideoService;
import com.hnucm.xinglinonlineschool.utils.ConverVideoTest;
import com.hnucm.xinglinonlineschool.utils.ConverVideoUtils;
import com.hnucm.xinglinonlineschool.utils.DateUtils;
import com.hnucm.xinglinonlineschool.utils.DumpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;
    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<Video> queryALlVideos() {
        return videoMapper.queryAllVideos();
    }

    @Transactional
    @Override
    public int dumpVideo(MultipartFile file,int nid) {           //转储和转码视频，图片截取、及路径存储于数据库
        /*c.run(sourceUrl);*/
        int cid = courseMapper.findTidById(nid);
        int tid = courseMapper.findTidById(cid);
        String src = DumpFile.dumpFile(file,tid,cid);
        String filePath = src;				//web传入的源视频
        System.out.println("ConverVideoTest说:传入工具类的源视频为:"+filePath);

        ConverVideoUtils zout = new ConverVideoUtils(filePath);  //传入path
        String targetExtension = ".mp4";  				//设置转换的格式
        boolean isDelSourseFile = true;
        //删除源文件
        boolean beginConver = zout.beginConver(targetExtension,isDelSourseFile);
        System.out.println(beginConver);
        /*Video video = new Video();
        video.setUploadDate(DateUtils.getDate());
        //Todo 需要提供真实数据
        video.setTeacherName("布鲁斯.李");
        video.setDescription(zout.getFilerealname());
        video.setVideoUrl(zout.getVideoUrl());
        video.setImageUrl(zout.getImageUrl());
        return videoMapper.addVideo(video);*/
        if(beginConver==true){
            CourseNode courseNode = new CourseNode();
            courseNode.setFid(nid);
            courseNode.setCid(cid);
            courseNode.setUrl(zout.getVideoUrl());
            courseNode.setName(zout.getFilerealname());
            courseNode.setImgUrl(zout.getImageUrl());
            courseNode.setDate(DateUtils.getDate());
            courseNode.setType(2);
            return courseMapper.addNode(courseNode);
        }else{
            return 0;
        }
    }

    @Override
    public Video getVideoById(int id) {
        return videoMapper.getVideoById(id);
    }

    @Override
    public List<BriefVideo> getBriefVideoList() {
        return videoMapper.getBriefVideoList();
    }


}
