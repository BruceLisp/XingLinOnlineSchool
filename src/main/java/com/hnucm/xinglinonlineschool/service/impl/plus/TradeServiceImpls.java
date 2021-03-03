package com.hnucm.xinglinonlineschool.service.impl.plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnucm.xinglinonlineschool.dao.CourseMapper;
import com.hnucm.xinglinonlineschool.dao.plus.CourseMappers;
import com.hnucm.xinglinonlineschool.dao.plus.TradeMappers;
import com.hnucm.xinglinonlineschool.pojo.PageReq;
import com.hnucm.xinglinonlineschool.pojo.Trade;
import com.hnucm.xinglinonlineschool.service.plus.TradeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpls extends ServiceImpl<TradeMappers, Trade>
        implements TradeServices {

    @Autowired
    CourseMappers courseMappers;

    @Override
    public List<Trade> queryAllTrades(int current, int size) {
        Page<Trade> page = new Page<>(current,size);
        QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
        IPage<Trade> iPage = this.getBaseMapper().selectPage(page,queryWrapper);
        return iPage.getRecords();
    }

    @Override
    public List<Trade> queryTradeById(int id, PageReq pageReq) {
        Page<Trade> page = new Page<>(pageReq.getCurrent(),pageReq.getSize());
        QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",id);
        IPage<Trade> iPage = this.getBaseMapper().selectPage(page,queryWrapper);
        List<Trade> trades = iPage.getRecords();
        for(Trade trade:trades){
            trade.setCourse(courseMappers.selectById(trade.getCid()));
        }
        return trades;
    }

    @Override
    public int queryUserTradeSize(int id) {
        QueryWrapper<Trade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",id);
        return baseMapper.selectCount(queryWrapper);
    }
}
