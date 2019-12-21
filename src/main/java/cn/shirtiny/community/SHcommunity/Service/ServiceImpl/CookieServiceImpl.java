package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Service.IcookieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Service
public class CookieServiceImpl implements IcookieService {

    @Value("${ShJwt_Cookie_name}")
    private String shJwtCookieName;
    @Value("${ShJwt_Cookie_path}")
    private String shJwtCookiePath;

    @Override
    public void addOneCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, Integer maxAge) {
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
        Cookie cookie = new Cookie(cookieName, cookieValue);
        //位于本站哪个目录下
        cookie.setPath(path);
        return cookie;
    }

    //设置jwt 的cookie
    @Override
    public void addJwtCookie(HttpServletResponse response, String jwt, Integer maxAge) {
        addOneCookie(response, shJwtCookieName, jwt, shJwtCookiePath, maxAge, true);
    }

    //取出cookie中shJwt的值
    @Override
    public String getShJwtFromCookie(Cookie[] cookies) {
        return getCookieValueByName(cookies, shJwtCookieName);
    }

    //删除jwt maxAge=0表示删除
    @Override
    public void deleteShJwtFromCookie(HttpServletResponse response) {
        addOneCookie(response, shJwtCookieName, "", shJwtCookiePath, 0, true);
    }

    //指定cookieName 返回cookies数组中的对应值
    @Override
    public String getCookieValueByName(Cookie[] cookies, String cookieName) {
        if(cookies == null){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookieName, cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    public String getCookieValueByName(HttpServletRequest request, String cookieName) {
        return getCookieValueByName(request.getCookies(), cookieName);
    }
}
