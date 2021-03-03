package com.hnucm.xinglinonlineschool.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Data
public class Course {
    private int id;
    private String theme;
    private String introduction;
    private int tid;
    private int type;
    private int status;
    @TableField(exist = false)
    private int isPurchase;     //是否购买
    private String date;
    private int price;
    private String coverurl;
    @TableField(exist = false)
    private List<CourseNode> courseNodeList;
    @TableField(exist = false, value = "salesvolume")
    private int salesVolume;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", introduction='" + introduction + '\'' +
                ", tid=" + tid +
                ", type=" + type +
                ", status=" + status +
                ", isPurchase=" + isPurchase +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", coverurl='" + coverurl + '\'' +
                ", courseNodeList=" + courseNodeList +
                ", salesVolume=" + salesVolume +
                '}';
    }
}
