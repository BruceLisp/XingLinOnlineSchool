package com.hnucm.xinglinonlineschool.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getDate() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
