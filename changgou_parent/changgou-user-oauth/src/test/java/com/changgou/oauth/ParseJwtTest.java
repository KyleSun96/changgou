package com.changgou.oauth;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/**
 * @Program: ChangGou
 * @ClassName: ParseJwtTest
 * @Description: 基于公钥解析jwt令牌
 * @Author: KyleSun
 **/
public class ParseJwtTest {
    @Test
    public void parseJwt() {

        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU5MTc0MjIxMSwiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiZ29vZHNfbGlzdCJdLCJqdGkiOiIzMDZlZWMyZC1lMjBhLTRiODYtOGRiOC01NzBjZGQ5ZTEyZDkiLCJjbGllbnRfaWQiOiJjaGFuZ2dvdSIsInVzZXJuYW1lIjoiaGVpbWEifQ.RkCOs04lZp1wWRM6diTW1d8ofPorARBhA011G5dQzucNcSmvlpZvtOa8Ia0y5j8XJis1GdBcVPniktskQJnReqCe2asiephe71n2IBuxOqvsOmJEnL9zILkK1OVBTJYsv8HPmk9HPBtwLWysY1tvvkFo1ejbV_iBv7k2Yy5P6tPvEfrMhgyTgbA_fC6XSNXrhuUHxp_AqNyd-uErYY3uV5x8nYHPH5jLZ7e6_Lt9mLRr9VArGcpT28v_viyicUcuPROHnH6JKIdjRkyTAaE1ZCUJs3yiAypbojVDCv0h8Fpj6Q5nRXf97b9_cBNxRCXx2J2WAj1v0ie5THr8Xf3_-A";

        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFsEiaLvij9C1Mz+oyAmt47whAaRkRu/8kePM+X8760UGU0RMwGti6Z9y3LQ0RvK6I0brXmbGB/RsN38PVnhcP8ZfxGUH26kX0RK+tlrxcrG+HkPYOH4XPAL8Q1lu1n9x3tLcIPxq8ZZtuIyKYEmoLKyMsvTviG5flTpDprT25unWgE4md1kthRWXOnfWHATVY7Y/r4obiOL1mS5bEa/iNKotQNnvIAKtjBM4RlIDWMa6dmz+lHtLtqDD2LF1qwoiSIHI75LQZ/CNYaHCfZSxtOydpNKq8eb1/PGiLNolD4La2zf0/1dlcr5mkesV570NxRmU1tFm8Zd3MZlZmyv9QIDAQAB-----END PUBLIC KEY-----";

        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));

        // 解析出jwt令牌的内容
        String claims = token.getClaims();
        System.out.println(claims);
    }

}
