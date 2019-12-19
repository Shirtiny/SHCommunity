package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import cn.shirtiny.community.SHcommunity.Service.ImailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.ITemplateEngine;

import javax.mail.MessagingException;

@RestController
public class MailController {

    @Autowired
    private ImailService mailService;
    @Autowired
    private IjwtService jwtService;

    //发送确认邮件
    @PostMapping("/shApi/sendConfirmMail")
    public void sendConfirmMail(String userName,String mailBox){
        String jwtBase64Url = jwtService.userSignMailToJwtBase64Url(userName, mailBox);
        System.out.println(jwtBase64Url);
        try {
            mailService.sendMailConfirm(userName,mailBox,jwtBase64Url);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("邮件发送出错");
        }
    }
}
