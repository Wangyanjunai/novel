package com.potato369.novel.app.web.utils;

import com.potato369.novel.app.web.vo.ResultVO;

/**
 * <pre>
 * @PackageName com.potato369.novel.utils
 * @ClassName ResultVOUtil
 * @Desc 返回结果工具类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 19:40
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ResultVOUtil {

	public static ResultVO success(Object object) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(0);
		resultVO.setMsg("返回数据成功");
		resultVO.setData(object);
		return resultVO;
	}

	public static ResultVO success() {
		return success(null);
	}

	public static ResultVO success(Integer code, String msg) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(code);
		resultVO.setMsg(msg);
		return resultVO;
	}

	public static ResultVO error(Object object) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(-1);
		resultVO.setMsg("返回数据失败");
		resultVO.setData(object);
		return resultVO;
	}

	public static ResultVO error() {
		return error(null);
	}

	public static ResultVO error(Integer code, String msg) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(code);
		resultVO.setMsg(msg);
		return resultVO;
	}
}
