package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 类名: LoadingDataVO
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年4月18日 上午10:55:21
 * 描述:
 * @author Jack
 * @version
 * @since JDK 1.6
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class LoadingDataVO {

    /**
     * <pre>
     * id:广告id
     * </pre>
     */
    @JsonProperty(value = "id", required = true)
    private String id;

    /**
     * <pre>
     * tag1：tag1：标识广告是应用类广告还是初始页面跳转广告，0-跳转广告，1-应用内广告。
     * </pre>
     */
    @JsonProperty(value = "tag1", required = true)
    private Integer tag1;

    /**
     * <pre>
     * tag2：标识广告是否可以跳转，0-可以跳转，1-不可以跳转。
     * </pre>
     */
    @JsonProperty(value = "tag2", required = true)
    private Integer tag2;

    /**
     * <pre>
     * imageUrl:图片链接地址
     * </pre>
     */
    @JsonProperty(value = "imageUrl", required = true)
    private String imageUrl;

    /**
     * <pre>
     * linkUrl:广告跳转链接地址
     * </pre>
     */
    @JsonProperty(value = "linkUrl")
    private String linkUrl;

    /**
     * <pre>
     * novelId:应用类推荐的小说id，不须是应用内广告，才必须，否则为空
     * </pre>
     */
    @JsonProperty(value = "novelId")
    private String novelId;

    /**
     * <pre>
     * novelParentCategoryId:应用类推荐的小说父类类型id，不须是应用内广告，才必须，否则为空
     * </pre>
     */
    @JsonProperty(value = "novelParentCategoryId")
    private String novelParentCategoryId;
}
