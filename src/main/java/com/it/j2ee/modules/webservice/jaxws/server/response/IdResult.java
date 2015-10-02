/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.it.j2ee.modules.webservice.jaxws.server.response;

import javax.xml.bind.annotation.XmlType;

import com.it.j2ee.modules.webservice.jaxws.WsConstants;

/**
 * 创建某个对象返回的 通用IdResult.
 * 
 * @author badqiu
 */
@XmlType(name = "IdResult", namespace = WsConstants.NS)
public class IdResult extends WSResult {
	private Long id;

	public IdResult() {
	}

	public IdResult(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
//		return ToStringBuilder.reflectionToString(this);
		return "id:" + this.id;
	}
}
