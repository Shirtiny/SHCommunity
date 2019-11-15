package cn.shirtiny.community.SHcommunity.Shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

//对shiro密码验证规则重写
public class ShPwdMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //用户输入的密码
        String inputPassWord = new String(upToken.getPassword());
        //数据库中查出的密码
        String dbPassword = (String)info.getCredentials();
        //返回是否相同
        return this.equals(inputPassWord,dbPassword);
    }
}
