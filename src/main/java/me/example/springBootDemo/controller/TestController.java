package me.example.springBootDemo.controller;

import me.example.springBootDemo.custom.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @RequestMapping("/")
    public Result index(){
        Result result = new Result();
        result.setMessage("测试成功");
        return result;
    }

    @RequestMapping("/test")
    public Result test(String id){
        Result result = new Result();
        result.setMessage("测试成功");
        result.setData(id);
        return result;
    }

}
