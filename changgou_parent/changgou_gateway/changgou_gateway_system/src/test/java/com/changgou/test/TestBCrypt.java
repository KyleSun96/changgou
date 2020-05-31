package com.changgou.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Date;

/**
 * @Program: ChangGou
 * @ClassName: TestBCrypt
 * @Description: 测试
 * @Author: KyleSun
 **/
public class TestBCrypt {

    /**
     * @description: //TODO 测试BCrypt密码加密
     * @param: [args]
     * @return: void
     * <p>
     * 盐是一个随机生成的含有29个字符的字符串
     * 并且会与密码一起合并进行最终的密文生成
     * 并且每一次生成的盐的值都是不同的
     */
    @Test
    public void testBCrypt() {

        for (int i = 0; i < 10; i++) {
            String salt = BCrypt.gensalt();
            System.out.println(salt);

            String saltPassword = BCrypt.hashpw("1234", salt);
            System.out.println(saltPassword);

            boolean checkpw = BCrypt.checkpw("1234", saltPassword);
            System.out.println("密码校验结果: " + checkpw);
        }
    }


    /**
     * @description: //TODO 测试JWT签发与验证token
     * @param: []
     * @return: void
     */
    @Test
    public void testJWT() {

        // 获取系统当前日期
        long currentTimeMillis = System.currentTimeMillis();
        // 设置过期时间为当前时间 + 1分钟
        Date date = new Date(currentTimeMillis + 60000);

        JwtBuilder builder = Jwts.builder()
                .setId("88")  // 设置 jwt 唯一编号
                .setSubject("主题") // 设置 jwt 主题
                .setIssuedAt(new Date()) // 设置 jwt 签发日期
                .setExpiration(date)    // 设置 jwt 过期时间
                .claim("role", "admin") // claims自定义内容
                .claim("company", "BAT")
                .signWith(SignatureAlgorithm.HS256, "signature"); // 设置 jwt 签名 使用 HS256 算法，

        // 生成 jwt令牌
        String jwtToken = builder.compact();
        System.out.println(jwtToken);

        // 解析 jwt令牌，得到其内部数据
        Claims claims = Jwts.parser().setSigningKey("signature")
                .parseClaimsJws(jwtToken).getBody();

        System.out.println("令牌内容：" + claims);

        /*
            得到解析结果
                {jti=88, sub=主题, iat=1590928908}
                其中，jti 为 jwt 唯一编号
                     sub 为 jwt 主题
                     iat 为 jwt 签发日期
         */

    }


}
