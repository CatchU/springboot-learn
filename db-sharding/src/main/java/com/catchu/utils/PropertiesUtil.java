package com.catchu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties getProperties(String filePath) {
        Properties properties = new Properties();
        try {
            InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
            properties.load(in);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
