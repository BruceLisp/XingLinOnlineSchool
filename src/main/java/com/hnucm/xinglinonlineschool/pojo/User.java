package com.hnucm.xinglinonlineschool.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private int sex;
    private String birthday;
    @TableField(value = "majortype")
    private int majorType;
    private String tel;
    private int type;           //1 表示普通用户， 2 表示讲师， 3 表示管理员， 4 表示讲师审核状态， 5 表示管理员审核状态
    private double balance;
    private String email;
    @TableField(value = "registerdate")
    private String registerDate;
    @TableField(value = "imgurl")
    private String imgUrl;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", majorType=" + majorType +
                ", tel='" + tel + '\'' +
                ", balance=" + balance +
                ", email='" + email + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
