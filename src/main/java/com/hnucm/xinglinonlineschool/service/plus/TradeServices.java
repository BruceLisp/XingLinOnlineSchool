package com.hnucm.xinglinonlineschool.service.plus;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnucm.xinglinonlineschool.pojo.PageReq;
import com.hnucm.xinglinonlineschool.pojo.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TradeServices extends IService<Trade> {
    public List<Trade> queryAllTrades(int current, int size);
    public List<Trade> queryTradeById(int id, PageReq pageReq);
    public int queryUserTradeSize(int id);
}
