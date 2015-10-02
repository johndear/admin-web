package com.it.j2ee.modules.webservice.cxf.service;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.it.j2ee.modules.webservice.cxf.entity.Endpoint;
import com.it.j2ee.modules.webservice.cxf.interceptor.CxfLogInInterceptor;
import com.it.j2ee.modules.webservice.cxf.interceptor.CxfLogOutInterceptor;
import com.it.j2ee.modules.webservice.cxf.util.CommonCacheUtil;
import com.it.j2ee.modules.webservice.cxf.util.ExceptionUtil;

@Service
public class EndpointServiceImpl implements EndpointService,ApplicationContextAware {
	
    @Autowired
	CommonCacheUtil CommonCacheUtil;
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getClient(String serviceName){
		try{
			Endpoint end = CommonCacheUtil.getEndpointMap().get(serviceName);
			
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			
			factory.getInInterceptors().add(new CxfLogInInterceptor());
			factory.getOutInterceptors().add(new CxfLogOutInterceptor());
			
			if(StringUtils.isNotBlank(end.getInInterceptors())){
				String []classs = end.getInInterceptors().split(";");
				for (String classsName : classs) {
					Map<String,? extends Object> map = context.getBeansOfType(Class.forName(classsName));
					if(map.size()>0){ // 首先检查该类型的javabean有没有托管给spring容器管理，如果有则从spring容器中获取该bean，如果没有就用反射实例化一个新的实例进行拦截处理
						for (Entry entry : map.entrySet()) {
							Object obj = entry.getValue();
							factory.getInInterceptors().add((Interceptor<Message>)obj);
						}
					}else{
						factory.getInInterceptors().add((Interceptor<Message>)Class.forName(classsName).newInstance());
					}
				}
			}
			
			if(StringUtils.isNotBlank(end.getOutInterceptors())){
				String []classs = end.getOutInterceptors().split(";");
				for (String classsName : classs) {
					Map<String,? extends Object> map = context.getBeansOfType(Class.forName(classsName));
					if(map.size()>0){ // 首先检查该类型的javabean有没有托管给spring容器管理，如果有则从spring容器中获取该bean，如果没有就用反射实例化一个新的实例进行拦截处理
						for (Entry entry : map.entrySet()) {
							Object obj = entry.getValue();
							factory.getOutInterceptors().add((Interceptor<Message>)obj);
						}
					}else{
						factory.getOutInterceptors().add((Interceptor<Message>)Class.forName(classsName).newInstance());
					}
				}
			}
	
			factory.setServiceClass(Class.forName(end.getServiceClass()));
			factory.setAddress(end.getAddress());
			Object client = (Object) factory.create();

            HTTPConduit http = (HTTPConduit) ClientProxy.getClient(client).getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(36000);
            httpClientPolicy.setAllowChunking(false);
            httpClientPolicy.setReceiveTimeout(32000);
            http.setClient(httpClientPolicy);

			return client;
		}catch(Exception e){
			e.printStackTrace();
			ExceptionUtil.saveException(this.getClass().getName()+"getClient", e);
		}
		return null;
	}
	
	private ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.context = arg0;
	}
	
	

}
