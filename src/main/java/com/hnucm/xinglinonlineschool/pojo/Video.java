package com.hnucm.xinglinonlineschool.pojo;

import lombok.Data;

@Data
public class Video {
    private int id;
    private String videoUrl;
    private String imageUrl;
    private String description;
    private String uploadDate;
    private String teacherName;

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", videoUrl='" + videoUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
