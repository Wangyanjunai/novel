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
 * @ClassName NovelUserInfoIdClass
 * @Desc NovelUserInfoIdClass
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/16 11:29
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@NoArgsConstructor
public class NovelUserInfoIdClass implements Serializable {
	
	/**
	 * <pre>
	 * @serialField serialVersionUID：序列号。
	 * </pre>
	 */	
	private static final long serialVersionUID = -8166540112953823633L;

	/**
	 * <pre>
	 * @serialField mid：用户mid。
	 * </pre>
	 */
	private String mid;
	
	/**
	 * <pre>
	 * @serialField meid：手机串号。
	 * </pre>
	 */
	private String meid;
}
