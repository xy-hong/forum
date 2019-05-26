package me.example.springBootDemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import me.example.springBootDemo.custom.Tip;
import me.example.springBootDemo.mapper.UserInformationMapper;
import me.example.springBootDemo.entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UserInformationService {

    //限制上传文件大小,5MB
    private final long limitFileByte = 5*1024*1024;

    @Autowired
    private UserInformationMapper mapper;

    //修改用户头像
    public String changeUserImage(String id, MultipartFile file){
        //禁止为空
        if (file==null || file.isEmpty()){
            return Tip.UPLOAD_FAIL;
        }

        //禁止超过限制
        if ( file.getSize() > this.limitFileByte ){
            return Tip.UPLOAD_OVER_SIZE;
        }

        //禁止其他类型文件
        if ( !"image/jpg".equals(file.getContentType()) && !"image/png".equals(file.getContentType()) && !"image/jpeg".equals(file.getContentType())){
            return Tip.UPLOAD_NONSUPPORT_TYPE;
        }


       /* //FileOutputStream fileOutputStream = new FileOutputStream(new File());
        System.out.println("类型:"+file.getContentType());
        System.out.println("路径"+System.getProperty("user.dir"));

        try {
            System.out.println("相对路径"+ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename.replaceAll("[a-zA-Z0-9]+[.]",id+"_head\\.");
        //File file = new File();
        File file1 = new File("F://springImage/"+fileName);
        try {
            file.transferTo(file1);
            UserInformation userInformation = mapper.selectById(id);
            userInformation.setHead(fileName);
            mapper.update(userInformation,null);
        } catch (IOException e) {
            e.printStackTrace();
            return  Tip.UPLOAD_FAIL;
        }

        return Tip.UPLOAD_SUCCESS;

    }

    //修改用户昵称
    public String changeNick(String id, String newNick){
        UserInformation userInformation = mapper.selectById(id);
        userInformation.setUsername(newNick);
        UpdateWrapper wrapper = new UpdateWrapper();
        if ( mapper.update(userInformation,null) >0){
            return Tip.UPDATE_SUCCESS;
        }else{
            return Tip.UPDATE_FAIL;
        }

    }

}
