package com.potato369.novel.exception;

import com.potato369.novel.basic.enums.ResultEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.exception
 * @ClassName SellerAuthorizeException
 * @Desc 卖家端自定义校验异常类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 18:53
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@NoArgsConstructor
public class SellerAuthorizeException extends RuntimeException {

    private static final long serialVersionUID = -3170669599060979538L;

    private Integer code;

    private String message;

    public SellerAuthorizeException(String message) {
        super(message);
        this.message = message;
    }

    public SellerAuthorizeException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellerAuthorizeException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
