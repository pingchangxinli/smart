package com.lee.common.core.response;

import com.lee.common.core.GateWayCode;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author lee.li
 * 基础相应信息
 **/
@Data
@Builder
public class BaseResponse<T> implements Serializable {
    private static final GateWayCode DEFAULT_GATEWAY_CODE = GateWayCode.SUCCESS;
    /**
     * 默认应用服务返回状态
     */
    private static final HttpStatus DEFAULT_STATUS = HttpStatus.OK;
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
    /**
     * 应用服务状态码
     */
    @Builder.Default
    private String subCode;
    /**
     * 应用服务状态信息
     */
    @Builder.Default
    private String subMessage;

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
                .code(DEFAULT_GATEWAY_CODE.getCode()).message(DEFAULT_GATEWAY_CODE.getMessage())
                .data(data)
                .subCode(String.valueOf(DEFAULT_STATUS.value())).subMessage(DEFAULT_STATUS.getReasonPhrase())
                .build();
    }

    /**
     * 错误提示返回信息
     *
     * @param subCode    应用服务状态
     * @param subMessage 应用服务状态信息
     * @return 返回应答信息
     */
    public static BaseResponse error(String subCode, String subMessage) {
        return error(DEFAULT_GATEWAY_CODE.getCode(), DEFAULT_GATEWAY_CODE.getMessage(), subCode, subMessage);
    }

    /**
     * 错误提示返回信息
     *
     * @param code       网关返回code
     * @param codeText   网关返回信息解释
     * @param subCode    应用服务状态
     * @param subMessage 应用服务状态信息
     * @return 返回应答信息
     */
    public static BaseResponse error(String code, String codeText, String subCode, String subMessage) {
        return BaseResponse.builder()
                .code(code).message(codeText)
                .subCode(subCode).subMessage(subMessage)
                .build();
    }
}
