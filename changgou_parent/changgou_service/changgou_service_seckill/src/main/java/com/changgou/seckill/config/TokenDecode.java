package com.changgou.seckill.config;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Program:
 * @ClassName: TokenDecode
 * @Description: 令牌解析类
 * @Author: KyleSun
 **/
public class TokenDecode {

    // 公钥
    private static final String PUBLIC_KEY = "public.key";

    private static String publicKey = "";

    /**
     * @description: //TODO 获取令牌中的用户信息
     * @param: []
     * @return: java.util.Map<java.lang.String, java.lang.String>
     */
    public Map<String, String> getUserInfo() {
        // 获取授权信息
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        // 令牌解码
        return decodeToken(details.getTokenValue());
    }

    /**
     * @description: //TODO 令牌解码，读取令牌数据
     * @param: [token]
     * @return: java.util.Map<java.lang.String, java.lang.String>
     */
    public Map<String, String> decodeToken(String token) {
        // 校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(getPubKey()));

        // 获取Jwt原始内容
        String claims = jwt.getClaims();
        return JSON.parseObject(claims, Map.class);
    }


    /**
     * @description: //TODO 获取非对称加密公钥 Key
     * @param: []
     * @return: java.lang.String
     */
    public String getPubKey() {
        if (!StringUtils.isEmpty(publicKey)) {
            return publicKey;
        }
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            publicKey = br.lines().collect(Collectors.joining("\n"));
            return publicKey;
        } catch (IOException ioe) {
            return null;
        }
    }
}
