package com.lee.common.core.response;

import com.lee.common.core.BaseResponseEnum;
import lombok.Builder;

import java.io.Serializable;

/**
 * @author haitao.li
 * 基础相应信息
 **/
@Builder
public  class BaseResponse implements Serializable {
    {
        code = BaseResponseEnum.SUCCESS.getCode();
        msg = BaseResponseEnum.SUCCESS.getMessage();
    }
    /**
     * 网关返回码
     */
    public String code;
    /**
     * 网关返回码描述
     */
    public String msg;
}
