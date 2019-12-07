package cn.shirtiny.community.SHcommunity.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IcookieService {

    void addOneCookie(HttpServletResponse response,String cookieName,String cookieValue,String Path,Integer maxAge);

    //指定cookieName 返回cookies数组中的对应值
    String getCookieValueByName(Cookie[] cookies, String cookieName);
    String getCookieValueByName(HttpServletRequest request, String cookieName);
}
