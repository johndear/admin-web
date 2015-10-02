package com.it.j2ee.modules.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtils {
	
	/**
	 * 将HttpServletRequest中的参数存放到HashMap中
	 * @param httpservletrequest
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Map<String,String> getParameters(HttpServletRequest httpservletrequest){
		Map<String,String>  par = new HashMap<String,String>();
		Iterator iterator = httpservletrequest.getParameterMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if (entry.getValue() instanceof String[]) {
				par.put(((String)entry.getKey()), decode(((String[]) entry.getValue())[0]));
			} else {
				par.put(((String)entry.getKey()), decode((String)entry.getValue()));
			}
		}
		return par;
	} 
	/**
	 * 将map转换为一个json格式
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String map2Json(HashMap<String,String> par) {
		StringBuffer pars = new StringBuffer();
		pars.append("{");
		Iterator iterator = par.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if (entry.getValue() instanceof String[]) {
				pars.append(entry.getKey().toString().toUpperCase()).append(":'").append(((String[]) entry.getValue())[0]);
				pars.append("',");
			} else {
				if(entry !=null && entry.getKey() != null){
					pars.append(entry.getKey().toString().toUpperCase()).append(":'").append(entry.getValue());
					pars.append("',");
				}
			}
		}
		return pars.substring(0, pars.length() - 1) + "}";
	}

	/**
	 * resonse 输出字符串
	 * @param resonse
	 * @param str
	 */
	public static void outString(HttpServletResponse response,String str) {
		PrintWriter out = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");   
			response.setHeader("Access-Control-Allow-Methods", "POST, GET,OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "x-prototype-version,x-requested-with");
			response.setHeader("Expires", "0");   
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Content-Type", "text/xml; charset=utf-8");
			out = response.getWriter();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
	}

	/**
	 * resonse 输出XML字符串
	 * @param resonse
	 * @param str
	 */
	public static void outXML(HttpServletResponse resonse,String xmlStr) {
		resonse.setContentType("application/xml;charset=UTF-8");
		outString(resonse,xmlStr);
	}

	/**
	 * 去掉传入的String参数的空格,
	 * 并转换为UTF-8格式
	 * @param s
	 * @return
	 */
	public static String decode(String s) {
		String ss = "";
		if (null != s && !"".equals(s)) {
			try {
				ss = java.net.URLDecoder.decode(s.trim(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			return "";
		}
		return ss;
	}
	/**
	 * 获取ServletContext中保存的属性值
	 * @param request
	 * @param key 属性值的名称
	 * @return
	 */
	public static Object getContextAttr(HttpServletRequest request,String key){
		Object attr = request.getSession().getServletContext().getAttribute(key);
		if(attr !=  null){
			return attr;
		}else{
			return "";
		}
	}
	/**
	 * 获取IP地址的方法
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**使用流下载文件
	 * @param downloadFile 要下载的文件对象
	 * @param fileName	下载时显示的文件名称
	 * @param response	HttpServletResponse
	 */
	public static void downloadFile(File downloadFile,String fileName,HttpServletResponse response){
		BufferedInputStream bis = null;
 		BufferedOutputStream bos = null;
 		OutputStream fos = null;
 		InputStream fis = null;
		try {
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" 
			+ new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			fis = new FileInputStream(downloadFile);
	 		bis = new BufferedInputStream(fis);
	 		fos = response.getOutputStream();
	 		bos = new BufferedOutputStream(fos);
	 		int bytesRead = 0;
	 		byte[] buffer = new byte[8192];
	 		while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
	 			bos.write(buffer, 0, bytesRead);
	 		}
	 		bos.flush();
	 		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (fis != null) {
	 			try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 		}
	 		if (bis != null) {
	 			try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 		}
	 		if (fos != null) {
	 			try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 		}
	 		if (bos != null) {
	 			try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 		}
		}
	};
}
