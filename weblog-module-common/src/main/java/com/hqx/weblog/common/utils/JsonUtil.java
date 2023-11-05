package com.hqx.weblog.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description JSON 工具类
 * @Create by hqx
 * @Date 2023/9/6 18:16
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper INSTANCE = new ObjectMapper();

    public static String toJsonString(Object obj) {
        try {
            return INSTANCE.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }


}
