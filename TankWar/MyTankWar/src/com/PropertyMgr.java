package com;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/29 20:51
 */

/*配置文件管理类*/
public class PropertyMgr {
    static Properties props = new Properties();

    static {
        try {
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PropertyMgr() {};

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}

