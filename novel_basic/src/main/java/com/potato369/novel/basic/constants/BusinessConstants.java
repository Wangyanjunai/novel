package com.potato369.novel.basic.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * <pre>
 * @PackageName com.potato369.novel.crawler.constants
 * @ClassName BusinessConstants
 * @Desc 业务数据字典类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/16 11:05
 * @CreateBy IntellJ IDEA 2018.3.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class BusinessConstants {

	//定义一个静态变量，用于全局协调页数，默认从第一页开始
    public static Integer CURRENT_PAGE_NUMBER = 1;
    
    //定义一个静态变量，用于记录总页数
    public static Integer CURRENT_TOTAL_PAGE_NUMBER = 1;

    //定义一个静态变量，用于记录每次的起始URL
    public static String CURRENT_START_URL = null;

    //定义一个静态变量，用于记录当前获取数据的URL
    public static String CURRENT_GET_DATA_URL = null;

    //定义一个静态变量，用于记录当前获取每一本书章节数据的URL
    public static String CURRENT_GET_BOOK_DATA_URL = null;
    
    //静态线程池
    public static ExecutorService threadPoolStart = Executors.newFixedThreadPool(1);

    //静态线程池
    public static ExecutorService threadPoolPage = Executors.newFixedThreadPool(1);

    //静态线程池
    public static ExecutorService threadPoolBook = Executors.newFixedThreadPool(10);

    //存放执行的线程
    public static Map<String,Thread> threadMap = new ConcurrentHashMap<String,Thread>();
    
    //线程锁
    public static Lock lock = new ReentrantLock();
    
    //必须配合lock.lock()使用
    public static Condition conditionPoolStart = lock.newCondition();
    
    //必须配合lock.lock()使用
    public static Condition conditionPoolPage = lock.newCondition();
    
    //必须配合lock.lock()使用
    public static Condition conditionPoolBook = lock.newCondition();
}
