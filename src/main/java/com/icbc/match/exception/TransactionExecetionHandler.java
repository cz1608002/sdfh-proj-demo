package com.icbc.match.exception;


import com.icbc.match.entry.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 通过异常拦截器
 */
@ControllerAdvice
@ResponseBody
public class TransactionExecetionHandler {

    private static final Logger logger = LoggerFactory.getLogger(TransactionExecetionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        if (e instanceof TransactionException) {
            TransactionException exception = (TransactionException) e;
            return Result.failed(exception.getErrorCode(), exception.getErrorMsg());
        }

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            try {
                String errorMsg = exception.getBindingResult().getFieldError().getDefaultMessage();
                return Result.validateFailed(errorMsg);
            } catch (Exception ex) {
                return Result.failed();
            }
        }

        logger.error("unexpected exption: ", e);

        return Result.failed();
    }


}
