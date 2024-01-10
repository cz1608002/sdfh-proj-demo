package com.icbc.match.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 验证码工具类
 */
public class CaptchaUtil {

    private static final Logger log = LoggerFactory.getLogger(CaptchaUtil.class);

    /**
     * 生成数字验证码
     *
     * @param width 验证码长度
     * @return 验证码
     */
    public static String randomCaptcha(int width) {
        if (width <= 0) {
            return null;
        }

        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < width; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    /**
     * 生成默认6位长度数字验证码
     *
     * @return 验证码
     */
    public static String randomCaptcha() {
        return randomCaptcha(6);
    }


}
