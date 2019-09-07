package com.lee.auth.server.controller;

import com.lee.auth.server.domain.SysClient;
import com.lee.auth.server.service.SysClientService;
import com.lee.common.core.BaseResponseEnum;
import com.lee.common.core.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author haitao.li
 */
@RestController
@RequestMapping("/clients")
public class SysClientController {
    @Autowired
    private SysClientService sysClientService;

    @GetMapping("/list")
    public BaseResponse list() {
        BaseResponseEnum respEnum = BaseResponseEnum.SUCCESS;
        HttpStatus httpStatus = HttpStatus.OK;
        List<SysClient> list = sysClientService.findList();
        return BaseResponse.builder().code(respEnum.getCode()).msg(respEnum.getMessage())
                .subCode(String.valueOf(httpStatus.value())).subMsg(httpStatus.getReasonPhrase())
                .data(list).build();

    }

    @GetMapping("{client_id}")
    public BaseResponse findClientById(@PathParam("client_id") String clientId) {
        BaseResponseEnum respEnum = BaseResponseEnum.SUCCESS;
        HttpStatus httpStatus = HttpStatus.OK;
        SysClient client = sysClientService.findClientById(clientId);
        return BaseResponse.builder().code(respEnum.getCode()).msg(respEnum.getMessage())
                .subMsg(String.valueOf(httpStatus.value())).subMsg(httpStatus.getReasonPhrase())
                .data(client).build();
    }
}
