/*
 * Created on 2005-4-1 TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package  com.it.j2ee.modules.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 框架的基本Spring服务定位器
 * 
 * @author Jason TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
@Component("baseServiceLocator")
public class SpringBeanLocator implements ApplicationContextAware {
	protected static Logger logger = LoggerFactory
			.getLogger(SpringBeanLocator.class);
	/**
	 * 基本Spring服务的配置文件
	 */
	private static String[] CONTEXT_FILE_NAME = new String[]{"**/applicationContext*.xml","**/spring*.xml","classpath:**/*.properties"};

	private static ApplicationContext fac = null;

	public synchronized static Object getService(String name) {
		try {
            if(fac == null) {
                getApplicationContext();
            }
				if (fac.containsBean(name)) {
					return fac.getBean(name);
				} else {
					logger.error("In ApplicationContext,bean name:" + name
							+ " is not exist");
				}
		} catch (BeansException e) {
			//e.printStackTrace();
			logger.error("getApplicationContext throw exception", e);

			return null;
		}

		return null;
	}

/*	@SuppressWarnings("unchecked")
	public static <T> T getService(String name, Class<T> clz) {
		try {
			ApplicationContext ac = getApplicationContext();
			if (ac != null && ac.containsBean(name)) {
				return (T) ac.getBean(name, clz);
			}
		} catch (BeansException e) {
			//e.printStackTrace();
			logger.error("getApplicationContext throw exception", e);

			return null;
		}
		return null;
	}*/

	// 如果没有当前上下文，自动从文件装载，用于调试的
	private synchronized static ApplicationContext getApplicationContext() {

		fac = ContextLoader.getCurrentWebApplicationContext();

		if (fac == null) {
			logger.error("WebApplicationContext is not exist");
			fac = new ClassPathXmlApplicationContext(CONTEXT_FILE_NAME);
			//fac =  new ClassPathXmlApplicationContext(new String[]{"test/applicationContext_test.xml","classpath:**/*.properties"});
			((AbstractApplicationContext) fac).registerShutdownHook();
		}

		return fac;
	}

	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		// TODO Auto-generated method stub
		fac = ac;
	}
}
