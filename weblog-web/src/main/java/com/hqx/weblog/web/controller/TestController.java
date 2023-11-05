package com.hqx.weblog.web.controller;

import com.hqx.weblog.common.aspect.ApiOperationLog;
import com.hqx.weblog.common.utils.JsonUtil;
import com.hqx.weblog.common.utils.Response;
import com.hqx.weblog.web.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * @Description
 * @Create by hqx
 * @Date 2023/9/6 18:51
 */
@Api(tags = "首页模块")
@RestController
@Slf4j
public class TestController {

    @ApiOperation(value = "测试接口")
    @PostMapping("/admin/test")
    @ApiOperationLog(description = "测试接口")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // 用户权限为 ROLE_ADMIN 才能访问该接口
    public Response<User> test(@RequestBody @Validated User user) {
        // 打印入参
        log.info(JsonUtil.toJsonString(user));

        // 设置三种日期字段值
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateDate(LocalDate.now());
        user.setTime(LocalTime.now());

        return Response.success(user);
    }

}
