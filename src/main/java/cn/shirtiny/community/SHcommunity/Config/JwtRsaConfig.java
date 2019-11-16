package cn.shirtiny.community.SHcommunity.Config;

import cn.shirtiny.community.SHcommunity.JWT.JwtRsaHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtRsaConfig {

    //jwt公钥
    @Value("${Jwt_PublicKey_Base64Str}")
    private String publicKeyBase64Str;
    //jwt私钥
    @Value("${Jwt_PrivateKey_Base64Str}")
    private String privateKeyBase64Str;

    @Bean
    public JwtRsaHelper generateJwtRsaHelper(){
        return new JwtRsaHelper(publicKeyBase64Str,privateKeyBase64Str);
    }

}
