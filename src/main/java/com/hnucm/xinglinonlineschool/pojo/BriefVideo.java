package com.hnucm.xinglinonlineschool.pojo;

import lombok.Data;

@Data
public class BriefVideo {
    private int id;
    private String label;

    @Override
    public String toString() {
        return "BriefVideo{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}