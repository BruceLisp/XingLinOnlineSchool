package com.hnucm.xinglinonlineschool.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageReq  implements Serializable {

    private int  size;

    private  int current;

    private  int maxCurrent;

    private  int total;

    private String name;

    private String date;

}