package com.hnucm.xinglinonlineschool.service.plus;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnucm.xinglinonlineschool.pojo.SystemImg;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SystemImgServices extends IService<SystemImg> {
    public int setImg(MultipartFile file, int id);
    public int addImg(MultipartFile file, SystemImg systemImg);
    public List<SystemImg> getPayImg(int type);
}
