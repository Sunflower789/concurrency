package com.example.async.controller;

import com.example.async.service.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class TestControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(TestControllerTest.class);

    @Autowired
    TestService testService;

    @Autowired
    private WebApplicationContext wac;

    //@MockBean
    MockMvc mvc;

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        //this.mvc = MockMvcBuilders.standaloneSetup(new TestControllerTest()).build();
    }

    @Test
    void testMvc() throws Exception {
        MvcResult result =
                this.mvc.perform(MockMvcRequestBuilders.post("/v1/test01")
                .header("count","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        logger.info("result: {} ", result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    void testWithMockMvc() throws Exception {
        MvcResult result =
                this.mvc.perform(MockMvcRequestBuilders.post("/v1/test03")
                        .header("count","1")
                        .param("param","param")
                        .content("test")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        logger.info("result: {} ", result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        TimeUnit.SECONDS.sleep(5);
    }

}