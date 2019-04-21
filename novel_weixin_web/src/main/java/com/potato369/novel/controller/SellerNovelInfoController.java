package com.potato369.novel.controller;

import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.conf.prop.ProjectUrlProperties;
import com.potato369.novel.dto.CategoryDTO;
import com.potato369.novel.dto.NovelInfoDTO;
import com.potato369.novel.form.NovelInfoForm;
import com.potato369.novel.service.CategoryService;
import com.potato369.novel.service.NovelInfoService;
import com.potato369.novel.utils.DateUtil;
import com.potato369.novel.utils.FileUtil;

import lombok.extern.slf4j.Slf4j;
import nl.siegmann.epublib.domain.*;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * @PackageName com.potato369.novel.controller
 * @ClassName SellerNovelInfoController
 * @Desc 卖家端小说信息Controller
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 15:14
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Controller
@Slf4j
@RequestMapping(value = "/info")
public class SellerNovelInfoController {

    @Autowired
    private NovelInfoService novelInfoService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProjectUrlProperties projectUrlProperties;

    /**
     * <pre>
     * 分页查询小说信息列表
     * @param page
     * @param size
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/list")
    public ModelAndView list(@RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        if (log.isDebugEnabled()) {
            log.debug("【卖家后台管理】开始分页查询小说信息列表");
        }
        try {
            Sort sort = new Sort(Sort.Direction.ASC, "updateTime");
            PageRequest pageRequest = new PageRequest(page - 1, size, sort);
            Page<NovelInfoDTO> novelInfoDTOPage = novelInfoService.findAll(pageRequest);
            map.put("currentPage", page);
            map.put("size", size);
            map.put("novelInfoPage", novelInfoDTOPage);
            return new ModelAndView("novel/list", map);
        } catch (Exception e) {
        	map.put("url", "#");
        	map.put("message", ResultEnum.NOVEL_INFO_LIST_ERROR.getMessage());
            log.error("【卖家后台管理】分页查询小说信息列表出现错误，e={}", e);
            return new ModelAndView("common/error", map);
        } finally {
        	if (log.isDebugEnabled()) {
                log.debug("【卖家后台管理】结束分页查询小说信息列表");
            }
		}
    }

    /**
     * <pre>
     * 修改或者请求到添加小说页面
     * @param id
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/index")
    public ModelAndView index(@RequestParam(name = "id", required = false) String id,
                              @RequestParam(name = "bookInfo", required = false) NovelInfoDTO bookInfo,
                              Map<String, Object> map) {
        findCategoryList(map);
        if (log.isDebugEnabled()) {
            log.debug("【卖家后台管理】开始根据小说id={}查询小说信息", id);
        }
        try {
         if (id != null) {
        	bookInfo = novelInfoService.find(id);
        	bookInfo.setCover(new StringBuffer()
                    .append(projectUrlProperties.getResUrl()).append(File.separator)
                    .append("epub2").append(File.separator)
                    .append(bookInfo.getCategoryText()).append(File.separator)
                    .append(bookInfo.getFileName()).append(File.separator)
                    .append(bookInfo.getCover()).toString().trim());
         }
         map.put("bookInfo", bookInfo);
         return new ModelAndView("novel/index", map);
        } catch (Exception e) {
            log.error("【卖家后台管理】查询根据小说id={}查询小说信息出现错误，e={}", id, e);
            map.put("msg", ResultEnum.CATEGORY_PARENT_ERROR.getMessage());
            map.put("url", "#");
            return new ModelAndView("common/error");
        } finally {
        	if (log.isDebugEnabled()) {
                log.debug("【卖家后台管理】结束根据小说id={}查询小说信息", id);
            }
		}
    }

    /**
     * <pre>
     * 保存小说信息
     * @param novelInfoForm
     * @param bindingResult
     * @param map
     * @return
     * </pre>
     */
    @PostMapping(value = "/save")
    public ModelAndView save(@Valid NovelInfoForm novelInfoForm, BindingResult bindingResult, Map<String, Object> map) {
        return null;
    }

