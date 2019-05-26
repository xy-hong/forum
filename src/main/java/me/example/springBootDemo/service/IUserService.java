package me.example.springBootDemo.service;


import javax.mail.MessagingException;

public interface IUserService {

    String login(String id, String password);

    String register(String id, String password, String code);

    String forgetPassword(String id, String newPassword, String code);

    String changePassword(String id, String oldPassword, String newPassword);

    void logout();

    void getEmailVerifyCode(String toEmail) throws MessagingException;


}
