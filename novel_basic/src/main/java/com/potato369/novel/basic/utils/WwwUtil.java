package com.potato369.novel.basic.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * @PackageName com.potato369.novel.utils
 * @ClassName WwwUtil
 * @Desc 网站相关工具类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 18:11
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Slf4j
public class WwwUtil {

	/**
	 * <pre>
	 * getIpAddr1方法的作用：
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param request
	 * @return String ip
	 * @since JDK 1.6
	 * </pre>
	 */
	public static String getIpAddr1(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * <pre>
	 * getIpAddr2方法的作用：从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip。
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param request
	 * @return String ip
	 * @since JDK 1.6
	 * </pre>
	 */
	public static String getIpAddr2(HttpServletRequest request) {
		String remoteAddr = request.getRemoteAddr();
		String forwarded = request.getHeader("X-Forwarded-For");
		String realIp = request.getHeader("X-Real-IP");
		String ip = null;
		if (realIp == null) {
			if (forwarded == null) {
				ip = remoteAddr;
			} else {
				ip = remoteAddr + "/" + forwarded.split(",")[0];
			}
		} else {
			if (realIp.equals(forwarded)) {
				ip = realIp;
			} else {
				if (forwarded != null) {
					forwarded = forwarded.split(",")[0];
				}
				ip = realIp + "/" + forwarded;
			}
		}
		return ip;
	}
	/**
	 * <pre>
	 * getIpAddr3方法的作用：
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param request
	 * @return String ip
	 * @since JDK 1.6
	 * </pre>
	 */
	public static String getIpAddr3(HttpServletRequest request) {
		String remoteAddr = request.getRemoteAddr();
		String forwarded = request.getHeader("X-Forwarded-For");
		String realIp = request.getHeader("X-Real-IP");
		String ip = null;
		if (realIp == null) {
			if (forwarded == null) {
				ip = remoteAddr;
			} else {
				ip = remoteAddr + "/" + forwarded;
			}
		} else {
			if (realIp.equals(forwarded)) {
				ip = realIp;
			} else {
				ip = realIp + "/" + forwarded.replaceAll(", " + realIp, "");
			}
		}
		return ip;
	}
	/**
	 * <pre>
	 * getIpAddr4方法的作用：
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param request
	 * @return String ip
	 * @since JDK 1.6
	 * </pre>
	 */
	public static String getIpAddr4(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	/**
	 * <pre>
	 * getIpAddr5方法的作用：
	 * 获取访问者IP 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 如果还不存在则调用Request.getRemoteAddr()。
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param request
	 * @return String ip
	 * @since JDK 1.6
	 * </pre>
	 */
	public static String getIpAddr5(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
	/**
	 * <pre>
	 * doPost方法的作用：发送post请求
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param url
	 * @param header
	 * @param body
	 * @return String JSON
	 * @since JDK 1.6
	 * </pre>
	 */
	public static String doPost(String url, Map<String, String> header, String body) {
		String result = "";
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			// 设置 url
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			// 设置 header
			for (String key : header.keySet()) {
				connection.setRequestProperty(key, header.get(key));
			}
			// 设置请求 body
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new PrintWriter(connection.getOutputStream());
			// 保存body
			out.print(body);
			// 发送body
			out.flush();
			// 获取响应body
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("出现异常Exception", e);
			return null;
		}
		return result;
	}
	/**
	 * 
	 * <pre>
	 * doGet方法的作用：发送get请求
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param url
	 * @param header
	 * @return String JSON
	 * @since JDK 1.6
	 * </pre>
	 */
	public static String doGet(String url, Map<String, String> header) {
		String result = "";
		BufferedReader in = null;
		try {
			// 设置 url
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			// 设置 header
			for (String key : header.keySet()) {
				connection.setRequestProperty(key, header.get(key));
			}
			// 设置请求 body
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("出现异常Exception", e);
			return null;
		}
		return result;
	}
	/**
	 * <pre>
	 * doMultiPost方法的作用：发送post请求,根据Content-Type分别返回不同的返回值
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param url
	 * @param header
	 * @param body
	 * @return Map<String, Object> map
	 * @since JDK 1.6
	 * </pre>
	 */
	public static Map<String, Object> doMultiPost(String url, Map<String, String> header, String body) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PrintWriter out = null;
		try {
			// 设置 url
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			// 设置 header
			for (String key : header.keySet()) {
				connection.setRequestProperty(key, header.get(key));
			}
			// 设置请求 body
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new PrintWriter(connection.getOutputStream());
			// 保存body
			out.print(body);
			// 发送body
			out.flush();
			// 获取响应header
			String responseContentType = connection.getHeaderField("Content-Type");
			if ("audio/mpeg".equals(responseContentType)) {
				// 获取响应body
				byte[] bytes = toByteArray(connection.getInputStream());
				resultMap.put("Content-Type", "audio/mpeg");
				resultMap.put("sid", connection.getHeaderField("sid"));
				resultMap.put("body", bytes);
				return resultMap;
			} else {
				// 设置请求 body
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				String result = "";
				while ((line = in.readLine()) != null) {
					result += line;
				}
				resultMap.put("Content-Type", "text/plain");
				resultMap.put("body", result);
				return resultMap;
			}
		} catch (Exception e) {
			log.error("出现异常Exception", e);
			return null;
		}
	}

	/**
	 * <pre>
	 * toByteArray方法的作用：
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param in
	 * @return byte[]
	 * @throws IOException
	 * @since JDK 1.6
	 * </pre>
	 */
	public static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}
}
