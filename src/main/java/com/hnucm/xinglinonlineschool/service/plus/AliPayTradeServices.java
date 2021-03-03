package com.hnucm.xinglinonlineschool.service.plus;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnucm.xinglinonlineschool.pojo.AliPayTrade;
import com.hnucm.xinglinonlineschool.pojo.Trade;

import java.util.List;

public interface AliPayTradeServices extends IService<AliPayTrade> {
    public List<AliPayTrade> queryAllTrades(int current, int size);
    public int getTradesSize();
}
