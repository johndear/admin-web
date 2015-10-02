package com.it.j2ee.modules.webservice.cxf.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.it.j2ee.modules.common.service.SpringBeanLocator;
import com.it.j2ee.modules.webservice.cxf.entity.WsInterfaceLog;
import com.it.j2ee.modules.webservice.cxf.service.WsInterfaceLogService;

public class ExceptionUtil {
	
    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);
    
	private static WsInterfaceLogService  wsInterfaceLogService =  (WsInterfaceLogService)SpringBeanLocator
			.getService("wsInterfaceLogService");
	
	private static String getExceptionString(Throwable ex){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(out);
		ex.printStackTrace(pout);
		String ret = new String(out.toByteArray());
		pout.close();
		try {
			out.close();
		} catch (Exception e) {
			
		}
		
		return ret;
	}
	
	public static void saveException(String name,Throwable ex ){
		logger.error(name, ex);
		WsInterfaceLog log = new WsInterfaceLog();
		log.setContent(getExceptionString(ex));
		log.setCreateTime(new Date());
		log.setName(name);
		wsInterfaceLogService.save(log);
		
	}
}
