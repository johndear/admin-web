package com.it.j2ee.modules.webservice.cxf.entity;

import javax.persistence.*;


/**
 * @author
 */
@Entity
@Table(name="WS_ENDPOINT",schema="ydscm")
public class Endpoint implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3861100262281358829L;

	public enum SeviceType {
		S, C
	}

	public enum Protocol {
		Cxf, JAXWS, Hessian, RMI, EJB, Custom,MQ,Axis,Http,WebService
	}

	/** 服务编号 */
	private Long id;

	/** 显示名 */
	private String name;

	/** 名称 */
	private String kind;

	/** 描述 */
	private String description;

	private String typeId;

	private String serviceClass;

	private String address;
	/**
	 * 连接超时时间
	 */
	private Integer timeout;
	
	/**
	 * 连接数
	 */
	private Integer connectcount;
	
	private String inInterceptors;
	private String outInterceptors;
	
    @Id
	@SequenceGenerator(name="SEQ_SCM_ENDPOINT",allocationSize=1, sequenceName="SEQ_SCM_ENDPOINT")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_SCM_ENDPOINT") 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "TIME_OUT")
	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Column(name = "CONNECT_COUNT")
	public Integer getConnectcount() {
		return connectcount;
	}

	public void setConnectcount(Integer connectcount) {
		this.connectcount = connectcount;
	}

	@Column(name = "SERVICE_CLASS")
	public String getServiceClass() {
		return serviceClass;
	}

	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "KIND")
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TYPE_ID")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Column(name = "IN_INTERCEPTORS")
	public String getInInterceptors() {
		return inInterceptors;
	}

	public void setInInterceptors(String inInterceptors) {
		this.inInterceptors = inInterceptors;
	}

	@Column(name = "OUT_INTERCEPTORS")
	public String getOutInterceptors() {
		return outInterceptors;
	}

	public void setOutInterceptors(String outInterceptors) {
		this.outInterceptors = outInterceptors;
	}
	
	
}
