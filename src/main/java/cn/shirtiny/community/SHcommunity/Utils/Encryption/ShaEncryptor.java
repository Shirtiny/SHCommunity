package cn.shirtiny.community.SHcommunity.Utils.Encryption;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("shaEncryptor")
public class ShaEncryptor {

    //使用的加密算法名
    @Value("${Shiro_AlgorithmName}")
    private String algorithmName;
    //盐值
    @Value("${Shiro_Salt}")
    private String salt;

    /**加密
     * @param source 待加密字符串
     * @return 加密后的密文*/
    public String encrypt(String source) {
        SimpleHash simpleHash = new SimpleHash(algorithmName, source, salt);
        return simpleHash.toHex();
    }

    public String md5EncryptWithoutSalt(String source) {
        SimpleHash simpleHash = new SimpleHash("md5", source,null,1);
        return simpleHash.toHex();
    }
}
