package com.changgou.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 提取二进制流转换为xml字符串
 */
public class ConvertUtils {

    /**
     * @description: //TODO 输入流转换为xml字符串
     * @param: [inputStream]
     * @return: java.lang.String
     */
    public static String convertToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inputStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");
        return result;
    }
}
