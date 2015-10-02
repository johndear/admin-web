package com.it.j2ee.modules.webservice.cxf.interceptor;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.InterfaceInfo;

import com.it.j2ee.modules.common.service.SpringBeanLocator;
import com.it.j2ee.modules.webservice.cxf.entity.WsInterfaceMessage;
import com.it.j2ee.modules.webservice.cxf.service.WsInterfaceMessageService;

public class WsServiceLogOutInterceptor extends AbstractLoggingInterceptor {
	
	protected int limit = 2 * 1024 * 1024;
	
	private WsInterfaceMessageService wsInterfaceMessageService;

	private static final Logger LOG = LogUtils
			.getLogger(WsServiceLogOutInterceptor.class);
	private static final String LOG_SETUP = WsServiceLogOutInterceptor.class
			.getName() + ".log-setup";

	public WsServiceLogOutInterceptor(String phase) {
		super(phase);
		addBefore(StaxOutInterceptor.class.getName());
	}

	public WsServiceLogOutInterceptor() {
		this("pre-stream");
	}

	public void handleMessage(Message message) throws Fault {
		wsInterfaceMessageService =  (WsInterfaceMessageService)SpringBeanLocator
				.getService("wsInterfaceMessageService");
		
		OutputStream os = (OutputStream) message.getContent(OutputStream.class);
		Writer iowriter = (Writer) message.getContent(Writer.class);
		if ((os == null) && (iowriter == null))
			return;
		
		  String id = (String)message.getExchange().get(LoggingMessage.ID_KEY);
		  
		  if (id == null) {
		      id = LoggingMessage.nextId();
		     
		    }

		Logger logger = getMessageLogger(message);
		if ((logger.isLoggable(Level.INFO)) || (this.writer != null)) {
			boolean hasLogged = message.containsKey(LOG_SETUP);

			message.put(LOG_SETUP, Boolean.TRUE);
			if (os != null) {
				CacheAndWriteOutputStream newOut = new CacheAndWriteOutputStream(
						os);
				if (this.threshold > 0L)
					newOut.setThreshold(this.threshold);

				if (this.limit > 0)
					newOut.setCacheLimit(this.limit);

				message.setContent(OutputStream.class, newOut);
				newOut.registerCallback(new LoggingCallback(logger, message, os));

			}
		}
	}

	private LoggingMessage setupBuffer(Message message) {
		String id = (String) message.getExchange().get(LoggingMessage.ID_KEY);
		if (id == null) {
			id = LoggingMessage.nextId();
			message.getExchange().put(LoggingMessage.ID_KEY, id);
		}
		LoggingMessage buffer = new LoggingMessage(
				"Outbound Message\n---------------------------", id);

		Integer responseCode = (Integer) message.get(Message.RESPONSE_CODE);
		if (responseCode != null) {
			buffer.getResponseCode().append(responseCode);
		}

		String encoding = (String) message.get(Message.ENCODING);
		if (encoding != null)
			buffer.getEncoding().append(encoding);

		String httpMethod = (String) message
				.get("org.apache.cxf.request.method");
		if (httpMethod != null)
			buffer.getHttpMethod().append(httpMethod);

		String address = (String) message.get(Message.ENDPOINT_ADDRESS);
		if (address != null) {
			buffer.getAddress().append(address);
			String uri = (String) message.get("org.apache.cxf.request.uri");
			if ((uri != null) && (!(address.startsWith(uri)))) {
				if ((!(address.endsWith("/"))) && (!(uri.startsWith("/"))))
					buffer.getAddress().append("/");

				buffer.getAddress().append(uri);
			}
		}
		String ct = (String) message.get("Content-Type");
		if (ct != null)
			buffer.getContentType().append(ct);

		Object headers = message.get(Message.PROTOCOL_HEADERS);
		if (headers != null)
			buffer.getHeader().append(headers);

		return buffer;
	}

	protected String formatLoggingMessage(LoggingMessage buffer) {
		return buffer.toString();
	}

	protected Logger getLogger() {
		return LOG;
	}

	class LoggingCallback implements CachedOutputStreamCallback {
		private final Message message;
		private final OutputStream origStream;
		private final Logger logger;
		private final int lim;

		public LoggingCallback(Logger paramLogger, Message paramMessage,
				OutputStream paramOutputStream) {
			this.logger = paramLogger;
			this.message = paramMessage;
			this.origStream = paramOutputStream;
			this.lim = ((WsServiceLogOutInterceptor.this.limit == -1) ? 2147483647
					: WsServiceLogOutInterceptor.this.limit);
		}

