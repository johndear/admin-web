package com.it.j2ee.modules.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * 根据spring的上下文获取bean
 * @author leihong
 *
 */
@Component
public class AppBeanFactory implements ApplicationContextAware {
	
	private static ApplicationContext context;

	/**   
	 * @param applicationContext
	 * @throws BeansException  
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)  
	 */

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		AppBeanFactory.context = applicationContext;
	}
	
	public static Object getBean(String name){
		return AppBeanFactory.context.getBean(name);
	}

}
