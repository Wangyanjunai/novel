package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.enums
 * @EnumName ResultEnum
 * @Desc 返回给前端的消息枚举
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/25 11:30
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum ResultEnum implements CodeEnum<Object> {

    SUCCESS(0, "成功"),

    PARAM_ERROR(1, "参数不正确"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),

    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),

    ORDER_NOT_EXIST(12, "订单不存在"),

    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "订单状态不正确"),

    ORDER_UPDATE_FAIL(15, "订单更新失败"),

    ORDER_DETAIL_EMPTY(16, "订单详情为空"),

    ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),

    CART_EMPTY(18, "购物车为空"),

    ORDER_OWNER_ERROR(19, "该订单不属于当前用户"),

    WECHAT_MP_ERROR(20, "微信公众账号方面错误"),

    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21, "微信支付异步通知金额校验不通过"),

    ORDER_CANCEL_SUCCESS(22, "订单取消成功"),

    ORDER_FINISH_SUCCESS(23, "订单完结成功"),

    PRODUCT_STATUS_ERROR(24, "商品状态不正确"),

    LOGIN_FAIL(25, "登录失败, 登录信息不正确"),

    LOGOUT_SUCCESS(26, "登出成功"),

    LOGOUT_FAIL(52, "登出失败, 清除信息出错"),

    OTHER_ERROR(27, "其他系统错误"),

    PRODUCT_ON_SALE_SUCCESS(28, "商品上架成功"),

    PRODUCT_ON_SALE_FAIL(29, "商品上架失败"),

    PRODUCT_OFF_SALE_SUCCESS(30, "商品下架成功"),

    PRODUCT_OFF_SALE_FAIL(31, "商品下架失败"),

    PRODUCT_SAVE_SUCCESS(32, "新增商品成功"),

    PRODUCT_SAVE_FAIL(33, "新增商品失败"),

    PRODUCT_UPDATE_SUCCESS(34, "修改商品成功"),

    PRODUCT_UPDATE_FAIL(35, "修改商品失败"),

    PRODUCT_SAVE_OR_UPDATE_SUCCESS(36, "新增或者修改商品成功"),

    PRODUCT_SAVE_OR_UPDATE_FAIL(37, "新增或者修改商品失败"),

    CATEGORY_SAVE_OR_UPDATE_SUCCESS(38, "新增或者修改商品类目成功"),

    CATEGORY_SAVE_OR_UPDATE_FAIL(39, "新增或者修改商品类目失败"),

    CATEGORY_SAVE_SUCCESS(40, "新增商品类目成功"),

    CATEGORY_UPDATE_SUCCESS(41, "修改商品类目成功"),

    CATEGORY_NOT_EXIST(42, "商品类目不存在"),

    CATEGORY_NOT_DELETED(43, "此商品类目下存在一个或者多个商品信息，不允许删除此类目"),

    CATEGORY_WHETHER_OR_NOT_TO_DELETED_STATUS_ERROR(44, "此商品类目是否标记已删除状态已经是已标记为是删除状态"),

    ENCODING_UNSUPPORT_ERROR(45, "编码格式不支持错误"),

    MP_OTHER_ERROR(46, "微信公众平台其他方面错误"),

    MP_OPENID_ERROR(47, "用户微信openid为空"),

    MP_USER_INFO_EMPTY(48, "用户信息不存在"),

    COOKIE_TOKEN_IS_NULL_ERROR(49, "Cookie中查询不到token，token为空"),

    REDIS_TOKEN_IS_NULL_ERROR(50, "Redis缓存中查询不到token，token为空"),

    COOKIE_OPENID_IS_NULL_ERROR(51, "Cookie中查询不到openid，openid为空"),

    CATEGORY_LIST_ERROR(52, "查询买家的小说类目信息列表出现错误"),

    CATEGORY_PARENT_ERROR(53, "父级类目信息不存在的类目信息列表出现错误"),

    NOVEL_INFO_LIST_ERROR(54, "查询小说信息列表出现错误"),
    
    ORDER_PAY_TYPE_ERROR(55, "订单支付方式出现错误"),

    ALIPAY_NOTIFY_MONEY_VERIFY_ERROR(56, "支付宝支付异步通知金额校验不通过"),

    ALIPAY_NOTIFY_PID_VERIFY_ERROR(57, "支付宝支付异步通知卖家支付宝用户号校验不一致"),

    ALIPAY_NOTIFY_APPID_VERIFY_ERROR(58, "支付宝分配给开发者的应用Id校验不一致"),
    ;

    private Integer code;

    private String message;
}
