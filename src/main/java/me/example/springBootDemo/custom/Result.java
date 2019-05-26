package me.example.springBootDemo.custom;

import lombok.Data;

@Data
public class Result {

    public final static int SUCESS = 1;
    public final static int FAIL = 0;

    private int code;
    private String message;
    private Object data;

    public Result(){}

    public Result(int code){
        this.code = code;
    }

    public Result(int code, String message){
        this.code = code;
        this.message = message;
    }

    public Result(int code, Object data){
        this.code = code;
        this.data = data;
    }

    public Result(int code, String message ,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static Result successResult(String message) { return new Result(Result.SUCESS, message); }

    public static Result failResult(String message){ return new Result(Result.FAIL, message); }

    public static Result successResult(Object data) { return new Result(Result.SUCESS, data); }

    public static Result failResult(Object data) { return new Result(Result.FAIL, data); }

}
