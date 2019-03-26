package com.potato369.novel.utils;

import java.util.Random;

/**
 * <pre>
 * @PackageName com.potato369.novel.utils
 * @ClassName StringUtil
 * @Desc 字符串处理工具类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/23 12:15
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class StringUtil {

    private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final Random RANDOM = new Random();
    /**
     * <pre>
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     * @param content 传入的字符串
     * @param frontNum 保留前面字符的位数
     * @param endNum 保留后面字符的位数
     * @return 带星号的字符串
     * </pre>
     */
    public static String replaceString2Star(String content, int frontNum, int endNum) {
        if (content == null || content.trim().isEmpty()) {
            return content;
        }
        int len = content.length();

        if (frontNum >= len || frontNum < 0 || endNum >= len || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= len) {
            return content;
        }
        int beginIndex = frontNum;
        int endIndex = len - endNum;
        char[] cardChar = content.toCharArray();
        for (int j = beginIndex; j < endIndex; j++) {
            cardChar[j] = '*';
        }
        return new String(cardChar);
    }

    /**
     * <pre>
     * 获取随机字符串
     * @return
     * </pre>
     */
    public static String getRandomStr() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(RANDOM_STR.charAt(RANDOM.nextInt(RANDOM_STR.length())));
        }
        return sb.toString();
    }
}
