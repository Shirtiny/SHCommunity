package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Service.IcookieService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class CookieServiceImpl implements IcookieService {
    @Override
    public void addOneCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, Integer maxAge) {
        Cookie cookie=new Cookie(cookieName,cookieValue);
        //位于本站哪个目录下
        cookie.setPath(path);
        //过期时间s
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
