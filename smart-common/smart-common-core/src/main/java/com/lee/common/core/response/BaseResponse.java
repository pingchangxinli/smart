package com.lee.common.core.response;

import com.lee.common.core.enums.BaseResponseEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author haitao.li
 * 基础相应信息
 **/
@Data
@Builder
public  class BaseResponse implements Serializable {
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
    private String subCode = String.valueOf(HttpStatus.OK.value());
    /**
     * 服务返回信息
     */
    @Builder.Default
    private String subMsg = HttpStatus.OK.getReasonPhrase();

    private Object data;
}
