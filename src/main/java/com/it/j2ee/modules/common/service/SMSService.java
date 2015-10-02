//package com.it.j2ee.modules.common.service;
//
//import java.net.URL;
//import java.rmi.RemoteException;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//
//import com.it.j2ee.core.webservices.entity.Endpoint;
//import com.it.j2ee.core.webservices.util.CommonCacheUtil;
//import com.it.j2ee.modules.common.exception.MessageSendException;
//
//import freemarker.template.Template;
//
///**
// * 发送短信服务
// * Created by leihong on 2014/12/1.
// */
//public class SMSService {
//
//	private Logger logger = LoggerFactory.getLogger(SMSService.class);
//
//	private String username;
//
//	private String password;
//
//	private final static String SMSSERVER="SMSServer";
//
//	@Autowired
//	CommonCacheUtil commonCacheUtil;
//	  
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	@Autowired
//	private FreeMarkerConfigurer freeMarkerConfigurer;
//
//	private SMSServerSoapBindingStub getServer() {
//		SMSServerSoapBindingStub serverStub = null;
//		try {
//			Endpoint endpoint=commonCacheUtil.getEndpointMap().get(SMSSERVER);
//			URL portAddress=new URL(endpoint.getAddress());
//			serverStub = (SMSServerSoapBindingStub) (new SMSServer_ServiceLocator())
//					.getSMSServer(portAddress);
//			serverStub.setTimeout(60000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return serverStub;
//	}
//	
//    /**
//     * 根据模板发送短信
//     * @param to 手机号码数组
//     * @param templateName 模板文件，以".ftl"结束
//     * @param pram 模板文件中对应的参数值
//     */
//	 public void sendSMS(String[] to,String templateName,Map<String,String> pram) {
//
//		String content = "";
//		try {
//			Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(
//					templateName);
//			if (tpl == null) {
//				logger.error("找不到模板文件:" + templateName);
//				throw new MessageSendException("根据模板名称:" + templateName
//						+ "找不到模板文件");
//			}
//
//			content = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,
//					pram);
//		} catch (Exception e) {
//			logger.error("发送短信出错:" + e);
//			throw new MessageSendException("发送短信出错!" + e);
//		}
//		this.send(to, content);
//    }
//    
//	 /**
//	  * 自定义短信内容发送
//	  * @param to 手机号码数组
//	  * @param content 短信内容
//	  */
//	 public void sendSMS(String[] to,String content) throws MessageSendException{
//		 this.send(to, content);
//	 }
//	 
//	private void send(String[] to, String content) throws MessageSendException {
//		
//		String receivers = "";
//		SMSServerSoapBindingStub serverStub = this.getServer();
//		try {
//
//			String sessionid = serverStub.login(username, password);
//			if (sessionid == null || sessionid.indexOf(username) == -1) {
//			} else {
//
//				if (to == null || to.length < 1) {
//					return;
//				} else if (to.length > 0 && to.length <= 100) {
//					for (int i = 0; i < to.length - 1; i++) {
//						receivers += to[i] + "#";
//					}
//					receivers += to[to.length - 1];
//
//					serverStub.send(sessionid, content, receivers, "");
//				} else if (to.length > 100) {
//					int c = to.length / 100 + 1;
//					for (int j = 1; j <= c; j++) {
//						int m = j * 100;
//						if (m > to.length) {
//							m = to.length;
//						}
//						for (int i = 0 + (j - 1) * 100; i < m-1; i++) {
//							receivers += to[i] + "#";
//						}
//						receivers += to[m - 1];
//
//						serverStub.send(sessionid, content, receivers, "");
//					}
//				}
//
//				int ret = serverStub.logout(sessionid);
//				if (ret == 0) {
//					serverStub.forceLogout(username, password);
//				}
//			}
//		} catch (RemoteException e) {
//			logger.error("发送短信出错" + e);
//			throw new MessageSendException("发送短信出错!" + e);
//		}
//	}
//}
