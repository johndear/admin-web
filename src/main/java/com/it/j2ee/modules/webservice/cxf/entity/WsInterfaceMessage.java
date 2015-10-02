package com.it.j2ee.modules.webservice.cxf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "Ws_Interface_Message", schema = "ydscm")
public class WsInterfaceMessage implements java.io.Serializable {
	private static final long serialVersionUID = -541095669383177384L;
	
	//主键
	private String messageId;
	
	private String msgInterface;
	
	private String msgMethod;
	
    private String msgType;
    
    
    private String msgKey;
	
	//请求报文
	private String requestMsg;
	
	//响应报文
	private String responseMsg;
	
	private Date created = new Date();
	private String createdby;
	private Date modified;
	private String modifiedby;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "MESSAGE_ID", unique = true, nullable = false)
	public String getMessageId() {
		return messageId;
	}


	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	@Column(name = "MSG_TYPE")
	public String getMsgType() {
		return msgType;
	}


	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Column(name = "MSG_KEY")
	public String getMsgKey() {
		return msgKey;
	}


	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}


	@Column(name = "REQUEST_MSG")
	public String getRequestMsg() {
		return requestMsg;
	}


	public void setRequestMsg(String requestMsg) {
		this.requestMsg = requestMsg;
	}

	@Column(name = "response_Msg")
	public String getResponseMsg() {
		return responseMsg;
	}


	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED")
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "CREATEDBY", length = 50)
	public String getCreatedBy() {
		return this.createdby;
	}

	public void setCreatedBy(String createdby) {
		this.createdby = createdby;
	}

//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED")
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Column(name = "MODIFIEDBY", length = 50)
	public String getModifiedBy() {
		return this.modifiedby;
	}

	public void setModifiedBy(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	@Column(name = "MSG_INTERFACE", length = 64)
	public String getMsgInterface() {
		return msgInterface;
	}


	public void setMsgInterface(String msgInterface) {
		this.msgInterface = msgInterface;
	}

	@Column(name = "MSG_METHOD", length = 64)
	public String getMsgMethod() {
		return msgMethod;
	}


	public void setMsgMethod(String msgMethod) {
		this.msgMethod = msgMethod;
	}
	
	

}
