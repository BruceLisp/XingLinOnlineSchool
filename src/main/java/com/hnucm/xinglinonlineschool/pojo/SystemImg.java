package com.hnucm.xinglinonlineschool.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("systemimg")
public class SystemImg {
    private int id;
    private String describle;
    private String url;
    private int type;       //1、支付界面    、、 2、首页轮转图

    @Override
    public String toString() {
        return "SystemImg{" +
                "id=" + id +
                ", describle='" + describle + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                '}';
    }
}
