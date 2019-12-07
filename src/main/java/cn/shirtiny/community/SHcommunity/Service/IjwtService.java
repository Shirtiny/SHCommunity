package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Model.User;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IjwtService {
    //从request请求头中解析token
    Map<String,Object> parseJwtByRequest(HttpServletRequest request);
    //从cookie中解析token
    Map<String,Object> parseJwtByCookie(HttpServletRequest request);
    //解析传入的token字符串
    Map<String,Object> parseJwt(String jwt);
    //把token字符串解析后返回一个userDto对象
    UserDTO parseJwtToUser(String jwt);
}
