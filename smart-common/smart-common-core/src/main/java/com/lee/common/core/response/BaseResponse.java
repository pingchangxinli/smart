package com.lee.common.core.response;

import com.lee.common.core.GateWayResponseEnum;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author haitao.li
 * @Date 2019-08-05
 * @Desc 基础相应信息
 **/
public  class BaseResponse implements Serializable {
    {
        code = GateWayResponseEnum.SUCCESS.getCode();
        msg = GateWayResponseEnum.SUCCESS.getMessage();
    }
    /**
     * 网关返回码
     */
    public String code;
    /**
     * 网关返回码描述
     */
    public String msg;

    /**
     * 当前消息返回时间戳
     */
    private LocalDateTime timestamp;
}
