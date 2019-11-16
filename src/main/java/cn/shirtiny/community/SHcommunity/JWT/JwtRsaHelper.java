package cn.shirtiny.community.SHcommunity.JWT;

import cn.shirtiny.community.SHcommunity.Encryption.RSAKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtRsaHelper {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public JwtRsaHelper(String publicKeyBase64Str,String privateKeyBase64Str){
        RSAKey rsaKey = null;
        try {
            rsaKey = new RSAKey(publicKeyBase64Str,privateKeyBase64Str);
            this.privateKey = rsaKey.getPrivateKey();
            this.publicKey = rsaKey.getPublicKey();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            System.out.println("jwtRsaHelper中的RsaKey密钥对恢复失败");
        }
    }

    //生成jwt
    public String createJwt(Map<String, Object> claims) {
        //注意把claims第一个设置 使用私钥加密
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId("1")
                .setSubject("shirtiny")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.RS256, this.privateKey);
        return builder.compact();
    }

    //解析jwt
    public Claims parseJwt(String jwt) {
        //使用公钥解密
        return Jwts.parser()
                .setSigningKey(this.publicKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}