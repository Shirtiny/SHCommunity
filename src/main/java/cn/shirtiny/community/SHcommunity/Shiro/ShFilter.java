package cn.shirtiny.community.SHcommunity.Shiro;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;
import cn.shirtiny.community.SHcommunity.Exception.LoginFailedException;
import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//自定义的shiro拦截过滤器
//执行流程 preHandle -> isAccessAllowed -> isLoginAttempt -> executeLogin
@Component
public class ShFilter extends BasicHttpAuthenticationFilter {

    @Autowired
    private IjwtService jwtService;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        https://www.jianshu.com/p/39efa72e129b
        //对跨域提供支持
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpServletResponse.setStatus(HttpStatus.OK.value());
//            return false;
//        }
        return super.preHandle(request, response);
    }

    //登录认证 授权
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //如果有登录意向
        if (isLoginAttempt(request, response)) {
            try {
                //执行登录 登录成功后放行
               return executeLogin(request, response);
            } catch (LoginFailedException e) {
                //登录出现异常
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                //给一个登录失败错误码
                httpServletResponse.setStatus(ShErrorCode.Login_Failed_Error.getCode());
                return false;
            }
        }else {
            //不放行
            return false;
        }
    }

    //当访问被拒绝时
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        return false;
    }

    //是否要尝试登录
    @Override
    protected boolean isLoginAttempt(ServletRequest request ,ServletResponse response) {
        //请求头的Authorization有值时，表示想尝试登录
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String jwt = httpRequest.getHeader("Authorization");
        return jwt != null && !"".equals(jwt.trim());
//        return super.isLoginAttempt(request,response);
    }

    //执行登录
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws LoginFailedException {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        //解析携带token
        Map<String, Object> calims = jwtService.parseJwtByRequest(httpRequest);
        //暂时处理 能解析出来，就登录成功
        if (calims!=null){
            return true;
        }else {
            throw new LoginFailedException("登录失败，令牌无效");
        }

    }
}
