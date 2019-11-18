package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Encryption.ShaEncryptor;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private IuserService userService;


    @PostMapping("/shApi/login")
    @ResponseBody
    public ShResultDTO<String,Object> userLogin(@RequestBody User user){
        if (user==null || user.getUserName()==null || user.getUserName().trim().length()==0 || user.getPassWord()==null || user.getPassWord().trim().length()==0){
            return new ShResultDTO<>(400,"登录失败，用户信息无效");
        }
        //shiro登录认证
        Subject subject = SecurityUtils.getSubject();
        //用户名和密码
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassWord(),false);
        try {
            //登录 登录失败将抛出异常
            subject.login(token);
            System.out.println(subject.getPrincipal()+"登录成功");
            return new ShResultDTO<>(200,"登录成功");
        }catch (AuthenticationException e){
            e.printStackTrace();
            return new ShResultDTO<>(403,"登录失败，用户名或密码不正确");
        }
    }

    @PostMapping("/shApi/signUp")
    @ResponseBody
    public ShResultDTO<String,Object> userSignUp(@RequestBody User user){
        userService.addUser(user);
        return new ShResultDTO<>(200,"注册完成");
    }

    //检查用户信息是否已存在、是否符合规范 userName 1,passWord 2,email 3
    @GetMapping("/shApi/checkUserInfo")
    @ResponseBody
    public ShResultDTO<String,Object> checkUserInfo(User user,Integer typeCode){
        if (user==null || typeCode ==null ){
            return new ShResultDTO<>(400,"客户端错误，缺少参数，此接口调用时需要接收user和type，请传入合法用户和合法的校验类型");
        }
        userService.checkOneOfUserInfo(user,typeCode);
        return new ShResultDTO<>(200,"用户信息符合规范");
    }

    //注销
    @GetMapping("/logout")
    @ResponseBody
    public ShResultDTO logout(HttpServletRequest request){
        request.getSession().invalidate();
        return new ShResultDTO(200,"用户注销");
    }

    //登录状态检查
    @GetMapping("/shApi/loginCheck")
    @ResponseBody
    public ShResultDTO loginChecker(HttpServletRequest request){
        if (request.getSession().getAttribute("user")!=null){
            return new ShResultDTO(200,"已登录");
        }else {
            return new ShResultDTO(4001,"未登录，请先登录再进行此操作");
        }
    }
}
