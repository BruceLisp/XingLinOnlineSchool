package com.hnucm.xinglinonlineschool.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnucm.xinglinonlineschool.pojo.AliPayTrade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AliTradeMappers extends BaseMapper<AliPayTrade> {
}
