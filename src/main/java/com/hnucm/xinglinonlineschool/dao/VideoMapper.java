package com.hnucm.xinglinonlineschool.dao;

import com.hnucm.xinglinonlineschool.pojo.BriefVideo;
import com.hnucm.xinglinonlineschool.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface VideoMapper {
    public int addVideo(Video video);
    public List<Video> queryAllVideos();
    public Video getVideoById(int id);
    public List<BriefVideo> getBriefVideoList();
}
