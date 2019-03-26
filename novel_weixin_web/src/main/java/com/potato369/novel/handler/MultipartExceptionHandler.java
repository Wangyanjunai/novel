package com.potato369.novel.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * <pre>
 * @PackageName com.potato369.novel.handler
 * @ClassName MultipartExceptionHandler
 * @Desc Multipart Exception Handler
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/03/04 15:55
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@ControllerAdvice
public class MultipartExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public ModelAndView handleError(MultipartException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return new ModelAndView("redirect:/info/uploadStatus");
    }
}
