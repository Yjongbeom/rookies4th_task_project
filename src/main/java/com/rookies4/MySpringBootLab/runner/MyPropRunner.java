package com.rookies4.MySpringBootLab.runner;


import com.rookies4.MySpringBootLab.property.MyPropProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class MyPropRunner implements ApplicationRunner {

    @Value("${myprop.username}")
    String username;

    @Value("${myprop.port}")
    int port;

    @Autowired
    private MyPropProperties myPropProperties;

    private Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("username = {}", username);
        logger.debug("port = {}", port);

        logger.debug("Properties myprop.username = " + username);
        logger.debug("Properties myprop.port = " + port);

        logger.info("username = " + myPropProperties.getUsername());
        logger.info("port = " + myPropProperties.getPort());
    }
}