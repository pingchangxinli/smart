package com.lee.common.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lee.common.core.enums.BaseResponseEnum;
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
public  class BaseResponse<T> implements Serializable {
    /**
     * 网关返回码
     */
    @Builder.Default
    private String code = BaseResponseEnum.SUCCESS.getCode();
    /**
     * 网关返回码描述
     */
    @Builder.Default
    private String msg =BaseResponseEnum.SUCCESS.getMessage();
    /**
     * 服务返回码
     */
    @Builder.Default
    @JsonProperty("sub_code")
    private String subCode;
    /**
     * 服务返回信息
     */
    @Builder.Default
    @JsonProperty("sub_msg")
    private String subMsg;

    private T data;
}
