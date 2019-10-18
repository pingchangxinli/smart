package com.lee.auth.server.controller;

import com.lee.auth.server.domain.SysClient;
import com.lee.auth.server.service.SysClientService;
import com.lee.common.core.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lee.li
 */
@RestController
@RequestMapping("/client")
public class SysClientController {
    @Autowired
    private SysClientService sysClientService;

    @GetMapping("/list")
    public BaseResponse list() {
        List<SysClient> list = sysClientService.findList();
        return BaseResponse.builder().data(list).build();

    }

    @GetMapping
    public BaseResponse findClientById(@PathVariable("client_id") String clientId) {
        SysClient client = sysClientService.findClientById(clientId);
        return BaseResponse.builder().data(client).build();
    }

    @PostMapping
    public BaseResponse createClient(@RequestBody SysClient client) {
        Integer count = sysClientService.createClient(client);
        return BaseResponse.builder().data(null).build();
    }
}
