package com.lizhihai.miaosha.exception;

import com.lizhihai.miaosha.vo.entity.Result;
import com.lizhihai.miaosha.vo.param.CodeMsg;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return Result.error(globalException.getCm());
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> allErrors = bindException.getAllErrors();
            ObjectError error = allErrors.get(0);
            String message = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillAllArgs(message));
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
