package com.example.async.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TestService02 {

    private static final Logger logger = LoggerFactory.getLogger(TestService02.class);

    @Async
    public void test02Temp() throws InterruptedException {
        logger.info("start --> Thread name: {},id:{}",Thread.currentThread().getName(),Thread.currentThread().getId());
        TimeUnit.SECONDS.sleep(1);
        //int a = 1/0;
        logger.info("end --> Thread name: {},id:{}",Thread.currentThread().getName(),Thread.currentThread().getId());
    }

}
