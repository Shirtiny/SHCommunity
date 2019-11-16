package cn.shirtiny.community.SHcommunity.Shiro;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//Shiro连接数据的桥梁 从数据库获取数据
public class ShRealm extends AuthorizingRealm {

    @Autowired
    private IuserService userService;

    //权限管理 暂无
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        User user0 = (User)principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        System.out.println("执行授权逻辑！");
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //创建一个用户主体，来获取登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        //user对象是通过楼下认证逻辑方法中SimpleAuthenticationInfo传递的
        User user1 = (User) subject.getPrincipal();
        //通过获取到的用户对象，使用用户对象中的角色id去用户权限表（rolePerm）中或获取该角色的权限
        List<String> list = new ArrayList<>();
//        list = rolePermService.findAllPermCode(user1.getRole_id());
        //添加资源的授权字段，此时该用户以及完成被授权的所有操作
        info.addStringPermissions(list);
//        return info;
        return null;
    }

    //登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        System.out.println("用户输入的用户名："+username);
        User user = userService.selectOneUserByUserName(username);
        System.out.println("查询到的用户："+user);
        //当前的realm名 this.getClass().getName()
        return new SimpleAuthenticationInfo(user,user.getPassWord(),this.getClass().getName());
    }
}
