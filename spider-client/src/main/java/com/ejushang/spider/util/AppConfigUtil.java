package com.ejushang.spider.util;

import java.io.*;
import java.util.Properties;

/**
 * 读取配置文件工具类
 * User: Baron.Zhang
 * Date: 14-1-02
 * Time: 下午1:20
 */
public class AppConfigUtil extends Properties{
    private static final String configFileName = "config.properties";

    private static AppConfigUtil conf= null;
    private static String configFile = null;
    /**
     * 私有构造函数
     *
     */
    private AppConfigUtil(){

    }

    /**
     * 单例模式
     * @return
     */
    public static AppConfigUtil getInstance(String configFileName){
        if (conf==null){
            conf  = new AppConfigUtil();
            try{
                InputStream is =  Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName);
                conf.load(is);
                is.close();
            } catch (IOException e) {
                System.err.println(configFileName+"文件读取失败！");
            }
        }
        return conf;
    }

    public static AppConfigUtil getInstance(){
        return getInstance(configFileName);
    }


    /**
     * 存储到props文件的格式中
     */
    public void storeToProps(){
        FileOutputStream fos=null;
        try {
            fos = new FileOutputStream(configFile);
        } catch (FileNotFoundException e1) {
            System.out.println("文件未找到："+configFile+e1.toString());
        }

        try {
            conf.store(fos,"");
        }catch (FileNotFoundException e) {
            System.err.println("配置文件"+configFile+"找不到！！\n"+e.toString());
        }catch (Exception e) {
            System.err.println("读取配置文件"+configFile+"错误！！\n"+e.toString());
        }

    }


}
