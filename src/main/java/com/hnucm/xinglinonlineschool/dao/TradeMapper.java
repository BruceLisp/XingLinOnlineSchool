package com.hnucm.xinglinonlineschool.dao;

import com.hnucm.xinglinonlineschool.pojo.Trade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TradeMapper {
    public int addTradeReturnId(Trade trade);
    public List<Trade> queryAllTradeByUid(int id);
    public List<Trade> queryAllTrade();             //得到所有的课程购买信息
}
