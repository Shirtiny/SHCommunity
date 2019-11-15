package cn.shirtiny.community.SHcommunity.Encryption;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAKey {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    //生成密钥
    public RSAKey() throws GeneralSecurityException {
        //密钥对生成器 生成rsa密钥对生成器
        KeyPairGenerator rsaGenerator = KeyPairGenerator.getInstance("RSA");
        //初始化生成器
        rsaGenerator.initialize(1024);
        //生成密钥对
        KeyPair keyPair = rsaGenerator.generateKeyPair();
        //公钥
        this.publicKey = keyPair.getPublic();
        //私钥
        this.privateKey = keyPair.getPrivate();
        System.out.println("生成公钥：" + Base64.getEncoder().encodeToString(this.publicKey.getEncoded()));
        System.out.println("生成私钥：" + Base64.getEncoder().encodeToString(this.privateKey.getEncoded()));
    }

    //从已有字符数组中恢复密钥
    public RSAKey(byte[] publicKeyBytes, byte[] privateKeyBytes) throws GeneralSecurityException {
        //RSA密钥工厂
        KeyFactory rsaFC = KeyFactory.getInstance("RSA");
        //恢复publicKey
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        this.publicKey = rsaFC.generatePublic(publicKeySpec);
        //恢复privateKet
        X509EncodedKeySpec privateKeySpec = new X509EncodedKeySpec(privateKeyBytes);
        this.privateKey = rsaFC.generatePrivate(privateKeySpec);
    }


    public PublicKey getPublicKey() {
        return publicKey;
    }

    //得到公钥字符数组
    public byte[] getPublicKeyBytes() {
        return this.publicKey.getEncoded();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    //得到私钥字符数组
    public byte[] getPrivateKeyBytes() {
        return this.privateKey.getEncoded();
    }

    //加密
    public byte[] encript(byte[] str,Key key) throws GeneralSecurityException{
        Cipher rsaCipher = Cipher.getInstance("RSA");
        //初始化 加密模式
        rsaCipher.init(Cipher.ENCRYPT_MODE, key);
        return rsaCipher.doFinal(str);
    }

    //解密
    public byte[] decript(byte[] str,Key key) throws GeneralSecurityException{
        Cipher rsaCipher = Cipher.getInstance("RSA");
        //初始化 解密模式
        rsaCipher.init(Cipher.DECRYPT_MODE, key);
        return rsaCipher.doFinal(str);
    }

    //示例方法
    public void rsaKeyTest() throws GeneralSecurityException {
        RSAKey rsaKey = new RSAKey();
        String message = "公钥加密的消息";
        //公钥加密私钥解
        //公钥加密
        byte[] encriptedMessage = rsaKey.encript(message.getBytes(), rsaKey.getPublicKey());
        System.out.println(new String(encriptedMessage, StandardCharsets.UTF_8));
        //私钥解密
        byte[] decriptedMessage = rsaKey.decript(encriptedMessage, rsaKey.getPrivateKey());
        System.out.println(new String(decriptedMessage, StandardCharsets.UTF_8));

        //私钥加密公钥解
        message = "私钥加密的消息";
        //私钥加密
        encriptedMessage = rsaKey.encript(message.getBytes(),rsaKey.getPrivateKey());
        System.out.println(new String(encriptedMessage, StandardCharsets.UTF_8));
        //公钥解密
        decriptedMessage = rsaKey.decript(encriptedMessage,rsaKey.getPublicKey());
        System.out.println(new String(decriptedMessage, StandardCharsets.UTF_8));
    }
}
