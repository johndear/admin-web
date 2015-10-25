package com.it.j2ee.core.bytecode.cglib;

import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.cglib.asm.ClassVisitor;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.core.AbstractClassGenerator;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.core.GeneratorStrategy;
import net.sf.cglib.core.KeyFactory;
import net.sf.cglib.proxy.Enhancer;

import com.it.j2ee.core.bytecode.ProxyUtils;
import com.it.j2ee.core.bytecode.asm.Programmer;

public class Test {

	public static void main(String[] args) throws Exception {
		// Programmer progammer = new Programmer();
		// Hacker hacker = new Hacker();
		// // cglib 中加强器，用来创建动态代理
		// Enhancer enhancer = new Enhancer();
		// // 设置要创建动态代理的类
		// enhancer.setSuperclass(progammer.getClass());
		// //
		// 设置回调，这里相当于是对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实行intercept()方法进行拦截
		// enhancer.setCallback(hacker);
		// Programmer proxy = (Programmer) enhancer.create();
		// proxy.code();


	}

}
