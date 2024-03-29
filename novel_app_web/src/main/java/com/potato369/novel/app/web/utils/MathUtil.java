
/**
 * Project Name:sell.<br/> 
 * File Name:MathUtil.java.<br/> 
 * Package Name:com.imooc.sell.utils.<br/> 
 * Date:2017年12月14日下午6:10:32.<br/> 
 * Copyright (c) 2017, 版权所有 (C) 2016-2036  土豆互联科技(深圳)有限公司 www.potato369.com All Rights Reserved.<br/> 
 */
package com.potato369.novel.app.web.utils;

import java.math.BigDecimal;

/**
 * <pre>
 * ClassName:MathUtil
 * Function:数字比较工具类
 * Reason:
 * Date:2017年12月14日 下午6:10:32
 * Desc:
 * @author 王艳军
 * @version  
 * @since JDK 1.6
 * </pre>
 */

public class MathUtil {

  private static final Double MONEY_RANG = 0.01;
  
  /**
   * <pre>
   * 比较两个金额是否相等
   * @param double1 金额1
   * @param double2 金额2
   * @return Boolean
   * </pre>
   */
	public static synchronized Boolean equals(Double double1, Double double2) {
		Double result = Math.abs(double1 - double2);
		if (result < MONEY_RANG) {
			return true;
		} else {
			return false;
		}
	}
  	/**
   * <pre>
   * 比较两个金额大小，如果double1 >= double2，返回true，否则返回false。
   * @param double1 金额1
   * @param double2 金额2
   * @return Boolean
   * </pre>
   */
	public static synchronized Boolean compareTo(Double double1, Double double2) {
		Double result = Math.abs(double1 - double2);
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * <pre>
	 * getRandom方法的作用：产生随机红包余额
	 * @param rang
	 * @return
	 * </pre>
	 */
	public static synchronized Double getRandom(int rang) {
		return new BigDecimal((Math.random() * rang + 1) / 10).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static void main(String[] args) {
		System.out.println(MathUtil.compareTo(0.01, 0.01));
		System.out.println(MathUtil.getRandom(4));
	}
}
