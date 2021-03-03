package com.hnucm.xinglinonlineschool.service;

import com.hnucm.xinglinonlineschool.pojo.AliPayTrade;

import java.util.List;

public interface AliPayTradeService {
    public int  addTradeReturnKey(AliPayTrade aliPayTrade);
    public int  tradeComplish(int id, String trade_no);        //付款成功，订单完成：修改订单状态，并修改用户杏林币余额
    public List<AliPayTrade> queryAllRecharge();
}