		public void onFlush(CachedOutputStream cos) {
		}

		public void onClose(CachedOutputStream cos) {
			LoggingMessage buffer = WsServiceLogOutInterceptor.this
					.setupBuffer(this.message);

			String ct = (String) this.message.get("Content-Type");

			try {
				String encoding = (String) this.message.get(Message.ENCODING);
				WsServiceLogOutInterceptor.this.writePayload(buffer.getPayload(),
						cos, encoding, ct);
			} catch (Exception ex) {
			}
			
			  String id = (String)message.getExchange().get(LoggingMessage.ID_KEY);
			  
			  if (id == null) {
			      id = LoggingMessage.nextId();
			     
			    }
			
			saveMsg(buffer.getPayload().toString(),id);
			//message.getExchange().put(LoggingMessage.ID_KEY, id );
			try {
				cos.lockOutputStream();
				cos.resetOut(null, false);
			} catch (Exception ex) {
			}
			this.message.setContent(OutputStream.class, this.origStream);
		}
	}

	private class LogWriter extends FilterWriter {
		StringWriter out2;
		int count;
		Logger logger;
		Message message;
		final int lim;

		public LogWriter(Logger paramLogger, Message paramMessage,
				Writer paramWriter) {
			super(paramWriter);
			this.logger = paramLogger;
			this.message = paramMessage;
			if (!(paramWriter instanceof StringWriter))
				this.out2 = new StringWriter();

			this.lim = ((WsServiceLogOutInterceptor.this.limit == -1) ? 2147483647
					: WsServiceLogOutInterceptor.this.limit);
		}

		public void write(int c) throws IOException {
			super.write(c);
			if ((this.out2 != null) && (this.count < this.lim))
				this.out2.write(c);

			this.count += 1;
		}

		public void write(char[] cbuf, int off, int len) throws IOException {
			super.write(cbuf, off, len);
			if ((this.out2 != null) && (this.count < this.lim))
				this.out2.write(cbuf, off, len);

			this.count += len;
		}

		public void write(String str, int off, int len) throws IOException {
			super.write(str, off, len);
			if ((this.out2 != null) && (this.count < this.lim))
				this.out2.write(str, off, len);

			this.count += len;
		}

		public void close() throws IOException {
			LoggingMessage buffer = WsServiceLogOutInterceptor.this
					.setupBuffer(this.message);
			if (this.count >= this.lim)
				buffer.getMessage().append(
						"(message truncated to " + this.lim + " bytes)\n");

			StringWriter w2 = this.out2;
			if (w2 == null)
				w2 = (StringWriter) this.out;

			String ct = (String) this.message.get("Content-Type");
			try {
				WsServiceLogOutInterceptor.this.writePayload(buffer.getPayload(),
						w2, ct);
			} catch (Exception ex) {
			}
			WsServiceLogOutInterceptor.this.log(this.logger, buffer.toString());
			this.message.setContent(Writer.class, this.out);
			super.close();
		}
	}

	Logger getMessageLogger(Message message) {
		Endpoint ep = message.getExchange().getEndpoint();
		if ((ep == null) || (ep.getEndpointInfo() == null))
			return getLogger();

		EndpointInfo endpoint = ep.getEndpointInfo();
		if (endpoint.getService() == null)
			return getLogger();

		Logger logger = (Logger) endpoint.getProperty("MessageLogger",
				Logger.class);
		if (logger == null) {
			String serviceName = endpoint.getService().getName().getLocalPart();
			InterfaceInfo iface = endpoint.getService().getInterface();
			String portName = endpoint.getName().getLocalPart();
			String portTypeName = iface.getName().getLocalPart();
			String logName = new StringBuilder()
					.append("org.apache.cxf.services.").append(serviceName)
					.append(".").append(portName).append(".")
					.append(portTypeName).toString();

			logger = LogUtils.getL7dLogger(getClass(), null, logName);
			endpoint.setProperty("MessageLogger", logger);
		}
		return logger;
	}
	
	private void saveMsg(String resMsg,String id){
		WsInterfaceMessage wsMsg = wsInterfaceMessageService.findByMessageId(id);
		
		if(wsMsg!=null){
			wsMsg.setModified(new Date());
			
			wsMsg.setResponseMsg(resMsg);
			
			wsInterfaceMessageService.update(wsMsg);
		}
	
	}
}
