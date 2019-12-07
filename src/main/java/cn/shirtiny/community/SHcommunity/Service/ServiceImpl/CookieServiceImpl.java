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
    public void addOneCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, Integer maxAge ) {
        Cookie cookie = setCookieArgs(cookieName, cookieValue, path);
        //过期时间s
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    @Override
    public void addOneCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, Integer maxAge, boolean httpOnly) {
        Cookie cookie = setCookieArgs(cookieName, cookieValue, path);
        //过期时间s
        cookie.setMaxAge(maxAge);
        //禁止js读取
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }

    //设置cookie
    @Override
    public Cookie setCookieArgs(String cookieName, String cookieValue, String path) {
        Cookie cookie=new Cookie(cookieName,cookieValue);
        //位于本站哪个目录下
        cookie.setPath(path);
        return cookie;
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
