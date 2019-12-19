package cn.shirtiny.community.SHcommunity.Utils.JWT;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Utils.Encryption.AESKey;
import cn.shirtiny.community.SHcommunity.Utils.Encryption.RSAKey;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtRsaHelper {

    private PublicKey publicKey;
    private PrivateKey privateKey;
    //由自定字符串生成的AES密钥
    private SecretKey psk;

    //对称jwt 过期时间
    @Value("${Jwt_HmacSHA256_ExpirationTime}")
    private long expirationTimeHS;
    //RSA非对称jwt 60分钟后过期 毫秒
    @Value("${Jwt_RSA_ExpirationTime}")
    private long expirationTimeRS;


    public JwtRsaHelper(String publicKeyBase64Str,String privateKeyBase64Str,String aesKeyStr){
        //初始化RSA密钥对
        RSAKey rsaKey = null;
        try {
            rsaKey = new RSAKey(publicKeyBase64Str,privateKeyBase64Str);
            this.privateKey = rsaKey.getPrivateKey();
            this.publicKey = rsaKey.getPublicKey();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            log.error("jwtRsaHelper中的RsaKey密钥对恢复失败", e);
        }
        //初始化AES对称密钥
        AESKey aesKey = new AESKey(aesKeyStr.getBytes());
        this.psk = aesKey.getPsk();
    }

    //生成jwt 使用私钥签名
    public String createJwt(Map<String, Object> claims) {
        //注意把claims第一个set 或者使用add
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("type","jwt_RS")
                .setHeaderParam("signBy","shirtiny")
                .setId("jwt唯一id")
                .setSubject("主题")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeRS))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.RS256, this.privateKey);
        return builder.compact();
    }

    //生成jwt 使用私钥签名 封装user令牌
    public String createJwt(User user) {
        //把用户敏感信息置空
        user.setPassWord(null);
        Map<String, Object> claims = new HashMap<>();
        //这样user内的信息会作为一个map存入jwt，jwt解析取的时候，需要把user转为map
        claims.put("user",user);
        claims.put("userId",user.getUserId());
        //注意把claims第一个设置 或使用addClaims
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("type","jwt_RS")
                .setHeaderParam("signBy","shirtiny")
                .setId("Jwt唯一id")
                .setSubject(user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeRS))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.RS256, this.privateKey);
        return builder.compact();
    }

    //生成jwt 使用AES对称密钥签名
    public String createHsJwt(Map<String, Object> claims){
        //注意把claims第一个set 或者使用add
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("type","jwt_HS")
                .setHeaderParam("signBy","shirtiny")
                .setId("jwt唯一id")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeHS))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256,this.psk);
        return builder.compact();
    }

    //解析jwt_RS 返回值包含头、负荷、签名 如果过期，会抛出ExpiredJwtException
    public Jws<Claims> parseJwt(String jwt){
        return Jwts.parser().
                setSigningKey(this.publicKey)
                .parseClaimsJws(jwt);
    }

    //解析jwt_RS的body 使用公钥验证签名
    public Claims parseJwtBody(String jwt) {
        return parseJwt(jwt)
                .getBody();
    }

    //解析jwt_RS的header 使用公钥验证签名
    public JwsHeader parseJwtHeader(String jwt) {
        return parseJwt(jwt)
                .getHeader();
    }

    //解析jwt_RS的签名 使用公钥验证签名
    public String parseJwtSignature(String jwt) {
        return parseJwt(jwt)
                .getSignature();
    }

    //解析jwt_HS 返回值包含头、负荷、签名 如果过期，会抛出ExpiredJwtException
    public Jws<Claims> parseHsJwt(String jwt){
        return Jwts.parser().
                setSigningKey(this.psk)
                .parseClaimsJws(jwt);
    }

    //解析jwt_HS的body
    public Claims parseHsJwtBody(String jwt) {
        return parseJwt(jwt)
                .getBody();
    }

}