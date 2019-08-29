package com.lee.uid.provider;

import com.lee.uid.provider.UidGeneratorApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author haitao.li
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UidGeneratorApiTest {
    private static final Logger log = LoggerFactory.getLogger(UidGeneratorApiTest.class);
    @Autowired
    private UidGeneratorApi uidGeneratorApi;
    private MockMvc mvc;
    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(uidGeneratorApi).build();
    }

    @Test
    public void testGetUid(){
        RequestBuilder builder = MockMvcRequestBuilders.get("/uid");
        try {
            MvcResult result = mvc.perform(builder).andReturn();
            log.info("UID GENERATOR API TEST,RESULT:{}",result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
