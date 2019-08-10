package com.catchu.builders;

import com.catchu.constants.XDBConstant;
import com.catchu.utils.PropertiesUtil;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * DB配置参数创建者
 */
public class XDBPropertiesBuilder extends XDBBuilder<Environment> {
    @Override
    public void build(Environment environment) {
        String env = environment.getActiveProfiles()[0];
        Properties pro = PropertiesUtil.getProperties(("application-" + env + ".properties"));
        XDBConstant.KOALA_DISDB_PROPERTIES = pro;
    }
}
