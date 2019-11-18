package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Service.IcookieService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "/cookieController")
public class CookieController {

    @Autowired
    private IcookieService cookieService;

    @RequestMapping(value = "/addCookie")
    @ResponseBody
    public ShResultDTO addOneCookie(@RequestBody Map<String,String> map,  HttpServletResponse response){
        String cookieName = map.get("cookieName");
        String cookieValue = map.get("cookieValue");
        cookieService.addOneCookie(response,cookieName,cookieValue,"/",600);
        return new ShResultDTO<>(200,"已增加cookie");
    }

}
