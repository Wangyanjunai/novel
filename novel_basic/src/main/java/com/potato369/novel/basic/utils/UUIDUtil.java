package com.potato369.novel.basic.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * <pre>
 * @PackageName com.potato369.novel.utils
 * @ClassName UUIDUtil
 * @Desc 创建唯一性id工具类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/25 17:48
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class UUIDUtil {

    /**
     * 创建订单号，订单详情
     * @return
     */
    public static synchronized String genTimstampUUID(){
        return DateUtil.getTimestamp().concat(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15).toLowerCase());
    }

    /**
     * 创建产品id，商品id
     * @return
     */
    public static synchronized String gen32UUID(){
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }
    
    /**
     * 创建用户mid，商品id
     * @return
     */
    public static synchronized String gen13MID() {
		return String.valueOf(System.currentTimeMillis());
	}

    public static void main(String[] args) {
        System.out.println(UUIDUtil.genTimstampUUID());
        System.out.println(UUIDUtil.gen32UUID());

        Date now = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(now);
        calendar1.add(Calendar.YEAR, 1);
        String lastYearDateStr = DateUtil.strFormat(calendar1.getTime(), "yyyy-MM-dd HH:mm:ss");
        System.out.println("一年后的时间：" + lastYearDateStr);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(now);
        calendar2.add(Calendar.MONTH, 3);
        String lastThreeMonthDateStr = DateUtil.strFormat(calendar2.getTime(), "yyyy-MM-dd HH:mm:ss");
        System.out.println("三个月后的时间：" + lastThreeMonthDateStr);
    }
}
