package cn.shirtiny.community.SHcommunity.MyHandlerInterceptors;

import cn.shirtiny.community.SHcommunity.Exception.NoLoginException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //handle执行之前，return true为放行
        System.out.println("已经拦截请求，将跳转到登录界面，回调还没写");
        //若登录则放行
        if (request.getSession().getAttribute("user")!=null){
            return true;
        }
        //先判断是不是ajax请求
        String isAjax = request.getParameter("isAjax");
        if ("true".equals(isAjax)){
            System.out.println("是ajax请求");
            throw new NoLoginException();
        }
        //其他情况直接跳转到登录界面
        response.sendRedirect("/loginPage");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
