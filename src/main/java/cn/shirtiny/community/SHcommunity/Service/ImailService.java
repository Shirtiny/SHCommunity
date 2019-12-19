package cn.shirtiny.community.SHcommunity.Service;

import javax.mail.MessagingException;

public interface ImailService {
    //https://www.jianshu.com/p/5eb000544dd7

    //简单邮件 标题 内容
    void sendSimpleMail();

    //复杂邮件 支持html,抄送,密送等 可以携带附件 文内可以嵌入静态资源
    void sendComplexMail(String to,String subject,String mailContent) throws Exception;

    //与thymeleaf模版引擎配合，发送模版化的html
    void sendTemplateHtml()throws Exception;

    //给指定邮箱发送 邮箱确认 jwt应包含用户的账户名、id
    void sendMailConfirm(String userName,String mailbox,String jwtBase64Url)throws Exception;

}
