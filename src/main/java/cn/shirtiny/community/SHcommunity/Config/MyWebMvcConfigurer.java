package cn.shirtiny.community.SHcommunity.Config;

import cn.shirtiny.community.SHcommunity.MyHandlerInterceptors.LoginHandlerInterceptor;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;
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
        excludePatterns.add("/Test6");
        //分页查询帖子列表
        excludePatterns.add("/listInvitationsByPage");

        //登录检测拦截器、拦截未登录者
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePatterns);
    }

    //文件上次大小限制，不设的话，默认1M
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("10000MB");
        //设置总请求数据大小
        factory.setMaxRequestSize("50000MB");
        return factory.createMultipartConfig();
    }

}
