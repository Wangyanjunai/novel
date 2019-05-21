package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName AppVersionVO
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/20 17:58
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AppVersionVO {
    /**
     * <pre>
     * versionCode：版本代码
     * </pre>
     */
    @JsonProperty(value = "version_code")
    private Integer versionCode;

    /**
     * <pre>
     * versionName：版本名称
     * </pre>
     */
    @JsonProperty(value = "version_name")
    private String versionName;

    /**
     * <pre>
     * releaseNotes：更新版本说明
     * </pre>
     */
    @JsonProperty(value = "release_notes")
    private String releaseNotes;

    /**
     * <pre>
     * app包下载资源地址
     * </pre>
     */
    @JsonProperty(value = "source_file_url")
    private String sourceFileUrl;

    /**
     * <pre>
     * publishTime：发布更新时间
     * </pre>
     */
    @JsonProperty(value = "publish_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
}
