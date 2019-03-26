package com.potato369.novel.utils;
import com.potato369.novel.vo.ResultVO;
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
public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setErr_no(0);
        resultVO.setMsg("返回数据成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setErr_no(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
