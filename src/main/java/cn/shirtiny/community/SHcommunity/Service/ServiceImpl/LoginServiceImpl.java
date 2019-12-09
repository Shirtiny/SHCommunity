package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Exception.LoginFailedException;
import cn.shirtiny.community.SHcommunity.Exception.UserInfoNotAllowException;
import cn.shirtiny.community.SHcommunity.Service.IcookieService;
import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import cn.shirtiny.community.SHcommunity.Utils.JWT.JwtRsaHelper;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IloginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements IloginService {

    @Autowired
    private IjwtService jwtService;
    @Autowired
    private IcookieService cookieService;
    @Value("${ShJwt_Cookie_maxAge}")
    private Integer ShJwtCookieMaxAge;

    @Override
    public String getRedirectFromCookie(HttpServletRequest request, HttpServletResponse response) {
        //拿cookie中的回调地址
        Cookie[] cookies = request.getCookies();
        //遍历cookies，拿到回调值后返回
        for (Cookie cookie:cookies){
            if ("shRedirectCookie".equals(cookie.getName())){
                //跳转到回调地址
                return cookie.getValue();
            }
        }
        //没找到回调地址就返回主页
        return "/";
    }

    //通过用户密码登录
    @Override
    public ShResultDTO<String, Object> userLoginByPWD(User user,HttpServletResponse response) {
        if (user==null || user.getUserName()==null || user.getUserName().trim().length()==0 || user.getPassWord()==null || user.getPassWord().trim().length()==0){
            throw  new UserInfoNotAllowException("待登录用户信息不正常");
        }
        //shiro登录认证
        Subject subject = SecurityUtils.getSubject();
        //用户名和密码
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassWord(),false);
        try {
            //登录 登录失败将抛出异常
            subject.login(token);
            //获得登录后的用户实体
            user = (User)subject.getPrincipal();
            //为用户颁发jwt 登录并设置cookie
            String jwt = userLogin(user, response);
            //把jwt封装 并返回
            Map<String,Object> data = new HashMap<>();
            data.put("jwt",jwt);
            return new ShResultDTO<>(200,"登录成功",data,null);
        }catch (AuthenticationException e){
            e.printStackTrace();
            throw new LoginFailedException("登录失败，用户名或密码不正确",e);
        }
    }

    //将用户封装为jwt并设置cookie 把jwt返回出来
    @Override
    public String userLogin(User user,HttpServletResponse response) {
        String jwt = jwtService.userToJwt(user);
        cookieService.addJwtCookie(response,jwt,ShJwtCookieMaxAge);
        return jwt;
    }
}
