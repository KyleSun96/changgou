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

        String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU5MTc0NDU2OCwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iLCJ1c2VyIiwic2FsZXNtYW4iXSwianRpIjoiZTZiNmZmN2YtZmNjMS00NjYxLTkwNjUtMDA4ZTkzZDA3NWZhIiwiY2xpZW50X2lkIjoiY2hhbmdnb3UiLCJ1c2VybmFtZSI6ImhlaW1hIn0.CCQ-nAmR85SIdOg0ocVFSLnWb6nnDQmrmemXnDvfgEsI0T4A9n81G8y551DuGPmZg_qQE1HtLk_hWYBB9COwnjriYrVyTkmurIuzBnrRlYQqg2V03NaTei_EnEeEd8-peWKVgEpQIV_DsPCkOA8gfnTZTaWamkolIgKBHfJdq3E0DfHf7q44iq8LLC5hNu6L714kJT6W1aElI9pTACLgZ54Om3RnG75-2zI_RIqZv0Es61BTTOF6CI_EZXfAa4_FglF-AnUH8ATqpekyHlnvqO97xyDv9AJgzCJRFJPE2Jbok-BcAtOcVdHQbzMBmWOW4IpreqruBveXCoi1CVYAGA";

        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFsEiaLvij9C1Mz+oyAmt47whAaRkRu/8kePM+X8760UGU0RMwGti6Z9y3LQ0RvK6I0brXmbGB/RsN38PVnhcP8ZfxGUH26kX0RK+tlrxcrG+HkPYOH4XPAL8Q1lu1n9x3tLcIPxq8ZZtuIyKYEmoLKyMsvTviG5flTpDprT25unWgE4md1kthRWXOnfWHATVY7Y/r4obiOL1mS5bEa/iNKotQNnvIAKtjBM4RlIDWMa6dmz+lHtLtqDD2LF1qwoiSIHI75LQZ/CNYaHCfZSxtOydpNKq8eb1/PGiLNolD4La2zf0/1dlcr5mkesV570NxRmU1tFm8Zd3MZlZmyv9QIDAQAB-----END PUBLIC KEY-----";

        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));

        // 解析出jwt令牌的内容
        String claims = token.getClaims();
        System.out.println(claims);
    }

}
