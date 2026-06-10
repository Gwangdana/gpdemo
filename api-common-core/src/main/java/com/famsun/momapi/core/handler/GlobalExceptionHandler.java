package com.famsun.momapi.core.handler;

import com.famsun.momapi.core.constent.ResultCode;
import com.famsun.momapi.core.exception.GException;
import com.famsun.momapi.core.vo.Result;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获自定义业务异常
     */
    @ExceptionHandler(GException.class)
    public Result handleBusinessException(GException e) {
        log.error("业务异常：{}", e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 捕获请求参数校验异常（@Valid/@Validated）
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result handleValidException(Exception e) {
        String msg;
        if (e instanceof MethodArgumentNotValidException validException) {
            // 获取第一个校验失败的信息
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
     * 捕获PathVariable/RequestParam参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        String msg = e.getConstraintViolations().iterator().next().getMessage();
        log.error("参数校验异常：{}", msg, e);
        return Result.fail(ResultCode.BAD_REQUEST.getCode(), msg);
    }

    /**
     * 捕获404异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("404异常：{}", e.getMessage(), e);
        return Result.fail(ResultCode.NOT_FOUND.getCode(), e.getMessage());
    }

    /**
     * 捕获所有未定义的异常（兜底处理）
     */
    @ExceptionHandler(Exception.class)
    public Result handleGlobalException(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return Result.fail(ResultCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }
}
