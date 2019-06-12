package com.potato369.novel.app.web.converter;

import com.potato369.novel.app.web.dto.AccountDTO;
import com.potato369.novel.basic.dataobject.NovelUserAccount;
import org.springframework.beans.BeanUtils;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.converter
 * @ClassName NovelUserAccount2AccountDTOConverter
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/12 15:00
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class NovelUserAccount2AccountDTOConverter {

    public static AccountDTO convert(NovelUserAccount novelUserAccount) {
        AccountDTO accountDTO = AccountDTO.builder().build();
        BeanUtils.copyProperties(novelUserAccount, accountDTO);
        return accountDTO;
    }

    public static NovelUserAccount convert(AccountDTO accountDTO) {
        NovelUserAccount userAccount = NovelUserAccount.builder().build();
        BeanUtils.copyProperties(accountDTO, userAccount);
        return userAccount;
    }
}
