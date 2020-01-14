package com.lee.tenant.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lee.li
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TenantControllerTest {
    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createTenant() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        MultiValueMap map = new LinkedMultiValueMap();

        try {
            this.mvc.perform(
                    MockMvcRequestBuilders.post("/tenant").
                            param("id", "2").
                            param("name", "tenant name").
                            param("create_time", formatter.format(LocalDateTime.now())).
                            param("update_time", formatter.format(LocalDateTime.now())).
                            param("stauts", "0").
                            param("domain", "127.0.0.1").
                            param("operator", "ivy")
            )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void list() {
    }

    @Test
    public void findTenantById() {
    }

    @Test
    public void findTenantByDomain() {
    }

    @Test
    public void findTenantIdByDomain() {
    }
}