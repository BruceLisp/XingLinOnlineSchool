package com.hnucm.xinglinonlineschool.pojo;

import com.hnucm.xinglinonlineschool.dao.CourseMapper;
import com.hnucm.xinglinonlineschool.service.CourseService;
import com.hnucm.xinglinonlineschool.utils.FileUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseNode {
    private int id;
    private String name;
    private int fid;
    private String url;
    private String date;
    private int cid;
    private int type;
    private String imgUrl;
    private List<CourseNode> list;

    public CourseNode(){
        list = new ArrayList<>();
    }
//    注入失败
//    @Autowired
//    CourseMapper courseMapper;

    @Override
    public String toString() {
        return "CourseNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fid=" + fid +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", cid=" + cid +
                ", type=" + type +
                ", imgUrl='" + imgUrl + '\'' +
                ", list=" + list +
                '}';
    }

    public void getSonFile(CourseMapper courseMapper){           //递归调用得到整个文件的目录结构
        list = courseMapper.queryCNodeByFid(this.id);
        for(CourseNode cf : list){
            cf.getSonFile(courseMapper);
        }
    }

    public void delSonFile(CourseMapper courseMapper){           //递归删除，删除所有该节点下的文件及结点
        if(list!=null){
            for(CourseNode cf : list){
                cf.delSonFile(courseMapper);
                if(this.url!=null){
                    FileUtils.deleteFile(this.url);
                }
                if(this.imgUrl!=null){
                    FileUtils.deleteFile(this.imgUrl);
                }
            }
            courseMapper.deleteNodeByFid(this.id);
        }
    }

    public void rename(CourseMapper courseMapper){               //修改该结点的名称
        courseMapper.updateCNodeName(this);
    }

}
