package com.hnucm.xinglinonlineschool.utils;

public class AuthCode {             //静态方法生成随机验证码，包括大写小写字码和数字
    public static String getAuthCode() {
        String result="";
        for(int i=0;i<6;i++){
            int a = 0;
            while(!((a>47&&a<58)||(a>64&&a<91)||(a>96&&a<123))){
                a=(int)(Math.random()*128);
            }
            result=result+(char)+a;
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println("请输入验证码："+AuthCode.getAuthCode());
    }
}
