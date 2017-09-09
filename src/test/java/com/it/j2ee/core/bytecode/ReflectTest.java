package com.it.j2ee.core.bytecode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import com.it.j2ee.core.bytecode.Javassist.demo1.Station;
import com.it.j2ee.core.bytecode.Javassist.demo1.TicketService;
import com.it.j2ee.core.bytecode.custome.CustomInvocationHandler;
import com.it.j2ee.core.bytecode.custome.CustomInvocationHandlerImpl;
import com.it.j2ee.core.bytecode.custome.CustomProxy;
import com.it.j2ee.modules.web.springmvc.task.service.TaskService;

public class ReflectTest {
	
	private static TaskService bean = null;
	
	public static void main(String[] args) throws Exception {
		CustomInvocationHandler handler = new CustomInvocationHandlerImpl(new Station());
		TicketService o = (TicketService)CustomProxy.newProxyInstance(Thread.class.getClassLoader(), TicketService.class, handler); 
		o.inquire();		
		
//		int total = bean.total(1,67);
//		System.out.println(total);
		
	}

	
	
	

}
