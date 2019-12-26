package cn.shirtiny.community.SHcommunity.Config;

import cn.shirtiny.community.SHcommunity.MyHandlerInterceptors.LoginHandlerInterceptor;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

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
        excludePatterns.add("/webjars/**");
        //登录页、github登录、主页、帖子详情页、错误页、cookie操作
        excludePatterns.add("/");
        excludePatterns.add("/github/**");
        excludePatterns.add("/loginPage");
//        excludePatterns.add("/invitationDetail/*");//这里只填一个*才不被拦截，不知道为什么
        excludePatterns.add("/error");
        excludePatterns.add("/cookieController/**");
        //测试
        excludePatterns.add("/test/**");
        excludePatterns.add("/Test6");
        //分页查询帖子列表
        excludePatterns.add("/listInvitationsByPage");
        //api
        excludePatterns.add("/shApi/**");
        //公开页面，无需登录即可访问
        excludePatterns.add("/shPub/**");
        //socket
        excludePatterns.add("/socket/**");

        //登录检测拦截器、对需要登录的页面拦截未登录者
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/ShPri/**").excludePathPatterns(excludePatterns);
    }

    //文件上传大小限制，不设的话，默认1M
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大  10000MB
        factory.setMaxFileSize(DataSize.parse("10000MB"));
        //设置总请求数据大小 50000MB
        factory.setMaxRequestSize(DataSize.parse("50000MB"));
        return factory.createMultipartConfig();
    }


    //注意这样配置会有问题，应该配置跨域过滤器 看 https://www.codercto.com/a/55519.html 我没试过 先这样吧
    //跨域处理 全局设置，这样就不用在每个Controller上加 @CrossOrigin(origins = "http://localhost:3000")了
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(30_000);

    }


}
