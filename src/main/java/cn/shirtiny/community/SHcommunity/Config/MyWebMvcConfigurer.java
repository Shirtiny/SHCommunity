package cn.shirtiny.community.SHcommunity.Config;

import cn.shirtiny.community.SHcommunity.MyHandlerInterceptors.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePatterns = new ArrayList<>();
        //静态资源
        excludePatterns.add("/css/**");
        excludePatterns.add("/js/**");
        excludePatterns.add("/js/myJs/**");
        excludePatterns.add("/image/**");
        excludePatterns.add("/fonts/**");
        excludePatterns.add("/editormd/**");
        //登录页、github登录、主页、帖子详情页、错误页、cookie操作
        excludePatterns.add("/");
        excludePatterns.add("/github/**");
        excludePatterns.add("/loginPage");
        excludePatterns.add("/invitationDetail/*");//这里只填一个*才不被拦截，不知道为什么
        excludePatterns.add("/error");
        excludePatterns.add("/cookieController/**");
        //测试
        excludePatterns.add("/test/**");

        //登录检测拦截器、拦截未登录者
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePatterns);
    }


}
