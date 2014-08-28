package com.ejushang.spider.taobao.helper;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * User: Baron.Zhang
 * Date: 13-12-24
 * Time: 下午1:20
 */
public class AppConfig extends Properties{
    private static final String taobaoPropFileName = "taobao.properties";

    private static AppConfig conf= null;
    private static String configFile = null;
    /**
     * 私有构造函数
     *
     */
    private AppConfig(){

    }

    /**
     * 单例模式
     * @return
     */
    public static AppConfig getInstance(String configFileName){
        if (conf==null){
            conf  = new AppConfig();
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

    public static AppConfig getInstance(){
        return getInstance(taobaoPropFileName);
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
