package com.lee.exception.handler;

import com.lee.common.core.response.BaseResponse;
import com.lee.role.exception.RoleExistException;
import com.lee.tenant.exception.CostCenterNotExistedException;
import com.lee.tenant.exception.TenantExistedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author lee.li
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
    public BaseResponse handleIllegalArgument(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return response(status,e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public BaseResponse handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return response(status,e);
    }
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public BaseResponse handleSqlException(SQLException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return response(status,e);
    }
    @ExceptionHandler(RoleExistException.class)
    @ResponseBody
    public BaseResponse handleRoleExistException(RoleExistException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return response(status,e);
    }
    @ExceptionHandler(TenantExistedException.class)
    @ResponseBody
    public BaseResponse handleTenantExistedException(TenantExistedException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return response(status,e);
    }
    @ExceptionHandler(CostCenterNotExistedException.class)
    @ResponseBody
    public BaseResponse handleCostCenterNotExistException(CostCenterNotExistedException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return response(status,e);
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public BaseResponse handleRuntimeException(RuntimeException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return response(status,e);
    }
    private BaseResponse response(HttpStatus status,Exception e) {
        log.error("[GlobalExceptionHandler.response]", e);
        return BaseResponse.builder().subCode(String.valueOf(status.value()))
                .subMsg(e.getLocalizedMessage()).build();
    }
}
