package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Service.ImailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Service
public class MailServiceImpl implements ImailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${SH_Mail_From}")
    private String shMailFrom;

    //简单邮件 标题 内容
    @Override
    public void sendSimpleMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(shMailFrom);
        simpleMailMessage.setTo("sssss111@xxx.com");
        simpleMailMessage.setSubject("主题");
        simpleMailMessage.setText("内容");
        javaMailSender.send(simpleMailMessage);
    }


    //复杂邮件 支持html,抄送,密送等 可以携带附件 文内可以嵌入静态资源
    @Override
    public void sendComplexMail(String subject,String mailContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(shMailFrom);
        helper.setTo("shirtiny@gmail.com");
        helper.setSubject(subject);
        //支持html 设置html为true即可
        helper.setText(mailContent,true);
        //嵌入静态资源
//        helper.addInline("thisImageO",new FileSystemResource(new File("C:\\Users\\Administrator\\Downloads\\11.png")));
        //传递附件
//        helper.addAttachment("您的附件.png",new File("C:\\Users\\Administrator\\Downloads\\11.png"));
        javaMailSender.send(mimeMessage);
    }

    @Autowired
    private ITemplateEngine templateEngine;

    //与thymeleaf模版引擎配合，发送模版化的html
    @Override
    public void sendTemplateHtml() throws MessagingException {
        Context context = new Context();
        context.setVariable("jwt","shJwt");
        context.setVariable("domain","community.shirtiny.cn");
        String mailContent = templateEngine.process("mail", context);
        sendComplexMail("账号激活",mailContent);
    }
}
