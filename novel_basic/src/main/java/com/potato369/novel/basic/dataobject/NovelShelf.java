package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.dataobject.idClass.NovelShelfIdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.dataobject
 * @ClassName NovelShelf
 * @Desc novel_shelf 小说app书架信息记录实体类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/14 17:42
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "NovelShelf")
@NoArgsConstructor
@Table(name = "novel_shelf")
@IdClass(value = NovelShelfIdClass.class)
public class NovelShelf implements Serializable {

    /**
     * <pre>
     * @serialField shelfId：联合主键，书架id。
     * </pre>
     */
    @Id
    @Column(name = "shelf_id", nullable = false, length = 32)
    private String shelfId;

    /**
     * <pre>
     * @serialField userId：联合主键，用户id。
     * </pre>
     */
    @Id
    @Column(name = "user_id", nullable = false, length = 32)
    private String userId;

    /**
     * <pre>
     * @serialField createTime：创建时间。
     * </pre>
     */
    @Column(name = "create_time", nullable = false, length = 64)
    private Date createTime;

    /**
     * <pre>
     * @serialField updateTime：更新时间。
     * </pre>
     */
    @Column(name = "update_time", nullable = false, length = 64)
    private Date updateTime;
}
