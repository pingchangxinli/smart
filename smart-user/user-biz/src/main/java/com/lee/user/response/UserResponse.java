package com.lee.user.response;

import com.lee.common.core.response.BaseResponse;
import lombok.*;


/**
 * @author haitao.li
 * 基础业务返回信息,所有的服务基于此类操作
 */
@SuppressWarnings({"ALL"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse<T> extends BaseResponse {
    /**
     * 业务返回码
     */
    @NonNull
    private String subCode;
    /**
     * 业务返回码描述
     */
    @NonNull
    private String subMsg;
    /**
     * 实际内容
     */

    private T data;

}
