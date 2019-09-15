package com.lee.tenant;

import com.lee.common.core.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author haitao.li
 */
@RestControllerAdvice
public class TenantExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(TenantExceptionHandler.class);

    /**
     * 无效参数
     * @param e 无效参数
     * @return httpstatus 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public BaseResponse handleIllegalArgument(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return BaseResponse.builder().subCode(String.valueOf(status.value()))
                .subMsg(status.getReasonPhrase()).build();
    }
}
