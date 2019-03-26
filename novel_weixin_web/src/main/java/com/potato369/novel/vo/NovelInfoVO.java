package com.potato369.novel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * @PackageName com.potato369.novel.vo
 * @ClassName NovelInfoVO
 * @Desc 小说信息VO
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 19:26
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Data
public class NovelInfoVO implements Serializable{

	private static final long serialVersionUID = -606966798012690739L;

	@JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "fileName")
    private String fileName;

    @JsonProperty(value = "cover")
    private String cover;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "author")
    private String author;

    @JsonProperty(value = "publisher")
    private String publisher;
    
    @JsonProperty(value = "bookId")
    private String bookId;

    @JsonProperty(value = "category")
    private Integer category;

    @JsonProperty(value = "categoryText")
    private String categoryText;
    
    @JsonProperty(value = "language")
    private String lang;
    
    @JsonProperty(value = "rootFile")
    private String rootFile;

    @JsonProperty(value = "introduction")
    private String introduction;

    @JsonProperty(value = "selected")
    private boolean selected;

    @JsonProperty(value = "privated")
    private boolean privated;

    @JsonProperty(value = "cache")
    private boolean cache;

    @JsonProperty(value = "haveRead")
    private boolean haveRead;
    
    @JsonProperty(value = "type")
    private Integer type;
    
    @JsonProperty(value = "result")
    private String result;
    
    @JsonProperty(value = "percent")
    private String percent;
    
    @JsonProperty(value = "readers", defaultValue = "0")
    private BigDecimal readers;
}
