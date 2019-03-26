package com.potato369.novel.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.exception
 * @ClassName DynamicTaskException
 * @Desc 定时任务执行异常
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/02 16:27
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DynamicTaskException extends RuntimeException{

	private static final long serialVersionUID = -6998825249084380991L;

	private Integer code;

    private String message;

    public DynamicTaskException(String message) {
        super(message);
        this.message = message;
    }

    public DynamicTaskException(Throwable t) {
        super(t);
    }

    public DynamicTaskException(String msg, Throwable t) {
        super(msg, t);
    }
}
