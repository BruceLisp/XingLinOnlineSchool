package com.hnucm.xinglinonlineschool.service.impl.plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnucm.xinglinonlineschool.dao.plus.AliTradeMappers;
import com.hnucm.xinglinonlineschool.pojo.AliPayTrade;
import com.hnucm.xinglinonlineschool.service.plus.AliPayTradeServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliPayTradeServiceImpls extends ServiceImpl<AliTradeMappers, AliPayTrade>
        implements AliPayTradeServices {

    @Override
    public List<AliPayTrade> queryAllTrades(int current, int size) {            //分页得到得到充值记录的数据
        Page<AliPayTrade> page = new Page<>(current,size);
        QueryWrapper<AliPayTrade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        IPage<AliPayTrade> iPage = this.getBaseMapper().selectPage(page,queryWrapper);
        return iPage.getRecords();
    }

    @Override
    public int getTradesSize() {            //得到充值记录的数据条目的数量
        QueryWrapper<AliPayTrade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        return baseMapper.selectCount(queryWrapper);
    }
}
