package me.example.springBootDemo.toolTest;

import me.example.springBootDemo.tool.MailTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MaillToolTest {
    @Autowired
    private MailTool mailTool;

    @Test
    public void TestSendMail(){
       // MailTool mail = new MailTool();
        mailTool.sendMail("test","测试能不能用","1015683970@qq.com");
        System.out.println("完成");
    }

    @Test
    public void TestSendHtmlMail(){
        String content = "<h1>Hello Mail</h1><hr/><p>这是内容</p>";
        try {
            mailTool.sendHtmlMail("testHtml",content,"1015683970@qq.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
