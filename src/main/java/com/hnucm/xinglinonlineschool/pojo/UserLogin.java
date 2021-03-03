package com.hnucm.xinglinonlineschool.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("userlogin")
public class UserLogin {
    private int id;
    private String username;
    private String password;
    @TableField(exist = false)
    private String oldPwd;
    private int uid;
    private int type;

    @Override
    public String toString() {
        return "UserLogin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", oldPwd='" + oldPwd + '\'' +
                ", uid=" + uid +
                ", type=" + type +
                '}';
    }
}
