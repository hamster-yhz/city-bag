package com.op.citybag.aop;

import com.op.citybag.demos.model.GlobalServiceStatusCode;
import com.op.citybag.demos.model.RedisKey;
import com.op.citybag.demos.model.common.Common;
import com.op.citybag.demos.redis.RedissonService;
import com.op.citybag.demos.utils.TokenUtil;
import com.op.citybag.demos.web.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 严豪哲
 * @Description: 访问个人私有资源权限拦截器
 * @Date: 2024/11/27 21:40
 * @Version: 1.0
 */

@Slf4j
@Component
@Aspect
@Order(Integer.MIN_VALUE + 1)
public class SelfPermissionVerificationAspect {

    @Autowired
    private RedissonService redissonService;

    private final long EXPIRED = 0;

    private final String IS_DELETED = "1";

    /**
     * 拦截入口
     */
    @Pointcut("@annotation(com.op.citybag.demos.web.constraint.LoginVerification)")
    public void pointCut() {
    }

    /**
     * 拦截处理
     *
     * @param joinPoint joinPoint 信息
     * @return result
     * @throws Throwable if any
     */
    @Around("pointCut()")
    public Object checkToken(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("access_token");

        if (!checkAT(token)) {
            log.info("accessToken:{}被伪造,是无效token", token);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getMessage());
        }

        // 获取token的过期时间
        String key = RedisKey.TOKEN + token;
        Long accessTokenExpired = redissonService.getMapExpired(key);

        // 获取用户信息
        String tokenUserId = redissonService.getFromMap(key, Common.USER_ID);

        // 获取用户ID
        Object arg = joinPoint.getArgs()[0];
        if(arg == null){
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_UNKNOWN_ERROR.getCode()), GlobalServiceStatusCode.LOGIN_UNKNOWN_ERROR.getMessage());
        }
        String targetUserId = (String) arg.getClass().getMethod("getUserId").invoke(arg);

        // token校验
        String isDeleted = redissonService.getFromMap(key, Common.TABLE_LOGIC);

        if (accessTokenExpired <= EXPIRED || isDeleted == IS_DELETED) {
            log.info("accessToken已失效,accessToken:{}", token);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getMessage());
        }

        // 校验用户ID是否相同
        if (tokenUserId.equals(targetUserId)) {
            log.info("当前用户访问的是个人私有资源,用户id相同,可以放行,userId:{}", tokenUserId);
            return joinPoint.proceed();
        } else {
            log.info("当前用户访问的是个人私有资源,用户id不相同,不可以放行,userId:{}", tokenUserId);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.USER_NO_PERMISSION.getCode()), GlobalServiceStatusCode.USER_NO_PERMISSION.getMessage());
        }

    }

    private Boolean checkAT(String token) {
        //用tokenUtil检查token是否合法
        if (TokenUtil.checkAccessToken(token)) {
            return true;
        } else {
            return false;
        }
    }
}