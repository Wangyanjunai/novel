package com.potato369.novel.constant;

/**
 * <pre>
 * @PackageName com.potato369.novel.constant
 * @InterfaceName RedisConstant
 * @Desc Redis相关配置常量类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 18:57
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface RedisConstant {

    Integer EXPIRE = 7200;//2小时

    String TOKEN_PREFIX = "TOKEN_%s";//token前缀

    String FORCE_TOKEN_PREFIX = "TOKEN_%s";//token前缀

    String CART_COOKIE_PREFIX = "CART_COOKIE%s";//cart前缀

    String ADD_SUBSCRIBE_USER_TASK_CRON = "cms:addSubscribeUserTaskCron";

    String CANCELS_UNPAID_TIMEOUT_ORDER_TASK_CRON = "cms:cancelsUnpaidTimeoutOrderTaskCorn";
}
