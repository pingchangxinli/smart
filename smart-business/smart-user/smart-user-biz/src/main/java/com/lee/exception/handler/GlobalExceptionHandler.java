package com.lee.exception.handler;

import com.lee.common.core.response.BaseResponse;
import com.lee.exception.RoleExistException;
import com.lee.exception.BusinessUnitNotExistedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;


/**
 * @author haitao Li
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 无效参数
     * @param e 无效参数exception
     * @return httpstatus 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse handleIllegalArgument(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return response(status,e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return response(status,e);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse handleSqlException(SQLException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return response(status, e);
    }

    @ExceptionHandler(RoleExistException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public BaseResponse handleRoleExistException(RoleExistException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return response(status, e);
    }


    @ExceptionHandler(BusinessUnitNotExistedException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public BaseResponse handleshopNotExistException(BusinessUnitNotExistedException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return response(status, e);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse handleRuntimeException(RuntimeException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return response(status,e);
    }

    private BaseResponse response(HttpStatus status,Exception e) {
        log.error("[GlobalExceptionHandler.response]", e);
        return BaseResponse.error(String.valueOf(status.value()), e.getLocalizedMessage());
    }
}
