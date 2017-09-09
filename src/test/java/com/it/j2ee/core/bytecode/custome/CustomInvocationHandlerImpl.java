package com.it.j2ee.core.bytecode.custome;

import java.lang.reflect.Method;

public class CustomInvocationHandlerImpl implements CustomInvocationHandler {

	private Object target;
	
	public CustomInvocationHandlerImpl(Object target){
		this.target = target;
	}
	
	@Override
	public Object invoke(Object arg0, Method method, Object[] arg2)
			throws Throwable {
		System.out.println("方法执行前。。。");
		method.invoke(target, arg2);
		System.out.println("方法执行后。。。");
		return null;
	}

}
