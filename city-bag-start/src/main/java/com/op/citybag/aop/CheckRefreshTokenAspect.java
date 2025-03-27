package com.op.citybag.aop;

import com.op.citybag.demos.model.common.Common;
import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import com.op.citybag.demos.model.common.RedisKey;
import com.op.citybag.demos.redis.RedissonService;
import com.op.citybag.demos.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.client.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author: 严豪哲
 * @Description:
 * @Date: 2024/11/17 15:26
 * @Version: 1.0
 */

@Component
@Slf4j
@Aspect
public class CheckRefreshTokenAspect {


    private final long EXPIRED = 0;

    private final String IS_DELETED = "1";

    private final String REFRESH_TOKEN = "refresh_token";

    @Autowired
    private RedissonService redissonService;

    /**
     * 拦截入口
     */
    @Pointcut("@annotation(com.op.citybag.demos.constraint.CheckRefreshToken)")
    public void pointCut() {
    }

    /**
     * 拦截处理
     * @param joinPoint joinPoint 信息
     * @return result
     * @throws Throwable if any
     */
    @Around("pointCut()")
    public Object checkToken(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(REFRESH_TOKEN);

        try {
            // 获取token的过期时间
            String key = RedisKey.REFRESH_TOKEN + token;
            Long RefreshTokenExpired = redissonService.getMapExpired(key);

            // token校验
            String isDeleted = redissonService.getFromMap(key, Common.TABLE_LOGIC);

            if (RefreshTokenExpired <= EXPIRED || isDeleted == IS_DELETED) {
                log.info("refreshToken已失效,refreshToken:{}", token);
                throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getMessage());
            }
        } catch (RedisException e) {
            //redis宕机兜底
            if (!checkRT(token)) {
                log.info("refreshToken已失效,refreshToken:{}", token);
                throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_REFRESH_TOKEN_INVALID.getMessage());
            }
        }

        //执行目标接口
        return joinPoint.proceed();
    }

    private Boolean checkRT(String token) {
        return true;
    }
}
