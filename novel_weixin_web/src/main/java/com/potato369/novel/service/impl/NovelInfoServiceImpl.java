package com.potato369.novel.service.impl;

import com.potato369.novel.basic.repository.NovelInfoRepository;
import com.potato369.novel.converter.NovelInfo2NovelInfoDTOConverter;
import com.potato369.novel.dto.NovelInfoDTO;
import com.potato369.novel.service.NovelInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @ClassName NovelInfoServiceImpl
 * @Desc 小说信息业务Service接口实现类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/14 13:50
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
public class NovelInfoServiceImpl implements NovelInfoService {

    @Autowired
    private NovelInfoRepository novelInfoRepository;

    /**
     * 新增
     *
     * @param novelInfoDTO
     * @return
     */
    @Override
    public NovelInfoDTO save(NovelInfoDTO novelInfoDTO) {
        return NovelInfo2NovelInfoDTOConverter.convert(novelInfoRepository.save(NovelInfo2NovelInfoDTOConverter.convert(novelInfoDTO)));
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        novelInfoRepository.delete(id);
    }

    /**
     * 修改
     *
     * @param novelInfoDTO
     * @return
     */
    @Override
    public NovelInfoDTO update(NovelInfoDTO novelInfoDTO) {
        return NovelInfo2NovelInfoDTOConverter.convert(novelInfoRepository.saveAndFlush(NovelInfo2NovelInfoDTOConverter.convert(novelInfoDTO)));
    }

    /**
     * 根据id查找单个小说
     *
     * @param id
     * @return
     */
    @Override
    public NovelInfoDTO find(String id) {
        return NovelInfo2NovelInfoDTOConverter.convert(novelInfoRepository.findOne(id));
    }

    /**
     * 根据小说的状态查询小说信息列表
     *
     * @param novelStatus
     * @return
     */
    @Override
    public List<NovelInfoDTO> findByNovelStatus(Integer novelStatus) {
        return NovelInfo2NovelInfoDTOConverter.convertToNovelInfoDTOList(novelInfoRepository.findNovelInfoByNovelStatus(novelStatus));
    }

    /**
     * 查找小说列表
     *
     * @return
     */
    @Override
    public List<NovelInfoDTO> findAll() {
        return NovelInfo2NovelInfoDTOConverter.convertToNovelInfoDTOList(novelInfoRepository.findAll());
    }

    /**
     * 查找小说分页列表
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<NovelInfoDTO> findAll(Pageable pageable) {
        return NovelInfo2NovelInfoDTOConverter.convertToNovelInfoDTOPage(novelInfoRepository.findAll(pageable), pageable);
    }

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.service.NovelInfoService#findByFileName(java.lang.String)
	 * </pre>
	 */
		
	@Override
	public NovelInfoDTO findByFileName(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}
}
