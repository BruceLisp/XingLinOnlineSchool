package com.hnucm.xinglinonlineschool.service.impl.plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnucm.xinglinonlineschool.dao.plus.SystemImgMapper;
import com.hnucm.xinglinonlineschool.pojo.SystemImg;
import com.hnucm.xinglinonlineschool.service.plus.SystemImgServices;
import com.hnucm.xinglinonlineschool.utils.DumpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SystemImgServiceImpls extends ServiceImpl<SystemImgMapper, SystemImg>
        implements SystemImgServices {

    @Autowired
    SystemImgMapper systemImgMapper;

    @Override
    public int setImg(MultipartFile file, int id) {
        String url = DumpFile.dumpFile(file,"D:\\privateData\\video\\other\\systemImg\\");
        SystemImg systemImg = new SystemImg();
        systemImg.setId(id);
        systemImg.setUrl(url);
        QueryWrapper<SystemImg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return systemImgMapper.update(systemImg,queryWrapper);
    }

    @Override
    public int addImg(MultipartFile file, SystemImg systemImg) {
        String url = DumpFile.dumpFile(file,"D:\\privateData\\video\\other\\systemImg\\");
        systemImg.setUrl(url);
        return systemImgMapper.insert(systemImg);
    }

    @Override
    public List<SystemImg> getPayImg(int type) {
        QueryWrapper<SystemImg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type);
        return systemImgMapper.selectList(queryWrapper);
    }
}
