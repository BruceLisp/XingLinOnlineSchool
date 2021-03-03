package com.hnucm.xinglinonlineschool.service.impl;

import com.hnucm.xinglinonlineschool.dao.AliPayTradeMapper;
import com.hnucm.xinglinonlineschool.dao.UserMapper;
import com.hnucm.xinglinonlineschool.dao.plus.AliTradeMappers;
import com.hnucm.xinglinonlineschool.pojo.AliPayTrade;
import com.hnucm.xinglinonlineschool.service.AliPayTradeService;
import com.hnucm.xinglinonlineschool.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AliPayTradeServiceImpl implements AliPayTradeService {

    @Autowired
    AliPayTradeMapper aliPayTradeMapper;
    @Autowired
    AliTradeMappers aliPayTradeMappers;

    @Transactional
    @Override
    public int addTradeReturnKey(AliPayTrade aliPayTrade) {
        aliPayTrade.setNumOfXL(10*(int)aliPayTrade.getAmount());
        aliPayTrade.setDate(DateUtils.getDate());
        aliPayTradeMapper.addTradeReturnKey(aliPayTrade);
        return aliPayTrade.getId();
    }

    @Transactional
    @Override
    public int tradeComplish(int id, String trade_no) {
        aliPayTradeMapper.updateTradeStatus(id);
        AliPayTrade aliPayTrade = aliPayTradeMappers.selectById(id);
        aliPayTrade.setTrade_no(trade_no);
        aliPayTradeMappers.updateById(aliPayTrade);
        return aliPayTradeMapper.updateUserBalance(aliPayTrade);
    }

    @Override
    public List<AliPayTrade> queryAllRecharge() {
        return aliPayTradeMapper.queryAllRecharge();
    }

}
