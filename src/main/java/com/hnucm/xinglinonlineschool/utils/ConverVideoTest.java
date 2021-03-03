package com.hnucm.xinglinonlineschool.utils;

import com.hnucm.xinglinonlineschool.dao.VideoMapper;
import com.hnucm.xinglinonlineschool.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverVideoTest {
	
	/**
	 * @Description:(1.转码和截图功能调用)
	 * @param:@param yuanPATH   
	 * @return:void  
	 * @author:Zoutao
	 * @date:2018-6-23
	 * @version:V1.0
	 */


	@Autowired
	VideoMapper videoService;
 
	//web调用
    public void run(String yuanPATH) {
        try {
            // 转码和截图功能开始
            //String filePath = "D:/testfile/video/rmTest.rm";  //本地源视频测试
        	String filePath = yuanPATH;				//web传入的源视频
        	System.out.println("ConverVideoTest说:传入工具类的源视频为:"+filePath);
        	
            ConverVideoUtils zout = new ConverVideoUtils(filePath);  //传入path
            String targetExtension = ".mp4";  				//设置转换的格式
            boolean isDelSourseFile = true;
            
            //删除源文件
            boolean beginConver = zout.beginConver(targetExtension,isDelSourseFile);
            System.out.println(beginConver);
			Video video = new Video();
			video.setUploadDate(DateUtils.getDate());
			video.setVideoUrl(zout.getVideoUrl());
			video.setImageUrl(zout.getImageUrl());
			System.out.println(videoService.queryAllVideos());
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}