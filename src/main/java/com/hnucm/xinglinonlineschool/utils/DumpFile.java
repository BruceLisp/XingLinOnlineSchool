package com.hnucm.xinglinonlineschool.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DumpFile {

    /**
     * 文件转储及文件重命名  （将课程数据放在该讲师的文件夹下的对应的课程文件夹里面）
     * @param file      //文件
     * @param uid       教师的id
     * @param cid       课程号
     * @return
     */
    public static String dumpFile(MultipartFile file,int uid,int cid){     //转存单个文件
        if(file.isEmpty()){
            return null;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "D:\\privateData\\video\\other\\"+uid+"\\"+cid+"\\";    //使用用户的uid作为上上级目录，cid作为上级目录
        File dest = new File(path + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest); //保存文件
            boolean isPermit = CheckoutFileType.getUpFilelegitimacyFlag(dest);
            System.out.println("是否可以上传："+isPermit);
            if(!isPermit){      //如果不允许上传该类型的文件，抛出异常
                throw new IllegalStateException();
            }

            return path+fileName;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件转储及文件重命名
     * @param file      文件
     * @param id        文件上传的人的id
     * @return
     */
    public static String dumpFile(MultipartFile file,int id){     //转存单个文件
        if(file.isEmpty()){
            return null;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "D:\\privateData\\video\\other\\"+id+"\\";    //使用用户的id作为上级目录
        File dest = new File(path + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            boolean isPermit = CheckoutFileType.getUpFilelegitimacyFlag(dest);
            System.out.println("是否可以上传："+isPermit);
            if(!isPermit){      //如果不允许上传该类型的文件，抛出异常
                throw new IllegalStateException();
            }
            return path+fileName;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dumpFile(MultipartFile file,String path){     //转存单个文件
        if(file.isEmpty()){
            return null;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        File dest = new File(path + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdirs();
        }
        try {

            file.transferTo(dest); //保存文件
            boolean isPermit = CheckoutFileType.getUpFilelegitimacyFlag(dest);
            System.out.println("是否可以上传："+path+fileName);
            System.out.println("是否可以上传："+isPermit);
            if(!isPermit){      //如果不允许上传该类型的文件，抛出异常
                throw new IllegalStateException();
            }

            return path+fileName;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 多文件上传
     * @param files
     * @return
     */
    public static boolean dumpMultiFile(List<MultipartFile> files){
        if(files.size()<=0){
            return false;
        }
        String path = "D:\\privateData\\video\\other\\";

        for(MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            System.out.println("fileName --> "+fileName);
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            if(file.isEmpty()){
                return true;
            }else{
                File dest = new File(path + fileName);
                if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                    boolean isPermit = CheckoutFileType.getUpFilelegitimacyFlag(dest);
                    System.out.println("是否可以上传："+isPermit);
                }catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            /*if(model.equals("video")){              //判断文件上传的格式做不同的存储
                videoService.dumpVideo(path+fileName);
            }else{
                System.out.println("不支持该格式文件的上传");
            }*/
        }
        return true;
    }
}
