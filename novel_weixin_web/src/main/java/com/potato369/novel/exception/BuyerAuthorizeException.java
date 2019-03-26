package com.potato369.novel.exception;

import com.potato369.novel.basic.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.exception
 * @ClassName BuyerAuthorizeException
 * @Desc 买家端自定义异常类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 18:55
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BuyerAuthorizeException extends RuntimeException {

    private static final long serialVersionUID = -3170669599060979531L;

    private Integer code;

    private String message;

    public BuyerAuthorizeException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public BuyerAuthorizeException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
