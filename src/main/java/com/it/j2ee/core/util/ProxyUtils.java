package com.it.j2ee.core.util;

import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.core.GeneratorStrategy;
import net.sf.cglib.proxy.Enhancer;

import com.it.j2ee.core.bytecode.Javassist.demo1.Station;
import com.it.j2ee.core.bytecode.Javassist.demo1.StationProxy;
import com.it.j2ee.core.bytecode.Javassist.demo1.TicketService;
import com.it.j2ee.core.bytecode.asm.Programmer;
import com.it.j2ee.core.bytecode.cglib.Hacker;

import sun.misc.ProxyGenerator;
/**
 * 
 * jdk中生成代理类的方法
 * 
 * 参考：jdk-》 proxy.class 671行
 * 
 * @author Administrator
 *
 */
public class ProxyUtils {

	/**
	 * 使用jdk生成代理类.class文件格式
	 * @param clazz
	 * @param proxyName
	 */
	public static void generateClassFile(Class clazz, String proxyName) {
		// 根据类信息和提供的代理类名称，生成字节码
		byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
		FileOutputStream out = null;

		try {
			// 保留到硬盘中
			String paths =clazz.getResource(".").getPath();
			System.out.println(paths);
			out = new FileOutputStream(paths + proxyName + ".class");
			out.write(classFile);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 使用cglib生成代理类.class文件格式
	 * @throws Exception
	 */
	public static void generateClassFileByCglib(Class clazz, String proxyName) throws Exception {
		Hacker hacker = new Hacker();
		// cglib 中加强器，用来创建动态代理
		Enhancer enhancer = new Enhancer();
		// 设置要创建动态代理的类
		enhancer.setSuperclass(clazz);
		// 设置回调，这里相当于是对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实行intercept()方法进行拦截
		enhancer.setCallback(hacker);
		enhancer.create();
		GeneratorStrategy strategy = DefaultGeneratorStrategy.INSTANCE;
		byte[] b = strategy.generate(enhancer);

		FileOutputStream out = null;
		try {
			// 保留到硬盘中
			String paths =clazz.getResource(".").getPath();
			System.out.println(paths);
			out = new FileOutputStream(paths + proxyName + ".class");
			out.write(b);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
