package com.tiger.sell.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {
    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test(){
        String name = "tiger";
        String password ="123654";
        logger.debug("debug............");
        logger.info("info............");
        logger.info("name:{},password:{}",name,password);
        logger.error("error............");
    }
}
