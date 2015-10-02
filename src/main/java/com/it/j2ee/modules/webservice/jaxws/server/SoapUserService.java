package com.it.j2ee.modules.webservice.jaxws.server;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.it.j2ee.modules.webservice.jaxws.WsConstants;
import com.it.j2ee.modules.webservice.jaxws.server.response.IdResult;

/**
 * JAX-WS2.0的WebService接口定义类.
 * 
 * 使用JAX-WS2.0 annotation设置WSDL中的定义.
 * 使用WSResult及其子类类包裹返回结果.
 * 使用DTO传输对象隔绝系统内部领域对象的修改对外系统的影响.
 * 
 * @author calvin
 */
// name 指明wsdl中<wsdl:portType>元素的名称
@WebService(name = "AccountService", targetNamespace = WsConstants.NS)
public interface SoapUserService {
	
	/**
	 * 新建用户.
	 */
	IdResult createUser(@WebParam(name = "userId") Long userId);

}
