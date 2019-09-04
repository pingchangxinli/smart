package com.lee.admin.response;

import com.lee.common.core.response.BaseResponse;
import lombok.*;

/**
 * 管理相应类
 * @author haitao.li
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public  class AdminResponse<T> extends BaseResponse {
    /**
     * 业务响应码
     */
    private String subCode;
    /**
     * 业务响应信息
     */
    private String subMessage;
    /**
     * 响应对象
     */
    private T data;

}
