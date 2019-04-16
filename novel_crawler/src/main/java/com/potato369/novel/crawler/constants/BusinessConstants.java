package com.potato369.novel.crawler.constants;

/**
 * <pre>
 * @PackageName com.potato369.novel.crawler.constants
 * @ClassName BusinessConstants
 * @Desc 业务字典类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/16 11:05
 * @CreateBy IntellJ IDEA 2018.3.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class BusinessConstants {
    /**
     * 定义一个静态变量，用于全局协调页数
     * 默认从第一页开始
     */
    public static Integer CURRENT_PAGE_NUMBER = 1;
    /**
     * 定义一个静态变量，用于记录每次的起始url
     * */
    public static String CURRENT_START_URL = null;
    /**
     * 定义一个静态变量，用于记录当前获取数据的url
     * */
    public static String CURRENT_GET_DATA_URL = null;
}
