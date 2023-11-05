package com.hqx.weblog.admin.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于查询用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUserInfoRspVO {
    /**
     * 用户名
     */
    private String username;

}