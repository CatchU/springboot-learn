package com.catchu.listener;

import com.catchu.config.ESAdminComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 评论组件SPRING容器启动监听
 */
@Slf4j
@Component
public class CommentSpringContainerListener implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 初始化ES配置
         */
        ESAdminComponent.getInstance().initES();
    }
}
