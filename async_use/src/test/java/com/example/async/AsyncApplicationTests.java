package com.example.async;

import com.example.async.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
@SpringBootTest
class AsyncApplicationTests {

    @Autowired
    TestService testService;

    @Test
    void contextLoads() {
    }

    @Test
    void test01() throws InterruptedException {
        testService.test01(10);
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    void test02() throws InterruptedException {
        testService.test01(10);
        TimeUnit.SECONDS.sleep(10);
    }

}
