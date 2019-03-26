package com.potato369.novel.exception;

import com.potato369.novel.basic.enums.ResultEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.exception
 * @ClassName SellerCategoryException
 * @Desc 卖家端类目信息异常类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/18 17:43
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@NoArgsConstructor
public class SellerCategoryException extends RuntimeException{

    private static final long serialVersionUID = -3170669599060979538L;

    private Integer code;

    private String message;

    public SellerCategoryException(String message) {
        super(message);
        this.message = message;
    }

    public SellerCategoryException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellerCategoryException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
