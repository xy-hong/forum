package me.example.springBootDemo.exceptionHandler;

import me.example.springBootDemo.custom.ShiroExceptionEnum;
import me.example.springBootDemo.custom.Result;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class UserExceptionHandler {

    //密码错误
    @ExceptionHandler(IncorrectCredentialsException.class)
    public Result IncorrectCredentialsExceptionHandler(){
        Result result = new Result();
        result.setCode(Result.FAIL);
        result.setMessage(ShiroExceptionEnum.IncorrectCredentialsException.getMessage());
        return result;
    }

    //黑名单
    @ExceptionHandler(LockedAccountException.class)
    public Result lockedExceptionHandler(){
        Result result = new Result();
        result.setCode(Result.FAIL);
        result.setMessage(ShiroExceptionEnum.LockedAccountException.getMessage());
        return result;
    }

    //不存在账户
    @ExceptionHandler(UnknownAccountException.class)
    public Result unknownAccountExceptionHandler(){
        Result result = new Result();
        result.setCode(Result.FAIL);
        result.setMessage(ShiroExceptionEnum.UnknownAccountException.getMessage());
        return result;
    }

    //尝试次数过多
    @ExceptionHandler(ExcessiveAttemptsException.class)
    public Result excessiveAttemptsExceptionHandler(){
        Result result = new Result();
        result.setCode(Result.FAIL);
        result.setMessage(ShiroExceptionEnum.ExcessiveAttemptsException.getMessage());
        return result;
    }

    //没有对应权限
    @ExceptionHandler(UnauthorizedException.class)
    public Result UnauthorizedExceptionHandler(){
        Result result = new Result();
        result.setCode(Result.FAIL);
        result.setMessage(ShiroExceptionEnum.UnauthorizedException.getMessage());
        return result;
    }







}
