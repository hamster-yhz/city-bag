package com.op.citybag.demos.web.exception;

import com.op.citybag.demos.model.common.GlobalServiceStatusCode;
import com.op.citybag.demos.web.common.OPResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chensongmin
 * @description 全局异常处理器
 * @date 2024/11/11
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * 自定义验证异常 MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OPResult handleValidationException(MethodArgumentNotValidException ex) {
        log.error("参数校验异常", ex);
        String message = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n"));

        return OPResult.RESPONSE(String.valueOf(GlobalServiceStatusCode.PARAM_FAILED_VALIDATE), message);

    }

    /**
     * 自定义验证异常 ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OPResult constraintViolationException(ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response) {
        log.error("参数校验异常", e);
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n"));

        return OPResult.RESPONSE(String.valueOf(GlobalServiceStatusCode.PARAM_FAILED_VALIDATE), message);
    }

    /**
     * 自定义验证异常 BindException
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OPResult handleBindException(BindException ex) {
        log.error("参数绑定异常", ex);
        String message = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n"));

        return OPResult.RESPONSE(String.valueOf(GlobalServiceStatusCode.PARAM_FAILED_VALIDATE), message);

    }

    /**
     * 自定义验证异常 BindException
     */
    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OPResult handleAPPException(AppException e) {
        log.error("系统异常", e);
        return OPResult.RESPONSE(e.getCode(), e.getInfo());
    }


}
