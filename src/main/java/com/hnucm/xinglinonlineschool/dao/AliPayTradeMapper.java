package com.hnucm.xinglinonlineschool.dao;


import com.hnucm.xinglinonlineschool.pojo.AliPayTrade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AliPayTradeMapper {
    public int  addTradeReturnKey(AliPayTrade aliPayTrade);         //添加订单信息，并返回订单的id
    public int  updateTradeStatus(int id);          //修改订单状态
    public AliPayTrade  queryTradeById(int id);     //根据id查询订单信息
    public int  updateUserBalance(AliPayTrade aliPayTrade);     //修改用户的杏林币余额
    public List<AliPayTrade> queryAllRecharge();
}
