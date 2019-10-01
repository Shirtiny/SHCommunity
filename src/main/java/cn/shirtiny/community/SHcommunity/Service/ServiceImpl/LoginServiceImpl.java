package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Service.IloginService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginServiceImpl implements IloginService {
    @Override
    public String getRedirectFromCookie(HttpServletRequest request, HttpServletResponse response) {
        //拿cookie中的回调地址
        Cookie[] cookies = request.getCookies();
        //遍历cookies，拿到回调值后返回
        for (Cookie cookie:cookies){
            if ("shRedirectCookie".equals(cookie.getName())){
                String redirect = cookie.getValue();
                //重置回调地址为首页
                Cookie oraginCookie=new Cookie("shRedirectCookie","/");
                response.addCookie(oraginCookie);
                //跳转到回调地址
                return redirect;
            }
        }
        //没找到回调地址就返回主页
        return "/";
    }
}
