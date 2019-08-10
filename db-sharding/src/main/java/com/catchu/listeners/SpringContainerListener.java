package com.catchu.listeners;

import com.catchu.builders.XDBMapperScanBuilder;
import com.catchu.builders.XDBPropertiesBuilder;
import com.catchu.builders.XDBSqlSessionFactoryBuilder;
import com.catchu.constants.XDBConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * SPRING容器启动监听
 */
@Component
@Order(value = 2)
public class SpringContainerListener implements ApplicationRunner {
    @Autowired
    Environment environment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new XDBPropertiesBuilder().build(environment);

        if (Boolean.parseBoolean(XDBConstant.KOALA_DISDB_PROPERTIES.getProperty(XDBConstant.KOALA_DISDB_INIT_ON_START, "true"))) {
            new XDBMapperScanBuilder().build();
            XDBSqlSessionFactoryBuilder.getInstance().build();
        }
    }
}