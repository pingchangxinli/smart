package com.lee.common.core.response;

import com.lee.common.core.enums.ResponseStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lee.li
 * 基础相应信息
 **/
@Data
@Builder
public class BaseResponse<T> implements Serializable {
    private static final ResponseStatusEnum RESPONSE_STATUS_OK = ResponseStatusEnum.OK;
    /**
     * 网关返回码
     */
    @Builder.Default
    private String code;
    /**
     * 网关返回码描述
     */
    @Builder.Default
    private String message;

    private T data;

    /**
     * 默认返回成功应答
     *
     * @param data 返回数据
     * @param <T>  返回数据类型
     * @return 返回应答
     */
    public static <T> BaseResponse<T> ok(T data) {
        return BaseResponse.<T>builder()
                .code(RESPONSE_STATUS_OK.getCode()).message(RESPONSE_STATUS_OK.getMessage())
                .data(data)
                .build();
    }


    public static BaseResponse error(Exception e) {
        return error(ResponseStatusEnum.FAILED.getCode(), e.getMessage());
    }

    /**
     * 错误提示返回信息
     *
     * @param code     网关返回code
     * @param codeText 网关返回信息解释
     * @return 返回应答信息
     */
    public static BaseResponse error(String code, String codeText) {
        return BaseResponse.builder()
                .code(code).message(codeText)
                .build();
    }
}
