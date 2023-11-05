package com.hqx.weblog.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义用户名或密码不能为空异常
 */
public class UsernameOrPasswordNullException extends AuthenticationException {

    public UsernameOrPasswordNullException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UsernameOrPasswordNullException(String msg) {
        super(msg);
    }
}