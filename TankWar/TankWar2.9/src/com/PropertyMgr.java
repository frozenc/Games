package com;

import javafx.beans.property.Property;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/23 9:54
 */
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