    /**
     * <pre>
     * 删除小说信息
     * @param id
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/delete")
    public ModelAndView delete(@RequestParam(name = "id", required = true, defaultValue = "") Integer id, Map<String, Object> map) {
        return null;
    }

    /**
     * <pre>
     * 上传小说文件并解析
     * @param file
     * @param map
     * @return
     * </pre>
     */
    @PostMapping(value = "/upload")
    public ModelAndView upload(@RequestParam(value = "file", required = true) MultipartFile file,
                               @RequestParam(value = "categoryEnName", required = true) String categoryEnName,
                               @RequestParam(value = "novelStatus", required = true) Integer novelStatus,
                               Map<String, Object> map) {
    	if (log.isDebugEnabled()) {
			log.debug("【卖家后台管理】开始上传小说文件并解析");
		}
        try {
        	if (file.isEmpty()) {
        		map.put("url", "#");
        		map.put("message", "Please select a novel file to upload");
                return new ModelAndView("common/error");
            }
            findCategoryList(map);
            map.put("categoryEnName", categoryEnName);
            NovelInfoDTO novelInfoDTO = saveFile(file, categoryEnName, novelStatus);
            map.put("bookInfo", novelInfoDTO);
            return new ModelAndView("novel/index", map);
		} catch (Exception e) {
			map.put("url", "#");
    		map.put("message", "Upload a novel file has exception");
			return new ModelAndView("common/error", map);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【卖家后台管理】结束上传小说文件并解析");
			}
		}
    }

    /**
     * 保存文件到服务器并解析出来小说的信息
     * @param file
     * @param categoryEnName
     * @return
     */
    private NovelInfoDTO saveFile(MultipartFile file, String categoryEnName, Integer novelStatus){
    	if (log.isDebugEnabled()) {
			log.debug("【卖家后台管理】开始解析并上传小说电子书到服务器");
		}
        try {
            MediaType[] lazyTypes = {
                    MediatypeService.CSS,
                    MediatypeService.GIF,
                    MediatypeService.JPG,
                    MediatypeService.PNG,
                    MediatypeService.MP3,
                    MediatypeService.MP4,
                    MediatypeService.OGG
            };
            Book book = new EpubReader().readEpubLazy(FileUtil.toZipFile(file), "UTF-8", Arrays.asList(lazyTypes));
            NovelInfoDTO novelInfoDTO = null;
            if (book!= null) {
            	novelInfoDTO = NovelInfoDTO.builder().build();
            	String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf("."));
                String coverImageFilePath = new StringBuffer()
                        .append(projectUrlProperties.getCoverImgFilePath()).append(File.separator)
                        .append(categoryEnName).append(File.separator)
                        .append(fileName).append(File.separator).toString().trim();
                byte[] coverData = null;
                if (book.getCoverImage() != null) {
                	coverData = book.getCoverImage().getData();
				}
                FileUtil.save(coverImageFilePath, "cover.jpg", coverData);
                String coverPath = "cover.jpg";
                Guide guide = book.getGuide();
                Metadata metadata = book.getMetadata();
                Resource ncxResource = book.getNcxResource();
                Resource opfResource = book.getOpfResource();
                Resources resources = book.getResources();
                Spine spine = book.getSpine();
                TableOfContents tableOfContents = book.getTableOfContents();
                List<Resource> contents = book.getContents();
                //获取小说的文件名称
                novelInfoDTO.setFileName(fileName);//fileName：小说文件名称，不能为空
                //获取小说的封面图片
                novelInfoDTO.setCover(coverPath);//cover：小说封面图片路径，不能为空
                //获取小说的书名
                String title = book.getTitle();
                novelInfoDTO.setTitle(title);//title：小说的名字，不能为空
                //获取小说作者的
                List<String> authorNameList = new ArrayList<>();
                for (Author author : metadata.getAuthors()) {
                    String firstName = author.getFirstname();
                    String lastName = author.getLastname();
                    authorNameList.add(new StringBuffer().append(lastName).append(firstName).toString().trim());
                }
                novelInfoDTO.setAuthor(authorNameList.get(0));//author：小说的作者，可以为空
                
                //获取小说的出版社
                String publishName = metadata.getPublishers().get(0);
                novelInfoDTO.setPublisher(publishName);//publisher：小说出版社，可以为空
                
                //获取小说的书的id
                String bookId = fileName;
                novelInfoDTO.setBookId(bookId);//bookId：小说书的id，不能为空
                
                //获取小说的状态
                novelInfoDTO.setNovelStatus(novelStatus);//novelStatus：小说的状态，不能为空

                CategoryDTO categoryDTO = categoryService.findByCategoryEnName(categoryEnName);

                //获取小说的类型type
                novelInfoDTO.setCategoryType(categoryDTO.getCategoryType());//categoryType：小说的类型type，不能为空
                
                //获取小说的类目名称
                novelInfoDTO.setCategoryText(categoryEnName);//categoryText：小说的类型type，不能为空
                
                //获取小说的语言
                String language = metadata.getLanguage();
                novelInfoDTO.setLang(language);//language：小说的语言，不能为空

                //获取小说的rootFile
                String rootFile = categoryEnName.concat(File.separator).concat(fileName).concat(File.separator).concat("content.opf").trim();
                novelInfoDTO.setRootFile(rootFile);//rootFile：小说的rootFile，可以为空

                //获取小说的简介
                String desc = metadata.getDescriptions().toString().trim();
                novelInfoDTO.setIntroduction(desc);//introduction：小说的简介，可以为空

                //获取小说的type
                novelInfoDTO.setType(categoryDTO.getCategoryType());//type：小说的type
                
                //获取小说的创建时间
                Date publishDate = metadata.getDates().get(0);
                novelInfoDTO.setCreateTime(DateUtil.dateFormat("yyyy年MM月dd日 HH:mm:ss", publishDate.getValue()));

                String filePath = projectUrlProperties.getUploadFilePath().concat(File.separator)
                        .concat("epub").concat(File.separator)
                        .concat(categoryEnName).concat(File.separator)
                        .concat(file.getOriginalFilename());
                byte[] bytes = file.getBytes();
                Path path = Paths.get(filePath);
                Files.write(path, bytes);
                if (log.isDebugEnabled()) {
    				log.debug("title={}, metadata={}, fileName={}, filePath={}", title, metadata, fileName, filePath);
    			}
			}
            NovelInfoDTO infoDTOTmp = novelInfoService.save(novelInfoDTO);
            return infoDTOTmp;
        } catch (Exception e) {
            log.error("【卖家后台管理】解析小说电子书信息出现错误，e={}", e);
            return null;
        } finally {
        	if (log.isDebugEnabled()) {
    			log.debug("【卖家后台管理】结束解析并上传小说电子书到服务器");
    		}
		}
    }

    private ModelAndView findCategoryList(Map<String, Object> map) {
        try {
        	if (log.isDebugEnabled()) {
                log.debug("【卖家后台管理】开始查询小说类目信息");
            }
            List<CategoryDTO> categoryDTOList = categoryService.findAllParentCategoryIdIsNotNull();
            map.put("categoryList", categoryDTOList);
        } catch (Exception e) {
            log.error("【卖家后台管理】查询小说类目信息出现错误，e={}", e);
            map.put("msg", ResultEnum.CATEGORY_PARENT_ERROR.getMessage());
            map.put("url", "#");
            return new ModelAndView("common/error");
        } finally {
        	if (log.isDebugEnabled()) {
                log.debug("【卖家后台管理】结束查询小说类目信息");
            }
		}
        return null;
    }
}
