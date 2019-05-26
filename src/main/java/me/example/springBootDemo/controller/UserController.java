package me.example.springBootDemo.controller;

import me.example.springBootDemo.custom.Result;
import me.example.springBootDemo.custom.Tip;
import me.example.springBootDemo.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class UserController {

    @Autowired
    private IUserService service;


    @PostMapping("/login")
    public Result login(String id, String password) throws AuthenticationException {
        if ( Tip.LOGIN_SUCCESS.equals(service.login(id, password)) ){
            return Result.successResult(Tip.LOGIN_SUCCESS);
        }else {
            return Result.failResult(Tip.LOGIN_FAIL);
        }
    }

    @PostMapping("/register")
    public Result register(String id, String password, String code){
        String reusltMessage = service.register(id, password, code);
        switch (reusltMessage){
            case Tip.DUPLICATE_USER: return Result.failResult(reusltMessage);
            case Tip.ERROR_VERIFY_CODE: return  Result.failResult(reusltMessage);
            case Tip.REGISTER_FAIL: return  Result.failResult(reusltMessage);
            case Tip.REGISTER_SUCCESS: return  Result.successResult(reusltMessage);
            default: return  Result.successResult(reusltMessage);
        }


    }

    @PostMapping("/changePassword")
    public Result changePassword(String id, String oldPassword, String newPassword){
       String resultMessage = service.changePassword(id, oldPassword, newPassword);
       if (Tip.CHANGE_PASSWORD_SUCCESS.equals(resultMessage)){
           return Result.successResult(resultMessage);
       }else {
           return Result.failResult(resultMessage);
       }
    }

    public Result forgetPassword(String id, String newPassword, String code){
        String message = service.forgetPassword(id, newPassword, code);
        if (message.equals(Tip.CHANGE_PASSWORD_SUCCESS)){
            return Result.successResult(message);
        }else {
            return Result.failResult(message);
        }
    }

    @GetMapping("/logout")
    public Result logout(){
        service.logout();
        return new Result();
    }

    @GetMapping("/Email")
    public Result getEmailVerifyCode(String toEmail) throws MessagingException {
        service.getEmailVerifyCode(toEmail);
        return Result.successResult(Tip.VERIFY_CODE_SUCCESS);
    }

}
