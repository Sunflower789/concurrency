package com.example.async.service;

import com.example.async.utils.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TestService02 testService02;

    @Autowired
    ApplicationContextUtil applicationContextUtil;

    public void test01(int count) throws InterruptedException {
        //TestService testServiceTemp = applicationContext.getBean(TestService.class);
        TestService testServiceTemp = (TestService) ApplicationContextUtil.getBean("testService");
        for (int i = 0; i < count; i++) {
            testServiceTemp.test01Temp();
        }
    }

    @Async
    public void test01Temp() throws InterruptedException {
        logger.info("start --> Thread name: {},id:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
        TimeUnit.SECONDS.sleep(1);
        //int a = 1/0;
        logger.info("end --> Thread name: {},id:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
    }

    public void test02(int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            testService02.test02Temp();
        }
    }

}
