package me.example.springBootDemo.controller;

import me.example.springBootDemo.custom.Result;
import me.example.springBootDemo.custom.Tip;
import me.example.springBootDemo.service.impl.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserInformationController {

    @Autowired
    private UserInformationService service;

    //todo
    @PostMapping("/image")
    public Result changeHead(String id, MultipartFile file){

        return new Result(3, service.changeUserImage(id,file));
    }

    @PutMapping("/nick")
    public Result changeNick(String id, String newNick){
        String resultMessage = service.changeNick(id, newNick);
        if (Tip.UPDATE_SUCCESS.equals(resultMessage)){
            return Result.successResult(resultMessage);
        }else{
            return Result.failResult(resultMessage);
        }

    }

}
