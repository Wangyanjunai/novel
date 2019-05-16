package com.potato369.novel.basic.dataobject.idClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.dataobject
 * @ClassName NovelShelfIdClass
 * @Desc NovelShelf
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/16 15:55
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@NoArgsConstructor
public class NovelShelfDetailIdClass implements Serializable {

    private String shelfDetailId;

    private String shelfId;

    private String novelId;

    private String userId;

    private String chapterId;
}