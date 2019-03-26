package com.potato369.novel.utils;

/**
 * <pre>
 * @PackageName com.potato369.novel.utils
 * @ClassName MathUtil
 * @Desc 数字比较工具类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/09 12:22
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class MathUtil {

    private static final Double MONEY_RANG = 0.01;
    /**
     * 比较两个金额是否相等
     * @param double1 金额1
     * @param double2 金额2
     * @return Boolean
     */
    public static Boolean equals(Double double1, Double double2){
        Double result =  Math.abs(double1 - double2);
        if (result < MONEY_RANG) {
            return true;
        } else {
            return false;
        }
    }
}
