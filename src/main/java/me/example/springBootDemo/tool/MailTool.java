package me.example.springBootDemo.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import  org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailTool {

    @Autowired
    JavaMailSender mailSender; //框架自带的

    @Value("${spring.mail.username}")  //发送人的邮箱  比如155156641XX@163.com
    private String from;

    //@Async  //意思是异步调用这个方法
    public void sendMail(String title, String content, String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); // 发送人的邮箱
        message.setSubject(title); //标题
        message.setTo(toEmail); //发给谁  对方邮箱
        message.setText(content); //内容
        mailSender.send(message); //发送
    }

    public void sendHtmlMail(String title, String content, String toEmail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setFrom(from);
        helper.setSubject(title);
        helper.setTo(toEmail);
        helper.setText(content,true);
        mailSender.send(message);

    }
}
