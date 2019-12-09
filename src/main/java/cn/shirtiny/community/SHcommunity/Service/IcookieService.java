package cn.shirtiny.community.SHcommunity.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IcookieService {

    //添加一个cookie
    void addOneCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, Integer maxAge);

    void addOneCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, Integer maxAge, boolean httpOnly);

    //设置cookie参数
    Cookie setCookieArgs(String cookieName, String cookieValue, String path);

    //设置jwt 的cookie
    void addJwtCookie(HttpServletResponse response, String jwt, Integer maxAge);

    //取出cookie中shJwt的值
    String getShJwtFromCookie(Cookie[] cookies);

    //删除jwt maxAge=0表示删除
    void deleteShJwtFromCookie(HttpServletResponse response);

    //指定cookieName 返回cookies数组中的对应值
    String getCookieValueByName(Cookie[] cookies, String cookieName);

    String getCookieValueByName(HttpServletRequest request, String cookieName);
}
