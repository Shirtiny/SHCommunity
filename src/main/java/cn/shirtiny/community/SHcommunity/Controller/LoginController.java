package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.JWT.JwtRsaHelper;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IcookieService;
import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import cn.shirtiny.community.SHcommunity.Service.IloginService;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private IuserService userService;
    @Autowired
    private IloginService loginService;
    @Autowired
    private IjwtService jwtService;
    @Autowired
    private IcookieService cookieService;


    @PostMapping("/shApi/login")
    @ResponseBody
    public ShResultDTO<String,Object> userLogin(@RequestBody User user, HttpServletResponse response){
        ShResultDTO<String, Object> parseResult = loginService.userLoginByPWD(user);
        String jwt = (String)parseResult.getData().get("jwt");
        cookieService.addOneCookie(response,"shJwt",jwt,"/",-1);
        return parseResult;
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
        Map<String, Object> claims = jwtService.parseJwtByRequest(request);
        if (claims!=null){
            return new ShResultDTO(200,"已登录");
        }else {
            return new ShResultDTO(4001,"未登录，请先登录再进行此操作");
        }
    }
}
