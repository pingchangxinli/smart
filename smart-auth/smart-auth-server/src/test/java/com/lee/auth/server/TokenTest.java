package com.lee.auth.server;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author haitao.li
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TokenTest {
    @Autowired
    private MockMvc mockMvc;
    private String clientId = "client";
    private String clientSecret = "123456";
    private String authHeader;

    @Before
    public void basicAuth() throws UnsupportedEncodingException {
        String tmp = clientId + ":" + clientSecret;
        authHeader = "Basic " + Base64.encodeBase64String(tmp.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void token() throws Exception {
        this.mockMvc.perform(post("/oauth/token").header("Authorization", authHeader)
                .param("grant_type", "password")
                .param("username", "user").param("password", "password")
                .param("client_id", "client").param("client_secret", "123456")
                .param("scope", "all")).andDo(print()).andExpect(status().isOk());
    }
}
