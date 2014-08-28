package com.ejushang.spider.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: liubin
 * Date: 14-1-13
 */
public class PermissionUtils {

    private static final Logger log = LoggerFactory.getLogger(PermissionUtils.class);

    /**
     * 去除url后缀,调用前:doc.xxx.html,调用后:doc.xxx
     * @param url
     * @return
     */
    public static String trimUrlSuffix(String url) {
        if(StringUtils.isBlank(url)) return url;
        int index = url.lastIndexOf(".");
        if(index == -1) return url;
        return url.substring(0, index);
    }

}
