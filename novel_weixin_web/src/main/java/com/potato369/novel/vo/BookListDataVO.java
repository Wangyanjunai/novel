package com.potato369.novel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookListDataVO implements Serializable {
		
	private static final long serialVersionUID = 7297301110984151218L;
	
	@JsonProperty(value = "bookList")
    private List<NovelInfoVO> bookListData;
}
