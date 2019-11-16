package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.Encryption.ShaEncryptor;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    private IuserService userService;
    @Autowired
    private ShaEncryptor shaEncryptor;

    @PostMapping("/shApi/login")
    @ResponseBody
    public String userLogin(String userName,String passWord){
        //shiro登录认证
        Subject subject = SecurityUtils.getSubject();
        //用户名和密码
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord,false);
        try {
            //登录 登录失败将抛出异常
            subject.login(token);
            System.out.println(userName+"登录成功");
            return "登录成功";
        }catch (AuthenticationException e){
            e.printStackTrace();
            return "登录失败";
        }
    }

    @PostMapping("/shApi/signUp")
    @ResponseBody
    public String userSignUp(User user){
        String encryptedPWD = shaEncryptor.encrypt(user.getPassWord());
        user.setPassWord(encryptedPWD);
        System.out.println(encryptedPWD);
        userService.addUser(user);
        return "注册完成";
    }

}
