package cn.shirtiny.community.SHcommunity.Utils.Encryption;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class AESKey {

    //由自定字符串生成的AES密钥
    private SecretKey psk;
    //密钥的Base64编码字符串
    private String aesKeyBase64Str;

    //基于自定字符串的字符数组 生成密钥
    public AESKey(byte[] keyBytes) {
        //key生成器
        KeyGenerator aesKeyGenerator = null;
        try {
            aesKeyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert aesKeyGenerator != null;
        //初始化key生成器 并要求密钥长度为 256 ( keySize: must be equal to 128, 192 or 256 )
        aesKeyGenerator.init(256, new SecureRandom(keyBytes));
        //构造aesKey
        this.psk = aesKeyGenerator.generateKey();
        //aesKey 的base64编码表示
        this.aesKeyBase64Str = Base64.getEncoder().encodeToString(this.psk.getEncoded());
        //打印
//        System.out.println(this.aesKeyBase64Str);
    }

    //从已有密钥base64字符串中恢复密钥
    public AESKey(String aesKeyBase64Str) {
        this.aesKeyBase64Str = aesKeyBase64Str;
        byte[] decode = Base64.getDecoder().decode(this.aesKeyBase64Str);
        this.psk = new SecretKeySpec(decode, "AES");
    }

    //返回aesKey
    public SecretKey getPsk() {
        return psk;
    }

    //返回aesKey的Base64字符串
    public String getAesKeyBase64Str() {
        return aesKeyBase64Str;
    }

    //加密
    public byte[] encrypt(String message) throws GeneralSecurityException {
        Cipher aesCipher = Cipher.getInstance("AES");
        //初始化 加密模式
        aesCipher.init(Cipher.ENCRYPT_MODE, this.psk);
        return aesCipher.doFinal(message.getBytes());
    }

    //解密
    public String decrypt(byte[] encrypted) throws GeneralSecurityException {
        Cipher aesCipher = Cipher.getInstance("AES");
        //初始化 解密模式
        aesCipher.init(Cipher.DECRYPT_MODE, this.psk);
        byte[] bytes = aesCipher.doFinal(encrypted);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    //示例方法
    public void aesKeyTest() {
        //基于自定字符串数组 生成密钥 来测试加密解密
        String keyStr = "自定字符串";
        //注意这里是通过byte[]来构造对象
        AESKey aesKey = new AESKey(keyStr.getBytes());
        String message = "未加密的消息";
        try {
            //加密
            byte[] encrypt = aesKey.encrypt(message);
            System.out.println("加密后：" + new String(encrypt, StandardCharsets.UTF_8));
            //解密
            String decrypt = aesKey.decrypt(encrypt);
            System.out.println("解密后：" + decrypt);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        //从已有字符串中 回复aesKey
        String aesBase64Str = Base64.getEncoder().encodeToString(aesKey.getPsk().getEncoded());
        //注意这里是通过String来构造对象
        AESKey aesKey2 = new AESKey(aesBase64Str);
        System.out.println(Base64.getEncoder().encodeToString(aesKey2.getPsk().getEncoded()));
    }
}
