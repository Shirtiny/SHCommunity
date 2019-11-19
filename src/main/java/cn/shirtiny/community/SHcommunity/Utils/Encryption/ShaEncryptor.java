package cn.shirtiny.community.SHcommunity.Utils.Encryption;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("shaEncryptor")
public class ShaEncryptor {

    @Value("${Shiro_AlgorithmName}")
    private String algorithmName;
    @Value("${Shiro_Salt}")
    private String salt;

    //加密
    public String encrypt(String source) {
        SimpleHash simpleHash = new SimpleHash(algorithmName, source, salt);
        return simpleHash.toHex();
    }

}
