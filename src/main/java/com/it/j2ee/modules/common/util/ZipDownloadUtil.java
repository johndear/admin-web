package com.it.j2ee.modules.common.util;
//package com.it.j2ee.modules.common.utils;
//
//import ibm.gdmcc.scm.base.service.AttachLocationService;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.io.FileUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class ZipDownloadUtil {
//	Logger logger = LoggerFactory.getLogger(ZipDownloadUtil.class);
//	private  static ZipDownloadUtil fileDownloadUtil;
//	public ZipDownloadUtil() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public void init()
//	{
//		fileDownloadUtil = this;
//		fileDownloadUtil.attachLocationService = this.attachLocationService;
//	}
//	private AttachLocationService attachLocationService;
//
//	public AttachLocationService getAttachLocationService() {
//		return attachLocationService;
//	}
//
//	public void setAttachLocationService(AttachLocationService attachLocationService) {
//		this.attachLocationService = attachLocationService;
//	}
//	/**
//	 * 将指定的文件列表在附件上传目录的temp文件夹生成压缩包
//	 * @param zipFileName 生成的压缩包的名称
//	 * @param files	要压缩的文件列表
//	 * @return
//	 */
//	public static Map<String,String> zipFile(String zipFileName,List<String> files){
//		Map<String,String> res = new HashMap<String,String>();
//		String zipFilePath = getZipPath(zipFileName);
//		File commonAttachDir = createDir(zipFilePath);
//		String attachDir = commonAttachDir.getAbsolutePath();
//		try {
//			FileUtils.forceMkdir(commonAttachDir);
//			for(String file:files){
//				FileUtils.copyFileToDirectory(new File(file), commonAttachDir, true);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try{
//			ZipUtil.zip(attachDir,attachDir+".zip");
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		res.put("fileName", zipFilePath+".zip");
//		res.put("filePath", attachDir+".zip");
//		return res;
//	}
//
//	/**
//	 * 将指定的文件列表在附件上传目录的temp文件夹生成压缩包
//	 * @param zipFileName 生成的压缩包的名称
//	 * @param files	要压缩的文件列表
//	 * @return
//	 */
//	public static Map<String,String> zipNewFile(String zipFileName,List<Map<String,String>> files){
//		Map<String,String> res = new HashMap<String,String>();
//		String zipFilePath = getZipPath(zipFileName);
//		File commonAttachDir = createDir(zipFilePath);
//		String attachDir = commonAttachDir.getAbsolutePath();
//		try {
//			FileUtils.forceMkdir(commonAttachDir);
//			for(Map<String,String> file:files){
//				FileUtils.copyFile(new File(file.get("filePath")),new File(attachDir +File.separator+file.get("newName")));
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try{
//			ZipUtil.zip(attachDir,attachDir+".zip");
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		res.put("fileName", zipFilePath+".zip");
//		res.put("filePath", attachDir+".zip");
//		return res;
//	}
//
//
//
//	/**使用流下载文件
//	 * @param downloadFile 要下载的文件对象
//	 * @param fileName	下载时显示的文件名称
//	 * @param response	HttpServletResponse
//	 */
//	public static void downloadFile(String zipFileName,List<String> files,HttpServletResponse response){
//		Map<String,String> res = ZipDownloadUtil.zipFile(zipFileName,files);
//		File downloadFile = new File(res.get("filePath"));
//		String fileName = res.get("fileName");
//		HttpUtils.downloadFile(downloadFile, fileName, response);
//	}
//	private static File createDir(String zipFilePath){
//		String location = fileDownloadUtil.attachLocationService.getLocation();
//		String temp = location+File.separator+"temp";
//		try {
//			FileUtils.forceMkdir(new File(temp));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		String attachDir = temp + File.separatorChar +zipFilePath;
//		File commonAttachDir = new File(attachDir);
//		if(!commonAttachDir.exists()){
//			try {
//				FileUtils.forceMkdir(commonAttachDir);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}	
//		return commonAttachDir;
//	}
//
//	private static  String getZipPath(String zipFileName){
//		SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyymmddhhmmss");
//		String zipFilePath = ymdFormat.format(new Date()) + File.separatorChar + zipFileName;
//		return zipFilePath;
//	}
//}
