package me.example.springBootDemo.service.impl;

import me.example.springBootDemo.custom.RedisKeyPrefix;
import me.example.springBootDemo.custom.Role;
import me.example.springBootDemo.custom.Tip;
import me.example.springBootDemo.entity.User;
import me.example.springBootDemo.entity.UserInformation;
import me.example.springBootDemo.mapper.UserInformationMapper;
import me.example.springBootDemo.mapper.UserMapper;
import me.example.springBootDemo.service.IUserService;

import me.example.springBootDemo.tool.EncryptTool;
import me.example.springBootDemo.tool.MailTool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;

import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInformationMapper userInformationMapper;

    @Autowired
    private MailTool mailTool;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EncryptTool encryptTool;

   @Override
   public String login(String id, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(id, password);
        token.setRememberMe(true);
        subject.login(token);
        String key = RedisKeyPrefix.LOGIN_LIMIT+":"+id;
        redisTemplate.opsForValue().set(key,0);
        return Tip.LOGIN_SUCCESS;
    }

    @Override
    public String register(String id, String password, String code){
        User user = userMapper.selectById(id);
        //是否存在该用户
        if (user != null) {
            return Tip.DUPLICATE_USER;
        }
        // 如果验证码不匹配返回错误信息
        String key = RedisKeyPrefix.VERIFY_CODE+":"+id;
        String realCode = String.valueOf(redisTemplate.opsForValue().get(key));
        if(realCode == null || !realCode.equals(code)){
            return Tip.ERROR_VERIFY_CODE;
        }

        String salt = new SecureRandomNumberGenerator().nextBytes(12).toHex();
        SimpleHash hash = new SimpleHash("md5", password, salt, 2);

        user = new User();
        user.setUserId(id);
        user.setPassword(hash.toHex());
        user.setSalt(salt);
        user.setRole(Role.USER);

        UserInformation information = new UserInformation();
        information.setUserId(id);
        information.setRegisterDate(LocalDate.now());



        if (userMapper.insert(user)>0 && userInformationMapper.insert(information)>0){
            return Tip.REGISTER_SUCCESS;
        }else{
            return Tip.REGISTER_FAIL;
        }
    }

    @Override
    public String forgetPassword(String id, String newPassword, String code) {
       String key = RedisKeyPrefix.VERIFY_CODE+":"+id;
       String realCode = String.valueOf(redisTemplate.opsForValue().get(key));

       if (code.equals(realCode)){
           User user = userMapper.selectById(id);
           String newHashPass = encryptTool.encrypt(newPassword,user.getSalt());
           user.setPassword(newHashPass);
           userMapper.updateById(user);
           return Tip.CHANGE_PASSWORD_SUCCESS;
       }else{
           return Tip.ERROR_VERIFY_CODE;
       }

    }

    @Override
    public String changePassword(String id, String oldPassword, String newPassword) {
       User user = userMapper.selectById(id);
       SimpleHash simpleHash = new SimpleHash("md5",oldPassword, ByteSource.Util.bytes(user.getSalt()),2);
       if (user.getPassword().equals(simpleHash.toHex())){
           String salt = new SecureRandomNumberGenerator().nextBytes(12).toHex();
          // SimpleHash newSimpleHash = new SimpleHash("md5",newPassword, salt,2);
           String newHash =  encryptTool.encrypt(newPassword,salt);
           user.setSalt(salt);
           user.setPassword(newHash);
           userMapper.updateById(user);
           return Tip.CHANGE_PASSWORD_SUCCESS;
       }else {
           return  Tip.OLD_PASSWORD_ERROR;
       }
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    public void getEmailVerifyCode(String toEmail) throws MessagingException{
       //生产六位数
       int code = (int)((Math.random()*9+1)*100000);
       //发送邮件
       String content = "<p>验证码<B>"+code+"</B>,十分钟内有效</p>";
       mailTool.sendHtmlMail("验证码", content, toEmail);

       //redis保存验证码十分钟
       String key = RedisKeyPrefix.VERIFY_CODE+":"+toEmail;
       redisTemplate.opsForValue().set(key, code, 10, TimeUnit.MINUTES);

    }
}
