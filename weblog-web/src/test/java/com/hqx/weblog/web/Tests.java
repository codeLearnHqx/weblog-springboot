package com.hqx.weblog.web;

import com.hqx.weblog.common.domain.dos.UserDO;
import com.hqx.weblog.common.domain.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Create by hqx
 * @Date 2023/10/12 2:12
 * @Description
 */

@SpringBootTest
class Tests {

    @Autowired
    private UserMapper userMapper;


    @Test
    void insertTest() {
        // 构建数据库实体类
        UserDO userDO = UserDO.builder()
                .username("sb111")
                .password("123456")
                .createTime(new Date())
                .updateTime(new Date())
                .isDeleted(false)
                .build();

        userMapper.insert(userDO);
    }
}
