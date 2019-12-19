package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import cn.shirtiny.community.SHcommunity.Service.ImailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class MailServiceImpl implements ImailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ITemplateEngine templateEngine;

    @Value("${SH_Mail_From}")
    private String shMailFrom;
    @Value("${SH_Domain_Name}")
    private String shDomainName;
    @Value("${SH_Mail_Confirm_Template}")
    private String mailConfirmTemplate;

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
    public void sendComplexMail(String to, String subject, String mailContent) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(shMailFrom);
        helper.setTo(to);
        helper.setSubject(subject);
        //支持html 设置html为true即可
        helper.setText(mailContent, true);
        //嵌入静态资源
//        helper.addInline("thisImageO",new FileSystemResource(new File("C:\\Users\\Administrator\\Downloads\\11.png")));
        //传递附件
//        helper.addAttachment("您的附件.png",new File("C:\\Users\\Administrator\\Downloads\\11.png"));
        javaMailSender.send(mimeMessage);
    }


    //与thymeleaf模版引擎配合，发送模版化的html
    @Override
    public void sendTemplateHtml() throws Exception {
        Context context = new Context();
        context.setVariable("url", "shJwtUrl");
        context.setVariable("domain", "community.shirtiny.cn");
        String mailContent = templateEngine.process("mail", context);
        String to = "shirtiny@gmail.com";
        String subject = "SH社区邮箱确认";
        sendComplexMail(to, subject, mailContent);
    }

    //给指定邮箱发送 邮箱确认 jwt应包含用户的账户名、id
    @Override
    public void sendMailConfirm(String userName,String to, String jwtBase64Url) throws Exception {
        Context context = new Context();
        String url = shDomainName +"/mailConfirm/"+ jwtBase64Url;
        context.setVariable("userName",userName);
        context.setVariable("url", url);
        context.setVariable("domain", shDomainName);
        String mailContent = templateEngine.process(mailConfirmTemplate, context);
        String subject = "SH社区邮箱确认";
        sendComplexMail(to, subject, mailContent);
    }
}
