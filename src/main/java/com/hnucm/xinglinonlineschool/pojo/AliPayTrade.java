package com.hnucm.xinglinonlineschool.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;

@Data
@TableName("recharge")
public class AliPayTrade {      //存储调用支付宝钱包支付时，记录支付成功的回调信息
    private int id;
    private String trade_no;
    private double amount;
    private int uid;
    private String date;
    private int status;
    @TableField(value = "numofxl")
    private int numOfXL;    //充值杏林币的数量

    @Override
    public String toString() {
        return "AliPayTrade{" +
                "id=" + id +
                ", trade_no=" + trade_no +
                ", amount=" + amount +
                ", uid=" + uid +
                ", date='" + date + '\'' +
                ", status=" + status +
                ", numOfXL=" + numOfXL +
                '}';
    }
}
