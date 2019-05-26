package me.example.springBootDemo.custom;

public enum ShiroExceptionEnum {

    IncorrectCredentialsException(1,"密码错误"),
    UnknownAccountException(2,"不存在该账号"),
    ExcessiveAttemptsException(3,"尝试密码失败多次，十分钟内禁止登录操作"),
    LockedAccountException(4,"黑名单账号，请联系管理员解锁"),
    UnauthorizedException(5,"没有权限");

    private ShiroExceptionEnum(int code, String  message){
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
