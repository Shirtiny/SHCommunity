package cn.shirtiny.community.SHcommunity.Config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import cn.shirtiny.community.SHcommunity.Shiro.ShPwdMatcher;
import cn.shirtiny.community.SHcommunity.Shiro.ShRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    //自定义的shiro密码比较器 实例名为shPwdMatcher
    @Bean("shPwdMatcher")
    public ShPwdMatcher generateShPwdMatcher() {
        return new ShPwdMatcher();
    }

    //认证和授权器 实例名为shRealm  注入上面的密码比较器shPwdMatcher实例
    @Bean("shRealm")
    public ShRealm generateShRealm(@Qualifier("shPwdMatcher") ShPwdMatcher shPwdMatcher) {
        ShRealm shRealm = new ShRealm();
        //设置密码比较器
        shRealm.setCredentialsMatcher(shPwdMatcher);
        return shRealm;
    }

    //管理器 实例名为securityManager 注入上面的认证授权器shRealm实例
    @Bean("securityManager")
    public SecurityManager generateSecurityManager(@Qualifier("shRealm") ShRealm shRealm) {
        //管理器，接口的实现使用默认web管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shRealm);
        return securityManager;
    }

    //过滤器 实例名为shiroFilter 注入上面的管理器securityManager实例
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean generateFilterFactory(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //设置管理器
        factoryBean.setSecurityManager(securityManager);

        //设置登录地址
        factoryBean.setLoginUrl("/loginPage");
        //设置登录成功后的跳转地址
        factoryBean.setSuccessUrl("/");
        //设置未授权状态跳转的地址
        factoryBean.setUnauthorizedUrl("/403");

        LinkedHashMap<String, String> filterChainMap = new LinkedHashMap<>();
        /*DefaultFilter:
        anon：无需认证（登录）可以访问
        authc：必须认证才可以访问
        user：如果使用RememberMe的功能可以直接访问
        perms：该资源必须得到资源权限才可以访问
        role：该资源必须得到角色权限才可以访问
        */
        filterChainMap.put("/index", "authc");
        filterChainMap.put("/loginPage", "anon");
        //设置拦截规则
        factoryBean.setFilterChainDefinitionMap(filterChainMap);
        return factoryBean;
    }

    //处理shiro与spring的关联

    //使用自定的管理器 配置授权参数源顾问
    @Bean
    public AuthorizationAttributeSourceAdvisor generateAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    //使用代理
    @Bean
    public DefaultAdvisorAutoProxyCreator useProxy() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }


    //需要在shiro配置文件中增加一个方法，用于thymeleaf和shiro标签配合使用
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
    /*shiro：hasPermission 作用：用于判断用户是否拥有这个权限，有则显示这个div，没有则不显示。
    <div shiro:hasPermission="user:add">进入用户添加功能：<a href="add">用户添加</a><br/>
    </div>
    */
}
