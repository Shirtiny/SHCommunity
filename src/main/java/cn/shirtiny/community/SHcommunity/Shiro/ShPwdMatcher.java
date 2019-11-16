package cn.shirtiny.community.SHcommunity.Shiro;

import cn.shirtiny.community.SHcommunity.Encryption.ShaEncryptor;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

//对shiro密码验证规则重写
public class ShPwdMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private ShaEncryptor shaEncryptor;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //用户输入的密码
        String inputPassWord = new String(upToken.getPassword());
        //对用户输入的密码加密
        String encryptedInput = shaEncryptor.encrypt(inputPassWord);
        //数据库中查出的密码密文
        String dbPassword = (String)info.getCredentials();
        //返回两者是否相同
        return this.equals(encryptedInput,dbPassword);
    }
}
