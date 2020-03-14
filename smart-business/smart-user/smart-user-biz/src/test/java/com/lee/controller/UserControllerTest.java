package com.lee.controller;

import com.lee.SmartUserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author lee.li
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SmartUserApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void currentUserTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/user/currentUser").header("Authorization", "Bearer 7a3ae75e-2cc5" +
                "-4e9c-b0ee-197a6334bba")).andExpect(status().isOk());
    }
}
