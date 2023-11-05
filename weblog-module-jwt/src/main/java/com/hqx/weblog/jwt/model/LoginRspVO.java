package com.hqx.weblog.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应请求的内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRspVO {

    /**
     * Token 值
     */
    private String token;

}