package com.it.j2ee.core.bytecode.custome;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import com.it.j2ee.core.bytecode.Javassist.demo1.Station;
import com.it.j2ee.core.bytecode.Javassist.demo1.TicketService;
import com.it.j2ee.core.util.ResourceUtil;

public class CustomProxy2222 {

	public static Object newProxyInstance(ClassLoader cl, Class clazz, CustomInvocationHandler handler) throws Exception{
		
		ClassPool pool = ClassPool.getDefault();
		
		CtClass cc = pool.makeClass(getFullClasspath(clazz) + "Proxy");
		List<CtClass> ccInterfaces = new ArrayList<CtClass>();
		
		Class[] interfaces = clazz.getInterfaces();
		for (Class interfaceClz : interfaces) {
			// 设置接口
			CtClass interface1 = pool.get(getFullClasspath(interfaceClz));
			ccInterfaces.add(interface1);
		}
		cc.setInterfaces(ccInterfaces.toArray(new CtClass[]{}));
		
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			String methodSign =  Modifier.toString(field.getModifiers());
			methodSign = methodSign +" "+ getFullClasspath(field.getType()) +" " + field.getName();
			System.out.println(methodSign);
			// 设置Field
			CtField ctField = CtField.make(methodSign, cc);
			cc.addField(ctField);
		}
		
		// 设置Field
		CtField ctField = CtField.make("private " + getFullClasspath(clazz) + " target;", cc);
		cc.addField(ctField);


		CtClass stationClass = pool.get(getFullClasspath(clazz));
		CtClass[] arrays = new CtClass[] { stationClass };
		CtConstructor ctc = CtNewConstructor.make(arrays, null, CtNewConstructor.PASS_NONE, null, null, cc);
//		// 设置构造函数内部信息
		ctc.setBody("{this.target=$1;}");
		cc.addConstructor(ctc);
		
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			String methodSign = Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getName() + " " + method.getName() +"(";
			String methodArgs = "";
			String methodArgNames = "";
			Class<?> paramTypes[] = method.getParameterTypes();
			for (int i = 0; i < paramTypes.length; i++) {
				Class paramType = paramTypes[i];
				if(i != paramTypes.length-1){
					methodArgs = methodArgs + paramType.getName()+ " arg"+ (i+1) +", ";
					methodArgNames = methodArgNames + "arg"+ (i+1) +", ";
					continue;
				}
				methodArgs = methodArgs + paramType.getName() + " arg"+ (i+1);
				methodArgNames = methodArgNames + "arg"+ (i+1);
				
			}
			methodSign = methodSign + methodArgs + ");";
			
			// 创建收取手续 takeHandlingFee方法
			CtMethod cm = CtNewMethod.make(methodSign, cc);
			String methodBody = "{"+ ("void".equals(method.getReturnType().getName())?"":"return ") + "this.target."+method.getName()+"("+methodArgNames.replace("arg", "$")+");}";
			cm.setBody(methodBody);
			cc.addMethod(cm);
			cm.insertBefore("{System.out.println(\"收取手续费，打印发票。。。。。start \");}");
			cm.insertAfter("{System.out.println(\"收取手续费，打印发票。。。。。end \");}");
		}

		// 输出到文件
		cc.writeFile("D://test");
		
		// 通过构造器实例化
		Class c = cc.toClass();
		Constructor constructor = c.getConstructor(clazz);
		TicketService o = (TicketService) constructor.newInstance(new Station());
		
		return o;
	}
	
	private static String getFullClasspath(Class clazz) {
		String fullClasspath = ResourceUtil.getClassFilePath(clazz).replace(ResourceUtil.getClassPath(clazz), "");
		fullClasspath = fullClasspath.substring(1,fullClasspath.lastIndexOf(".")).replace(File.separator, ".");
		return fullClasspath;
	}
	
	public static void main(String[] args) throws Exception {
		CustomProxy2222.newProxyInstance(Thread.currentThread().getClass().getClassLoader(), Station.class,new CustomInvocationHandlerImpl(new Station()));
	}
	
	
}
