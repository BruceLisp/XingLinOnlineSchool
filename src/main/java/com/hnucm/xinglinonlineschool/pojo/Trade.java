package com.hnucm.xinglinonlineschool.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class Trade {        //购买课程的支付信息
    private int id;
    @TableField(value = "numofxl")
    private int numOfXL;     //消耗杏林币的数目
    private int cid;
    private int uid;
    private String date;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private Course course;

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", numOfXL=" + numOfXL +
                ", cid=" + cid +
                ", uid=" + uid +
                ", date='" + date + '\'' +
                '}';
    }
}
