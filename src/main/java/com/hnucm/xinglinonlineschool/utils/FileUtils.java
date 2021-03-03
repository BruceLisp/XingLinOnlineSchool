package com.hnucm.xinglinonlineschool.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    /**
     * 删除文件，可以是文件或文件夹
     * @param fileName
     * @return 删除成功为true,反之为false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     * @param fileName
     * @return 删除成功为true,反之为false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     * @param dir
     * @return 删除成功为true,反之为false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtils.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtils.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }


    /**
     *
     * @param filePath
     * @return
     */

    // 创建目标文件
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("目标文件已经存在" + filePath);
            System.err.println("日志信息：目标文件已经存在"+filePath+"日志日期:"+new SimpleDateFormat("yyyy/MM/dd:HH:mm:ss").format(new Date()));
            return false;
        }
        // 判断文件是否为目录
        if (filePath.endsWith(file.separator)) {
            System.out.println("目标文件不能为目录！");
        }
        if (!file.getParentFile().exists()) {// 判断目标文件所在的目录是否存在
            System.out.println("目标文件所在的目录不存在，准备创建！");
            if (!file.getParentFile().mkdirs()) {// 判断创建目录是否成功
                System.out.println("创建目标文件所在的目录失败！");
                return false;
            }
        }
        try {
            // 创建文件
            if (file.createNewFile()) {
                System.out.println("创建文件成功：" + filePath);
                return true;
            } else {
                System.out.println("创建文件失败！");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("创建文件失败！" + e.getMessage());

            return false;
        }
    }

    // 创建目录
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录失败！" + dir.getPath());
            return false;
        }
        if (destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        if (dir.mkdirs()) {
            System.out.println("创建目录成功！" + destDirName);
            return true;

        } else {
            System.out.println("创建目录失败！");
            return false;
        }
    }

    //创建临时文件
    public static String createTempFile(String prefix,String suffix,String dirName){
        File tempFile= null;
        if (dirName==null) {
            try {
                tempFile=File.createTempFile(prefix, suffix);
                return tempFile.getCanonicalPath();
            } catch (Exception e) {
                System.out.println("创建临时文件失败："+e.getMessage());
                return null;
            }
        }else{
            File dir=new File(dirName);
            if (!dir.exists()) {
                if (createDir(dirName)) {
                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try {
                tempFile=File.createTempFile(prefix, suffix,dir);
                return tempFile.getCanonicalPath();
            } catch (Exception e) {
                e.getStackTrace();
                System.out.println("创建临时文件失败！"+e.getMessage());
                return null;
            }
        }
    }

    public static void main(String[] args) {
//  // 删除单个文件
        String file = "D:\\privateData\\video\\other\\d1.mp4";
        File file1 = new File(file);
        System.out.println("Parent："+file1.getParent());
//  System.out.println();
        // 删除一个目录
     /*   String dir = "D:/home/web/upload/upload/files";
        FileUtils.deleteDirectory(dir);*/
//  System.out.println();
//  // 删除文件
//  dir = "c:/test/test0";
//  DeleteFileUtil.delete(dir);
//        createFile create = new createFile();
//        create.createFile("d://create//temp.txt");
//        create.createDir("d://create2//");
//        String prefix="temp";
//        String suffix=".txt";
//        for (int i = 0; i < 10; i++) {
//            System.out.println("您创建的临时文件为："+create.createTempFile(prefix, suffix, "d://create2//"));
//        }

    }



}
