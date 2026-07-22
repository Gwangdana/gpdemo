package com.dana.common.core.web;

import com.dana.common.core.exception.GException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获自定义业务异常
     */
    @ExceptionHandler(GException.class)
    public Result<Void> handleBusinessException(GException e) {
        log.error("业务异常：{}", e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 捕获请求参数校验异常（@Valid / @Validated）
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result<Void> handleValidException(Exception e) {
        String msg;
        if (e instanceof MethodArgumentNotValidException validException) {
            msg = validException.getBindingResult().getFieldError() != null
                    ? validException.getBindingResult().getFieldError().getDefaultMessage()
                    : "请求参数校验失败";
        } else if (e instanceof BindException bindException) {
            msg = bindException.getBindingResult().getFieldError() != null
                    ? bindException.getBindingResult().getFieldError().getDefaultMessage()
                    : "请求参数绑定失败";
        } else {
            msg = "请求参数格式错误";
        }
        log.error("参数校验异常：{}", msg, e);
        return Result.fail(ResultCode.BAD_REQUEST.getCode(), msg);
    }

    /**
     * 捕获 PathVariable / RequestParam 参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolationException(ConstraintViolationException e) {
        String msg = e.getConstraintViolations().iterator().next().getMessage();
        log.error("参数校验异常：{}", msg, e);
        return Result.fail(ResultCode.BAD_REQUEST.getCode(), msg);
    }

    /**
     * 捕获 404 异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<Void> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("404异常：{}", e.getMessage(), e);
        return Result.fail(ResultCode.NOT_FOUND.getCode(), e.getMessage());
    }

    /**
     * 捕获所有未定义的异常（兜底处理）
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR);
    }
}
