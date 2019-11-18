package cn.shirtiny.community.SHcommunity.Service;

import javax.servlet.http.HttpServletResponse;

public interface IcookieService {
    void addOneCookie(HttpServletResponse response,String cookieName,String cookieValue,String Path,Integer maxAge);
}
