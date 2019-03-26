package com.potato369.novel.controller;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.potato369.novel.conf.prop.ProjectUrlProperties;
import com.potato369.novel.conf.prop.XFYunProperties;
import com.potato369.novel.utils.FileUtil;
import com.potato369.novel.utils.WwwUtil;
import com.potato369.novel.vo.VoiceDataVO;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * @PackageName com.potato369.novel.controller
 * @ClassName VoiceController
 * @Desc 科大讯飞语音合成Controller
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/24 18:17
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/voice")
public class BuyerVoiceController {

    @Autowired
    private XFYunProperties xfYunProperties;

    @Autowired
    private ProjectUrlProperties projectUrlProperties;

    @GetMapping
    public synchronized VoiceDataVO voice(@RequestParam(name = "text", required = false, defaultValue = "")String text,
                                          @RequestParam(name = "lang", required = false, defaultValue = "zh-cn")String lang)  {
        if (log.isDebugEnabled()) {
            log.debug("text={}, lang={}", text, lang);
        }
        VoiceDataVO voiceDataVO = new VoiceDataVO();
        String engineType = xfYunProperties.getEngineType();
        if (StringUtils.isEmpty(text)) {
            text = "山上五棵树，架上五壶醋，林中五只鹿，箱里五条裤。伐了山上树，搬下架上的醋，射死林中的鹿，取出箱中的裤。";
        }
        if (StringUtils.isEmpty(lang)) {
            lang = "zh-cn";
        } else {
            if (lang.toLowerCase().equalsIgnoreCase("en")) {
                engineType = "intp65_en";
            }
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Map<String, String> header = null;
        try {
            header = getHeader(xfYunProperties.getAuf(), xfYunProperties.getAue(), xfYunProperties.getVoiceName(), xfYunProperties.getSpeed(), xfYunProperties.getVolume(), engineType, xfYunProperties.getTextType(), xfYunProperties.getPitch(), request);
            if (log.isDebugEnabled()) {
                log.debug("header={}", header);
            }
            final String WEBTTS_URL = new StringBuffer("http://").append(xfYunProperties.getHost()).append(xfYunProperties.getPath()).toString().trim();
            if (log.isDebugEnabled()) {
                log.debug("WEBTTS_URL={}", WEBTTS_URL);
            }
            Map<String, Object> resultMap = WwwUtil.doMultiPost(WEBTTS_URL, header, "text=" + text);
            if (log.isDebugEnabled()) {
                log.debug("resultMap={}", resultMap);
            }
            // 合成成功
            if ("audio/mpeg".equals(resultMap.get("Content-Type"))) {
                String fileName = String.valueOf(new Date().getTime()) + ".mp3";
                FileUtil.save(projectUrlProperties.getMp3FilePath(), fileName, (byte[]) resultMap.get("body"));
                if (log.isDebugEnabled()) {
                    log.debug("sid={}", resultMap.get("sid"));
                }
                voiceDataVO.setErr_no(0);
                voiceDataVO.setMsg("合成成功");
                voiceDataVO.setPath(new StringBuffer("").append(projectUrlProperties.getResUrl()).append("/mp3/").append(fileName).toString().trim());
                return voiceDataVO;
            } else { // 合成失败
                voiceDataVO.setErr_no(1);
                voiceDataVO.setMsg("合成失败");
                voiceDataVO.setPath(null);
                if (log.isDebugEnabled()) {
                    log.debug("body={}", resultMap.get("body").toString());
                }
                log.error("合成失败, body={}", resultMap.get("body").toString());
                return voiceDataVO;
            }
        } catch (Exception e) {
            voiceDataVO.setErr_no(1);
            voiceDataVO.setMsg("合成失败");
            voiceDataVO.setPath(null);
            log.error("合成失败", e);
            return voiceDataVO;
        }
    }

    /**
     * 组装http请求头
     *
     * @param aue
     * @param voiceName
     * @param speed
     * @param volume
     * @param engineType
     * @param textType
     * @param pitch
     * @param volume
     * @param request
     * @return map
     * @throws UnsupportedEncodingException
     */
    private Map<String, String> getHeader(String auf, String aue, String voiceName, String speed, String volume, String engineType, String textType, String pitch, HttpServletRequest request) throws UnsupportedEncodingException {
        String curTime = System.currentTimeMillis() / 1000L + "";
        String param = "{\"auf\":\"" + auf + "\"";
        if (!StringUtil.isEmpty(aue)) {
            param += ",\"aue\":\"" + aue + "\"";
        }
        if (!StringUtils.isEmpty(voiceName)) {
            param += ",\"voice_name\":\"" + voiceName + "\"";
        }
        if (!StringUtil.isEmpty(speed)) {
            param += ",\"speed\":\"" + speed + "\"";
        }
        if (!StringUtil.isEmpty(volume)) {
            param += ",\"volume\":\"" + volume + "\"";
        }
        if (!StringUtil.isEmpty(pitch)) {
            param += ",\"pitch\":\"" + pitch + "\"";
        }
        if (!StringUtil.isEmpty(engineType)) {
            param += ",\"engine_type\":\"" + engineType + "\"";
        }
        if (!StringUtil.isEmpty(textType)) {
            param += ",\"text_type\":\"" + textType + "\"";
        }
        param += "}";
        if (log.isDebugEnabled()) {
            log.debug("param={}", param);
        }
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
        String checkSum = DigestUtils.md5Hex(xfYunProperties.getApiKey() + curTime + paramBase64);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", paramBase64);
        header.put("X-CurTime", curTime);
        header.put("X-CheckSum", checkSum);
        header.put("X-Real-Ip", WwwUtil.getIpAddr4(request));
        header.put("X-Appid", xfYunProperties.getAppId());
        if (log.isDebugEnabled()) {
            log.debug("header={}", header);
        }
        return header;
    }
}
