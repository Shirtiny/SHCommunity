package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.Service.ImailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.ITemplateEngine;

import javax.mail.MessagingException;

@RestController
public class MailController {

    @Autowired
    private ImailService mailService;

    @GetMapping("/shApi/sendMail")
    public void sendMail(){
//        mailService.sendSimpleMail();
        try {
//            mailService.sendComplexMail();
            mailService.sendTemplateHtml();
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("from不对");
        }
    }
}
