package com.it.j2ee.modules.webservice.jaxws.server;

import javax.jws.WebService;

import org.apache.cxf.feature.Features;

import com.it.j2ee.modules.webservice.jaxws.WsConstants;
import com.it.j2ee.modules.webservice.jaxws.server.response.IdResult;
/**
 * WebService服务端实现类.
 * 
 * 为演示方便，直接调用了Dao层.客户端实现见功能测试用例.
 * 
 * @author calvin
 */
// serviceName指明WSDL中<wsdl:service>与<wsdl:binding>元素的名称, endpointInterface属性指向Interface类全称.
@WebService(serviceName = "AccountService", endpointInterface = "com.it.j2ee.modules.webservice.jaxws.server.SoapUserService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class SoapUserServiceImpl implements SoapUserService{

	public IdResult createUser(Long userId) {
		return new IdResult(userId);
	}

	
	

}
