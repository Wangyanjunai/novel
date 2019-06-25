package com.potato369.novel.basic.utils;

//import java.util.Calendar;
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
        System.out.println(UUIDUtil.gen13MID());

        Date now = new Date();
//        Calendar calendar_month1 = Calendar.getInstance();
//        calendar_month1.setTime(now);
//        calendar_month1.add(Calendar.MONTH, 1);
        String lastThreeMonthDateStr = DateUtil.strFormat(DateUtil.getAfterMonthDate(now, 1), "yyyy-MM-dd HH:mm:ss");
        System.out.println("一个月后的时间：" + lastThreeMonthDateStr);

//        Calendar calendar_month3 = Calendar.getInstance();
//        calendar_month3.setTime(now);
//        calendar_month3.add(Calendar.MONTH, 3);
        String lastThreeMonthDateStr3 = DateUtil.strFormat(DateUtil.getAfterMonthDate(now, 3), "yyyy-MM-dd HH:mm:ss");
        System.out.println("三个月后的时间：" + lastThreeMonthDateStr3);

//        Calendar calendar_month6 = Calendar.getInstance();
//        calendar_month6.setTime(now);
//        calendar_month6.add(Calendar.MONTH, 6);
        String lastThreeMonthDateStr6 = DateUtil.strFormat(DateUtil.getAfterMonthDate(now, 6), "yyyy-MM-dd HH:mm:ss");
        System.out.println("六个月后的时间：" + lastThreeMonthDateStr6);

//        Calendar calendar_month12 = Calendar.getInstance();
//        calendar_month12.setTime(now);
//        calendar_month12.add(Calendar.MONTH, 12);
        String lastYearDateStr12 = DateUtil.strFormat(DateUtil.getAfterMonthDate(now, 12), "yyyy-MM-dd HH:mm:ss");
        System.out.println("十二个月后的时间：" + lastYearDateStr12);

//        Calendar calendar_day7 = Calendar.getInstance();
//        calendar_day7.setTime(now);
//        calendar_day7.add(Calendar.DATE, 7);
        String lastDayDateStr7 = DateUtil.strFormat(DateUtil.getAfterDayDate(now, 7), "yyyy-MM-dd HH:mm:ss");
        System.out.println("七天后的时间：" + lastDayDateStr7);

//        Calendar calendar_day30 = Calendar.getInstance();
//        calendar_day30.setTime(now);
//        calendar_day30.add(Calendar.DATE, 30);
        String lastDayDateStr30 = DateUtil.strFormat(DateUtil.getAfterDayDate(now, 30), "yyyy-MM-dd HH:mm:ss");
        System.out.println("三十天后的时间：" + lastDayDateStr30);

//        Calendar calendar_day60 = Calendar.getInstance();
//        calendar_day60.setTime(now);
//        calendar_day60.add(Calendar.DATE, 60);
        String lastDayDateStr60 = DateUtil.strFormat(DateUtil.getAfterDayDate(now, 60), "yyyy-MM-dd HH:mm:ss");
        System.out.println("六十天后的时间：" + lastDayDateStr60);

//        Calendar calendar_day90 = Calendar.getInstance();
//        calendar_day90.setTime(now);
//        calendar_day90.add(Calendar.DATE, 90);
        String lastDayDateStr90 = DateUtil.strFormat(DateUtil.getAfterDayDate(now, 90), "yyyy-MM-dd HH:mm:ss");
        System.out.println("九十天后的时间：" + lastDayDateStr90);
        System.out.println("随机数：" + ((Math.random()*4 +1)/10));
    }
}
