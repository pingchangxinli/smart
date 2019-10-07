package com.lee.gateway.handler;

import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haitao.li
 */
@Slf4j
public class GateWayErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {
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
        errorAttributes.put("status",HttpStatus.OK.value());
        errorAttributes.put("code",HttpStatus.OK.value());
        errorAttributes.put("msg",HttpStatus.OK.getReasonPhrase());
        if ( error instanceof RetryableException |  error instanceof SocketTimeoutException) {
            errorAttributes.put("subCode",HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorAttributes.put("subCode",HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        if (error instanceof IllegalArgumentException){
            errorAttributes.put("subCode",HttpStatus.BAD_REQUEST.value());
            errorAttributes.put("subMsg",error.getMessage());
        }
        if (error instanceof feign.FeignException.InternalServerError) {
            errorAttributes.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorAttributes.put("subCode",HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorAttributes.put("subMsg",error.getMessage());
        }
        if (error instanceof ResponseStatusException) {
            errorAttributes.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorAttributes.put("subCode",HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorAttributes.put("subMsg",error.getMessage());
        }
        errorAttributes.put("data", null);
        log.error("exception handler path: {},method:{},exception:",request.path(),request.methodName(),
                error);
        return errorAttributes;
    }
}
