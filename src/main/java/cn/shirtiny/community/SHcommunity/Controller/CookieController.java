package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "/cookieController")
public class CookieController {

    @RequestMapping(value = "/addCookie")
    @ResponseBody
    public ShResultDTO addOneCookie(@RequestBody Map<String,String> map,  HttpServletResponse response){
        String cookieName = map.get("cookieName");
        String cookieValue = map.get("cookieValue");
        Cookie cookie=new Cookie(cookieName,cookieValue);
        //位于本站根目录下
        cookie.setPath("/");
        //过期时间s
        cookie.setMaxAge(600);
        response.addCookie(cookie);

        return new ShResultDTO<>(200,"已增加cookie");
    }

}
