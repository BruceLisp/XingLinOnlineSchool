package com.hnucm.xinglinonlineschool.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnucm.xinglinonlineschool.pojo.Trade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TradeMappers extends BaseMapper<Trade> {
}
