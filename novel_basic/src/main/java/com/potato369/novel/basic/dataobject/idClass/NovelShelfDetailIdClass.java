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
@Data
@DynamicUpdate
@NoArgsConstructor
public class NovelShelfDetailIdClass implements Serializable {

	/**
	 * <pre>
	 * @serialField serialVersionUID：序列号。
	 * </pre>
	 */
	private static final long serialVersionUID = -5091762923632258642L;

	/**
	 * <pre>
	 * @serialField shelfId：小说书架id。
	 * </pre>
	 */
    private String shelfId;
    
	/**
	 * <pre>
	 * @serialField shelfDetailId：小说书架详情id。
	 * </pre>
	 */
	private String shelfDetailId;
	
	/**
	 * <pre>
	 * @serialField novelId：小说id。
	 * </pre>
	 */
    private String novelId;
    
	/**
	 * <pre>
	 * @serialField userId：用户id。
	 * </pre>
	 */
    private String userId;
}
