package com.changgou.oauth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @Program: ChangGou
 * @ClassName: CreateJwtTest
 * @Description: 基于私钥生成jwt令牌
 * @Author: KyleSun
 **/
public class CreateJwtTest {

    @Test
    public void createJWT() {

        // 1.1 指定私钥的位置
        ClassPathResource classPathResource = new ClassPathResource("changgou.jks");
        // 1.2 指定秘钥库的密码
        String keyPass = "changgou";
        // 1. 创建一个秘钥工厂(两个参数如上)
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, keyPass.toCharArray());

        // 2.1 私钥别名
        String alias = "changgou";
        // 2.2 私钥密码
        String password = "changgou";
        // 2. 基于工厂获取私钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, password.toCharArray());
        // 将当前的私钥转换为 rsa 私钥，后面作为生成jwt的签名
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 3.1 jwt令牌的内容
        Map<String, String> map = new HashMap<>();
        map.put("company", "heima");
        map.put("address", "beijing");

        // 3. 生成jwt对象
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(map), new RsaSigner(rsaPrivateKey));
        // 获取jwt令牌
        String jwtEncoded = jwt.getEncoded();

        System.out.println(jwtEncoded);

    }
}
