package com.it.j2ee.modules.webservice.cxf.interceptor;
//package ibm.gdmcc.scm.wsm.interceptor;
//
//import ibm.gdmcc.scm.ccloud.service.AccessTokenService;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import javax.xml.bind.JAXBException;
//import javax.xml.namespace.QName;
//
//import org.apache.cxf.binding.soap.SoapMessage;
//import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
//import org.apache.cxf.headers.Header;
//import org.apache.cxf.interceptor.Fault;
//import org.apache.cxf.jaxb.JAXBDataBinding;
//import org.apache.cxf.phase.Phase;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.cmcc.schema.ecity.sendmessage.v1_0.local.request.MsgHead;
//
///**
// * 
// * @author Liusu
// * 推送消息时，设置报文头信息
// *
// */
//@Component
//public class SendMsgInInterceptor extends AbstractSoapInterceptor {
//	
//	@Value("${ccloud.sendMsg.ecserpId}")
//	private String ecserpId;
//	
//	@Value("${ccloud.sendMsg.siappId}")
//	private String siappId;
//	
//	@Value("${ccloud.sendMsg.functionId}")
//	private String functionId;
//	
//	@Value("${ccloud.sendMsg.msgVersion}")
//	private String msgVersion;
//	
//	@Autowired
//	private AccessTokenService accessTokenService;
//
//	public SendMsgInInterceptor() {
//		super(Phase.WRITE);
//	}
//
//	public void handleMessage(SoapMessage message) throws Fault {
//		List<Header> headers=message.getHeaders(); 
//        MsgHead headValue=new MsgHead();
//        headValue.setMsgversion(msgVersion);
//        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        headValue.setAccesstoken(accessTokenService.getToken(time));
//        headValue.setReqtimestamp(time);
//        headValue.setRcvtimestamp(null);
//        headValue.setEcserpid(ecserpId);
//        headValue.setSiappid(siappId);
//        headValue.setFunctionid(functionId);
//        Header resMsgHeader = null;
//		try {
//			resMsgHeader = new Header(new QName("res", "msghead"), headValue, new JAXBDataBinding(MsgHead.class));
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
//        headers.add(resMsgHeader);
//		
//	}
//
//}
