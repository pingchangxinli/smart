package com.lee.gateway.handler;

import com.lee.common.core.enums.BaseResponseEnum;
import com.lee.common.core.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lee.li
 */
@Slf4j
public class GateWayErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {
    private static final String RESP_MESS_UNAUTHORIZED = "授权验证失败，请在请求参数中添加access_token" +
            "或者header中添加Authorization";
    public GateWayErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }


    @Override
    protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
        log.debug("GateWayErrorWebExceptionHandler hello getHttpStatus");
        return super.getHttpStatus(errorAttributes);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        // 这里其实可以根据异常类型进行定制化逻辑
        Throwable error = super.getError(request);
        Map<String, Object> errorAttributes = new HashMap<>(8);
        BaseResponseEnum baseResponse = BaseResponseEnum.SUCCESS;
        errorAttributes.put("status", baseResponse.getCode());
        errorAttributes.put("code", baseResponse.getCode());
        errorAttributes.put("msg", baseResponse.getMessage());
        errorAttributes.put("data", null);
        log.error("[GatewayErrorWebExceptionHandler],path: {},method:{},exception name:{};",
                request.path(),
                request.methodName(),
                error.getClass().getName(),error);
        if ( error instanceof feign.RetryableException ||  error instanceof SocketTimeoutException
                || error instanceof feign.FeignException.InternalServerError || error instanceof ResponseStatusException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorAttributes.put("status",status.value());
            errorAttributes.put("sub_code",status.value());
            errorAttributes.put("sub_msg", error.getMessage());
            return errorAttributes;
        }
        if (error instanceof IllegalArgumentException){
            errorAttributes.put("sub_code",HttpStatus.BAD_REQUEST.value());
            errorAttributes.put("sub_msg", error.getMessage());
            return errorAttributes;
        }
        if (error instanceof feign.FeignException.Unauthorized) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            errorAttributes.put("status",status.value());
            errorAttributes.put("sub_code",status.value());
            errorAttributes.put("sub_msg", RESP_MESS_UNAUTHORIZED);
            return errorAttributes;
        }
        if (error instanceof Exception) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorAttributes.put("status",status.value());
            errorAttributes.put("sub_code",status.value());
            errorAttributes.put("sub_msg", error.getMessage());
            return errorAttributes;
        }
        return  errorAttributes;
    }
}
