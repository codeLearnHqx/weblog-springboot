package com.hqx.weblog.common.aspect;

import com.hqx.weblog.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description
 * @Create by hqx
 * @Date 2023/9/6 18:19
 */
@Slf4j
@Aspect
@Component
public class ApiOperationLogAspect {

    /* 以自定义 @ApiOperationLog 注解为切点，凡是添加 @ApiOperationLog 的方法，都会执行环绕中的代码 */
    @Pointcut("@annotation(com.hqx.weblog.common.aspect.ApiOperationLog)")
    public void apiOperationLog(){}


    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
      try{
          // 请求开始时间
          long startTime = System.currentTimeMillis();

          // MDC 是一种在日志中记录额外信息的机制，通常用于跟踪日志事件。MDC 可以存储键值对，其中键是字符串，值可以是任何对象。
          // traceId 表示跟踪 ID， 值这里直接用的 UUID
          MDC.put("traceId", UUID.randomUUID().toString());

          // 获取被请求的类和方法
          String className = joinPoint.getTarget().getClass().getSimpleName();
          String methodName = joinPoint.getSignature().getName();

          // 请求入参
          Object[] args = joinPoint.getArgs();
          //  入参转json字符串
          String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(","));
          // 功能描述信息
          String description = getApiOperationLogDescription(joinPoint);

          // 打印请求相关参数
          log.info("====== 请求开始：[{}], 入参：{} , 请求类：{}，请求方法：{} ============================",
                  description, argsJsonStr, className, methodName);
          // 执行切点方法
          Object result = joinPoint.proceed();

          // 执行耗时
          long executionTime = System.currentTimeMillis() - startTime;

          // 打印出参等相关信息
          log.info("===== 请求结束：[{}]，耗时：{}ms，出参：{} ==========================================",
                  description,executionTime, JsonUtil.toJsonString(result));

          return result;
      } finally {
          /*
            这行代码用于清除MDC中存储的所有键值对。在某些情况下，当一个请求或操作的日志记录完成后，你可能希望清除MDC，
            以便在下一个请求或操作中不会受到之前的上下文数据的影响。清除MDC有助于确保日志上下文的干净和准确。
           */
          MDC.clear();
      }


    }



    /**
     * 获取注解的描述信息
     * @param joinPoint
     * @return
     */
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint) {
        // 1. 从 ProceedingJoinPoint 获取 MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 2. 使用 MethodSignature 获取当前被注解的 Method
        Method method = signature.getMethod();

        // 3. 从 Method 中提取 LogExecution 注解
        ApiOperationLog apiOperationLog = method.getAnnotation(ApiOperationLog.class);

        // 4. 从 LogExecution 注解中获取 description 属性
        return apiOperationLog.description();
    }

    /**
     * 转 JSON 字符串
     * @return
     */
    private Function<Object, String> toJsonStr() {
        return JsonUtil::toJsonString;
    }


}
