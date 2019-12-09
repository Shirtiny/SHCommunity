package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Exception.JwtInvalidException;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IcookieService;
import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import cn.shirtiny.community.SHcommunity.Service.IloginService;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    @Value("${ShJwt_Cookie_name}")
    private String shJwtCookieName;


    //用户账号密码登录
    @PostMapping("/shApi/login")
    @ResponseBody
    public ShResultDTO<String, Object> userLogin(@RequestBody User user, HttpServletResponse response) {

        return loginService.userLoginByPWD(user,response);
    }

    //用户注册
    @PostMapping("/shApi/signUp")
    @ResponseBody
    public ShResultDTO<String, Object> userSignUp(@RequestBody User user) {
        userService.addUser(user);
        return new ShResultDTO<>(200, "注册完成");
    }

    //检查用户信息是否已存在、是否符合规范 userName 1,passWord 2,email 3
    @GetMapping("/shApi/checkUserInfo")
    @ResponseBody
    public ShResultDTO<String, Object> checkUserInfo(User user, Integer typeCode) {
        if (user == null || typeCode == null) {
            return new ShResultDTO<>(400, "客户端错误，缺少参数，此接口调用时需要接收user和type，请传入合法用户和合法的校验类型");
        }
        userService.checkOneOfUserInfo(user, typeCode);
        return new ShResultDTO<>(200, "用户信息符合规范");
    }

    //注销 删除jwt maxAge=0表示删除
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        cookieService.deleteShJwtFromCookie(response);
        return "redirect:/";
    }

    //api方式注销 删除jwt maxAge=0表示删除
    @GetMapping("/shApi/logout")
    @ResponseBody
    public ShResultDTO logoutByApi(HttpServletResponse response) {
        cookieService.deleteShJwtFromCookie(response);
        return new ShResultDTO(200, "用户注销");
    }

    //登录状态检查 检查请求头中的jwt
    @GetMapping("/shApi/loginCheck")
    @ResponseBody
    public ShResultDTO loginChecker(HttpServletRequest request) {
        Map<String, Object> claims = jwtService.parseJwtByRequest(request);
        if (claims != null) {
            return new ShResultDTO(200, "已登录");
        } else {
            return new ShResultDTO(4001, "未登录，请先登录再进行此操作");
        }
    }

    //登录状态检查 检查cookie中的jwt
    @GetMapping("/shApi/loginCheckByCookie")
    @ResponseBody
    public ShResultDTO loginCheckByCookie(HttpServletRequest request) {
        Map<String, Object> claims = jwtService.parseJwtByCookie(request);
        if (claims != null) {
            return new ShResultDTO(200, "已登录");
        } else {
            return new ShResultDTO(4001, "未登录，请先登录再进行此操作");
        }
    }

    //解析header中的jwt，返回用户信息
    @GetMapping("/shApi/getLoginedUser")
    @ResponseBody
    public ShResultDTO<String, Object> backLoginedUserInfo(HttpServletRequest request) {
        Map<String, Object> claims = jwtService.parseJwtByRequest(request);
        Map<String, Object> data = new HashMap<>();
        if (claims != null) {
            data.put("user", claims.get("user"));
            return new ShResultDTO<>(200, "已返回jwt用户信息", data, null);
        } else {
            throw new JwtInvalidException("Jwt格式无效或解析失败");
        }
    }

    //解析cookie中的jwt，返回用户信息
    @GetMapping("/shApi/getLoginedUserByCookie")
    @ResponseBody
    public ShResultDTO<String, Object> backLoginedUserInfoByCookie(HttpServletRequest request) {
        String jwt = cookieService.getShJwtFromCookie(request.getCookies());
        UserDTO userDTO = jwtService.parseJwtToUser(jwt);
        Map<String, Object> data = new HashMap<>();
        if (userDTO != null) {
            data.put("user", userDTO);
            return new ShResultDTO<>(200, "已返回jwt用户信息", data, null);
        } else {
            throw new JwtInvalidException("Jwt格式无效或解析失败");
        }
    }

}
