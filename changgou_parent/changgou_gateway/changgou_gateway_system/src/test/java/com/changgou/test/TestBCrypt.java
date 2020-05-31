package com.changgou.test;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @Program: ChangGou
 * @ClassName: TestBCrypt
 * @Description: BCrypt 密码加密测试案例
 * @Author: KyleSun
 **/
public class TestBCrypt {

    /**
     * 得到盐
     * <p>
     * 盐是一个随机生成的含有29个字符的字符串
     * 并且会与密码一起合并进行最终的密文生成
     * 并且每一次生成的盐的值都是不同的
     */
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            String salt = BCrypt.gensalt();
            System.out.println(salt);

            String saltPassword = BCrypt.hashpw("1234", salt);
            System.out.println(saltPassword);

            boolean checkpw = BCrypt.checkpw("1234", saltPassword);
            System.out.println("密码校验结果: " + checkpw);
        }
    }

}
