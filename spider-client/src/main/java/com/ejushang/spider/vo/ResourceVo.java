package com.ejushang.spider.vo;

/**
 * User:moon
 * Date: 14-2-11
 * Time: 上午10:07
 */
public class ResourceVo {

    private  String name;
    /** 前端展示需要使用的属性 */
    private String iconCls;
    /** 前端展示需要使用的属性 */
    private String module;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
}
