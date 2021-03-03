package com.hnucm.xinglinonlineschool.configure;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MyConfigures {
    @Bean
    public MultipartConfigElement multipartConfigElement() {        //配置文件传输的大小
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //factory.setMaxFileSize(1024);
        //单个文件最大
        factory.setMaxFileSize(DataSize.parse("102400KB")); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("102400KB"));
        return factory.createMultipartConfig();
    }
}
