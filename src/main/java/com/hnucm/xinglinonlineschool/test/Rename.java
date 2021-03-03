package com.hnucm.xinglinonlineschool.test;

import java.io.File;
import java.io.IOException;

public class Rename {
    public static void main(String[] args){
        String path = "D:\\privateData\\video\\";
        String oldName = "test";
        String newName = "yes";
        raname(newName,oldName,path);
    }
    public static void raname() throws IOException {
        File oldFile = new File("d:/PMS");
        if(!oldFile.exists()) {
            oldFile.createNewFile();
        }
        System.out.println("修改前文件bai名称是："+oldFile.getName());
        String rootPath = oldFile.getParent();
        System.out.println("根路径是："+rootPath);
        File newFile = new File(rootPath + File.separator + "PMSTmp");
        System.out.println("修改后文件名称是："+newFile.getName());
        if(oldFile.renameTo(newFile))
        {
            System.out.println("修改成功!");
        }else{
            System.out.println("修改失败");
        }
    }

    public static void raname(String newName,String oldName,String path){
        File oldFile = new File(path+oldName);
        oldFile.renameTo(new File(path+newName));
    }
}