package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 类名: TypeEnum
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年5月7日 上午11:49:47
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */

@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum TypeEnum implements CodeEnum<Object>{
	MALE("61ae83410ed64850bb3d334845d83bda", 0, "male", "男生"),
	FEMALE("61ae83410ed64850bb3d334845d83bdb", 1, "female", "女生"),
	PICTURE("61ae83410ed64850bb3d334845d83bdc", 3, "picture", "漫画"),
	HOT("62ae83410ed64850bb3d334845d83bda", 4, "hot", "近期热门"),
	EDIT("62ae83410ed64850bb3d334845d83bdb", 5, "edit", "主编推荐"),
	NEWEST("62ae83410ed64850bb3d334845d83bdc", 6, "newest", "新书推荐"),
	HANDSOME("62ae83410ed64850bb3d334845d83bde", 7, "handsome", "爽文推荐"),
	DEFAULT("62ae83410ed64850bb3d334845d83bdf", 8, "all", "所有")
	;
	
	private String id;
	
	private Integer code;

	private String en;
	
    private String cn;
    
    
}
