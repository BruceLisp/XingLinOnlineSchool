package com.hnucm.xinglinonlineschool.service;

import com.hnucm.xinglinonlineschool.pojo.BriefVideo;
import com.hnucm.xinglinonlineschool.pojo.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    public List<Video> queryALlVideos();
    public int dumpVideo(MultipartFile file, int cid);
    public Video getVideoById(int id);
    public List<BriefVideo> getBriefVideoList();
}
