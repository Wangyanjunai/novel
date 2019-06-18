package com.potato369.novel.basic.utils;

import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.enums.TypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.utils
 * @ClassName BeanUtil
 * @Desc 类实现的功能点描述
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019-05-11 17:49
 * @CreateBy IntelliJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class BeanUtil {
    public static NovelInfo copy(NovelInfo source, NovelInfo target) {
        if (source != null && target != null) {
            if (StringUtils.isNotEmpty(source.getId())) {
                target.setId(source.getId());//设置小说id
            }
            if (StringUtils.isNotEmpty(source.getCoverURL())) {
                target.setCoverURL(source.getCoverURL());//设置小说封面图片路径url
            }
            if (StringUtils.isNotEmpty(source.getDataURL())) {
                target.setDataURL(source.getDataURL());//获取数据url
            }
            if (source.getCategoryType() != null) {
                target.setCategoryType(source.getCategoryType());//设置小说分类类型type
            }
            if (StringUtils.isNotEmpty(source.getCategoryENText())) {
                target.setCategoryENText(source.getCategoryENText());//设置小说分类英文名称
            }
            if (StringUtils.isNotEmpty(source.getCategoryCNText())) {
                target.setCategoryCNText(source.getCategoryCNText());//设置小说分类中文名称
            }
            if (StringUtils.isNotEmpty(source.getAuthor())) {
                target.setAuthor(source.getAuthor());//设置小说作者笔名
            }
            if (StringUtils.isNotEmpty(source.getTitle())) {
                target.setTitle(source.getTitle());//设置小说标题（名称）
            }
            if (StringUtils.isNotEmpty(source.getIntroduction())) {
                target.setIntroduction(source.getIntroduction());//设置小说简介
            }
            if (StringUtils.isNotEmpty(source.getNewestChapterId())) {
                target.setNewestChapterId(source.getNewestChapterId());//设置小说最新章节id
            }
            if (StringUtils.isNotEmpty(source.getNewestChapterTitle())) {
                target.setNewestChapterTitle(source.getNewestChapterTitle());//设置小说最新章节标题
            }
            if (source.getRetention() != null && !Integer.valueOf(0).equals(source.getRetention())) {//留存率，现在只是保存数字，显示的时候加上百分比
                target.setRetention(source.getRetention());//设置小说留存率，现在只是保存数字，显示的时候加上百分比
            }
            if (source.getTotalChapters() != null && !Integer.valueOf(0).equals(source.getTotalChapters())) {//章节总数
                target.setTotalChapters(source.getTotalChapters());//设置小说总章节数
            }
            if (source.getClickNumber() != null && !BigDecimal.ZERO.equals(source.getClickNumber())) {//点击次数
                target.setClickNumber(source.getClickNumber());//设置小说点击次数
            }
            if (source.getReaders() != null && !BigDecimal.ZERO.equals(source.getReaders())) {//阅读（点击）用户数；默认“0-阅读（点击）用户数”
                target.setReaders(source.getReaders());//设置小说阅读（点击）用户数；默认“0-阅读（点击）用户数”
            }
            if (source.getRecentReaders() !=null && !BigDecimal.ZERO.equals(source.getRecentReaders())) {//最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”
                target.setRecentReaders(source.getRecentReaders());//设置小说最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”
            }
            if (source.getTotalWords()!=null && !BigDecimal.ZERO.equals(source.getTotalWords())) {//总字数
                target.setTotalWords(source.getTotalWords());//设置小说总字数
            }
            if (source.getCreateTime() != null) {
                target.setCreateTime(source.getCreateTime());//设置小说创建时间
            }
            if (source.getUpdateTime() != null) {
                target.setUpdateTime(source.getUpdateTime());//设置小说更新时间
            }
        }
        return target;
    }

    public static String getId(String categoryType) {
        String id = null;
        //如果是男生首页
        if (TypeEnum.MALE.getCn().equals(categoryType) || TypeEnum.MALE.getEn().equals(categoryType)) {
            id = TypeEnum.MALE.getId();
        }
        //如果是女生首页
        if (TypeEnum.FEMALE.getCn().equals(categoryType) || TypeEnum.FEMALE.getEn().equals(categoryType)) {
            id = TypeEnum.FEMALE.getId();
        }
        //如果是漫画首页
        if (TypeEnum.PICTURE.getCn().equals(categoryType) || TypeEnum.PICTURE.getEn().equals(categoryType)) {
            id = TypeEnum.PICTURE.getId();
        }
        return id;
    }
}
