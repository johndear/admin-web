package com.it.j2ee.webservice;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import com.it.j2ee.modules.webservice.jaxws.server.response.IdResult;

/**
 * 用JaxWsProxyFactoryBean 与 JaxWsDynamicClientFactory 进行比较
 * JaxWsProxyFactoryBean 要求服务器端的webservice必须是java实现--这样也就失去了使用webservice的意义
 * JaxWsDynamicClientFactory 只要指定服务器端wsdl文件的位置，然后指定要调用的方法和方法的参数即可，不关心服务端的实现方式。
 * @author Administrator
 *
 */
public class CxfClientTest {

	 public static void main(String[] args) throws Exception {  
//	        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();  
////	        Client client = clientFactory.createClient("http://localhost:8083/showcase/cxf/soap/accountservice?wsdl");
//	        Client client = clientFactory.createClient("http://localhost:8081/admin-web/cxf/soap/accountservice?wsdl");
//	        Object[] result = client.invoke("createUser", 5L);  
//	        System.out.println(((IdResult)result[0]).getId());  
		 
		  String aa="afdadf\"\"";
		  System.out.println(aa.replaceAll("\"", "“"));
	    }  
}
