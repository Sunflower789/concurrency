package com.example.async.controller;

import com.example.async.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    TestService testService;

    @PostMapping("/test01")
    public String test01(@RequestHeader("count") Integer count) throws InterruptedException {
        logger.info("***** test01 *****");
        testService.test01(count);
        return "SUCCESS";
    }

    @PostMapping("/test03")
    public String test03(@RequestParam("param") String param,
                         @RequestHeader("count") Integer count,
                         @RequestBody String body) throws InterruptedException {
        logger.info("***** test03 *****");
        return body;
    }
}
