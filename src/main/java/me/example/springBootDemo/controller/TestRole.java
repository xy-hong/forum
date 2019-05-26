package me.example.springBootDemo.controller;

import me.example.springBootDemo.custom.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRole {


    @PostMapping("/testUser")
    @RequiresRoles("USER")
    public Result user(){

        Result result = new Result();
        result.setMessage("用户权限");
        return result;
    }

    @PostMapping("/testAdmin")
    @RequiresRoles("ADMIN")
    public Result amdin(){

        Result result = new Result();
        result.setMessage("管理员权限");
        return result;
    }

    @PostMapping("/testBlackList")
    @RequiresRoles("BLACKLIST")
    public Result black(){

        Result result = new Result();
        result.setMessage("黑名单权限");
        return result;
    }


}
