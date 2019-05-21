package com.potato369.novel.basic.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.repository
 * @ClassName AppVersion
 * @Desc app版本管理实体
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/20 17:36
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "AppVersion")
@NoArgsConstructor
@Table(name = "app_version")
public class AppVersion implements Serializable {

    /**
     * <pre>
     *  版本id
     * </pre>
     */
    @Id
    @Column(name = "id", nullable = false, length = 32)
    private String id;

    /**
     * <pre>
     * versionCode：版本代码
     * </pre>
     */
    @Column(name = "version_code", nullable = false, length = 11)
    private Integer versionCode;

    /**
     * <pre>
     * versionName：版本名称
     * </pre>
     */
    @Column(name = "version_name", nullable = false, length = 128)
    private String versionName;

    /**
     * <pre>
     * releaseNotes：更新版本说明
     * </pre>
     */
    @Column(name = "release_notes", nullable = true, length = 128)
    private String releaseNotes;

    /**
     * <pre>
     * app包下载资源地址
     * </pre>
     */
    @Column(name = "source_file_url", nullable = false, length = 255)
    private String sourceFileUrl;

    /**
     * <pre>
     * publishTime：发布更新时间
     * </pre>
     */
    @Column(name = "publish_time", nullable = false, length = 255)
    private Date publishTime;
}
