package com.catchu.listeners;

import com.catchu.builders.XCuratorBuilder;
import com.catchu.builders.XIDWorkerConfigurationBuilder;
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
@Order(value = 1)
public class XIDWorkerSpringContainerListener implements ApplicationRunner {

    @Autowired
    private Environment environment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        XIDWorkerConfigurationBuilder.getInstance().build(environment);
        XCuratorBuilder.getInstance().build();
    }
}
