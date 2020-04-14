package com.lee.auth.server.controller;

import com.lee.auth.server.domain.SysClient;
import com.lee.auth.server.service.SysClientService;
import com.lee.common.core.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author haitao Li
 */
@Slf4j
@RestController
@RequestMapping("/client")
public class SysClientController {
    @Autowired
    private SysClientService sysClientService;

    @GetMapping("/list")
    public BaseResponse list() {
        List<SysClient> list = sysClientService.findList();
        return BaseResponse.ok(list);

    }

    @GetMapping
    public BaseResponse findClientById(@PathVariable("client_id") String clientId) {
        SysClient client = sysClientService.findClientById(clientId);
        return BaseResponse.ok(client);
    }

    @PostMapping
    public BaseResponse createClient(@RequestBody SysClient client) {
        if (log.isDebugEnabled()) {
            log.debug("[SysClientController createClient] param:" + client);
        }
        Integer count = sysClientService.createClient(client);
        return BaseResponse.ok(null);
    }
}
