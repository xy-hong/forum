package me.example.springBootDemo.controller;

import me.example.springBootDemo.custom.Result;
import me.example.springBootDemo.custom.Role;
import me.example.springBootDemo.custom.Tip;
import me.example.springBootDemo.entity.User;
import me.example.springBootDemo.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.lang.reflect.Field;

@RestController
public class UserController {

    @Autowired
    private IUserService service;


    @PostMapping("/login")
    public Result login(String id, String password) throws AuthenticationException {
        if ( Tip.LOGIN_SUCCESS.equals(service.login(id, password)) ){
            //将用户角色作为data返回，供前端渲染不同页面
            Subject subject = SecurityUtils.getSubject();
            String role = null;
            for(String r: Role.roles){
                if (subject.hasRole(r)){
                    role = r;
                    break;
                }
            }

            return new Result(1,Tip.LOGIN_SUCCESS, role);
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
        return Result.successResult(Tip.LOGOUT_SUCCESS);
    }

    @PostMapping("/Email")
    public Result getEmailVerifyCode(String toEmail) throws MessagingException {
        service.getEmailVerifyCode(toEmail);
        return Result.successResult(Tip.VERIFY_CODE_SUCCESS);
    }


}
