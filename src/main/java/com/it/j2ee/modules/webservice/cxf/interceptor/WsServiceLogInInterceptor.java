package com.it.j2ee.modules.webservice.cxf.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.Date;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Message;

import com.it.j2ee.modules.common.service.SpringBeanLocator;
import com.it.j2ee.modules.webservice.cxf.entity.WsInterfaceMessage;
import com.it.j2ee.modules.webservice.cxf.service.WsInterfaceMessageService;

public class WsServiceLogInInterceptor extends AbstractLoggingInterceptor {
	
	private WsInterfaceMessageService wsInterfaceMessageService;
	
	protected int limit = 2 * 1024 * 1024;

	public void handleMessage(Message message) throws Fault {
		
		wsInterfaceMessageService =  (WsInterfaceMessageService)SpringBeanLocator
				.getService("wsInterfaceMessageService");
		    
		 if (message.containsKey(LoggingMessage.ID_KEY))
		      return;

		    String id = (String)message.getExchange().get(LoggingMessage.ID_KEY);
		    if (id == null) {
		      id = LoggingMessage.nextId();
		      message.getExchange().put(LoggingMessage.ID_KEY, id);
		    }
		    message.put(LoggingMessage.ID_KEY, id);
		    LoggingMessage buffer = new LoggingMessage("Inbound Message\n----------------------------", id);

		    if (!(Boolean.TRUE.equals(message.get("decoupled.channel.message"))))
		    {
		      Integer responseCode = (Integer)message.get(Message.RESPONSE_CODE);
		      if (responseCode != null)
		        buffer.getResponseCode().append(responseCode);

		    }

		    String encoding = (String)message.get(Message.ENCODING);

		    if (encoding != null)
		      buffer.getEncoding().append(encoding);

		    String httpMethod = (String)message.get("org.apache.cxf.request.method");
		    if (httpMethod != null)
		      buffer.getHttpMethod().append(httpMethod);

		    String ct = (String)message.get("Content-Type");
		    if (ct != null)
		      buffer.getContentType().append(ct);

		  
		    InputStream is = (InputStream)message.getContent(InputStream.class);
		    if (is != null) {
		      logInputStream(message, is, buffer, encoding, ct);
		    }
		    
		    String interfaceName = null;
			QName interFace = (QName) message.getContextualProperty(message.WSDL_INTERFACE);
			if(interFace!=null)	
				interfaceName = interFace.getLocalPart();
			
			String method = null;
			QName operation = (QName) message.getContextualProperty(message.WSDL_OPERATION);
			if(operation!=null)	
				method = operation.getLocalPart();
			
			id = saveMsg(buffer.getPayload().toString(),interfaceName,method );
			message.getExchange().put(LoggingMessage.ID_KEY, id );
		   
		  }
	

	protected void logInputStream(Message message, InputStream is,
			LoggingMessage buffer, String encoding, String ct) {
		CachedOutputStream bos = new CachedOutputStream();
		if (this.threshold > 0L)
			bos.setThreshold(this.threshold);

		try {
			InputStream bis = (is instanceof DelegatingInputStream) ? ((DelegatingInputStream) is)
					.getInputStream() : is;

			this.copyAtLeast(bis, bos, (this.limit == -1) ? 2147483647
					: this.limit);

			bos.flush();
			bis = new SequenceInputStream(bos.getInputStream(), bis);

			if (is instanceof DelegatingInputStream)
				((DelegatingInputStream) is).setInputStream(bis);
			else {
				message.setContent(InputStream.class, bis);
			}

			
			bos.writeCacheTo(buffer.getPayload(), encoding, this.limit);
			
			bos.close();
		} catch (Exception e) {
			throw new Fault(e);
		}
	}
	

	public static void copyAtLeast(InputStream input, OutputStream output,
			int atLeast) throws IOException {
		byte[] buffer = new byte[4096];
		int n = (atLeast > buffer.length) ? buffer.length : atLeast;
		n = input.read(buffer, 0, n);
		while (-1 != n) {
			if (n == 0)
				throw new IOException(
						"0 bytes read in violation of InputStream.read(byte[])");

			output.write(buffer, 0, n);
			atLeast -= n;
			if (atLeast <= 0)
				return;

			n = (atLeast > buffer.length) ? buffer.length : atLeast;
			n = input.read(buffer, 0, n);
		}
	}
	
	  protected String formatLoggingMessage(LoggingMessage loggingMessage)
	  {
	    return loggingMessage.toString();
	  }

	@Override
	protected Logger getLogger() {
		return null;
	}

	public WsServiceLogInInterceptor() {
		super("receive");
	}

	public WsServiceLogInInterceptor(String phase) {
		super(phase);
	}
	
	private String saveMsg(String msg,String intf,String method){
		WsInterfaceMessage wsMsg =  new WsInterfaceMessage();
		//wsMsg.setMessageId(UUID.randomUUID().toString());
		wsMsg.setCreated(new Date());
		wsMsg.setCreatedBy("ws");
		wsMsg.setRequestMsg(msg);
		wsMsg.setMsgInterface(intf);
		wsMsg.setMsgMethod(method);
		wsInterfaceMessageService.create(wsMsg);
		
		return wsMsg.getMessageId();
	}
}
