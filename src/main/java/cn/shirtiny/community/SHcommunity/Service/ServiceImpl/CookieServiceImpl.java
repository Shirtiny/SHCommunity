package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Service.IcookieService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

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

    //指定cookieName 返回cookies数组中的对应值
    @Override
    public String getCookieValueByName(Cookie[] cookies, String cookieName) {
        for (Cookie cookie : cookies){
            if (Objects.equals(cookieName,cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    public String getCookieValueByName(HttpServletRequest request, String cookieName) {
        return getCookieValueByName(request.getCookies(),cookieName);
    }
}
