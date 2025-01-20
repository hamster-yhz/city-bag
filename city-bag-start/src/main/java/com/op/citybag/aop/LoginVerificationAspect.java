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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: 严豪哲
 * @Description: 登录验证拦截器
 * @Date: 2024/11/18 10:27
 * @Version: 1.0
 */

@Slf4j
@Component
@Aspect
@Order(Integer.MIN_VALUE)
public class LoginVerificationAspect {

    private final long EXPIRED = 0;

    private final String IS_DELETED = "1";


    @Autowired
    private RedissonService redissonService;

    /**
     * 拦截入口
     */
    @Pointcut("@annotation(com.op.citybag.demos.web.constraint.LoginVerification)")
    public void pointCut(){
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
        String token = request.getHeader("access_token");

        if (!checkAT(token)) {
            log.info("accessToken:{}被伪造,是无效token", token);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getMessage());
        }

        // 获取token的过期时间
        String key = RedisKey.TOKEN + token;
        Long accessTokenExpired = redissonService.getMapExpired(key);

        // token校验
        String isDeleted = redissonService.getFromMap(key, Common.TABLE_LOGIC);

        if(accessTokenExpired <= EXPIRED || isDeleted == IS_DELETED){
            log.info("accessToken已失效,accessToken:{}", token);
            throw new AppException(String.valueOf(GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getCode()), GlobalServiceStatusCode.LOGIN_ACCESS_TOKEN_INVALID.getMessage());
        }

        //执行目标接口
        return joinPoint.proceed();

    }

    private Boolean checkAT(String token) {

        //用tokenUtil检查token是否合法
        if(TokenUtil.checkAccessToken(token)){
            return true;
        }
        else {
            return false;
        }

    }

}
