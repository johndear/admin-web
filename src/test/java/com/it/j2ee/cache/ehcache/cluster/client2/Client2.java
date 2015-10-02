/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.it.j2ee.cache.ehcache.cluster.client2;

import java.util.Scanner;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.it.j2ee.spring.SpringContextTestCase;

/**
 * 演示Ehcache的配置.
 * 
 * 配置见applicationContext-ehcache.xml与ehcache.xml
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "classpath:com/it/j2ee/modules/cache/ehcache/cluster/c2/spring-ehcache2.xml" })
public class Client2 extends SpringContextTestCase {
	private static final String CACHE_NAME = "colors";

	@Autowired
	private CacheManager ehcacheManager;
	private Cache cache;

	@SuppressWarnings("resource")
	@Test
	public void demo() {
		cache = ehcacheManager.getCache(CACHE_NAME);
		
		System.out.println("Cluster2: (输入q退出！)");
		
		int i =0;
		do{
			// 从cluster1中读取数据
			System.out.println("读取cluster1-key:");
			String key=new Scanner(System.in).nextLine();
			if("q".equals(key)){
				System.exit(0);
			}
			Object result = get(key);
			System.out.println("读取cluster1-value:"+result);
			
			// 写入cluster2缓存，检查cluster1是否能取到值
			System.out.println("写入cluster2-key:");
			key=new Scanner(System.in).nextLine();
			System.out.println("写入cluster2-value:");
			Object value=new Scanner(System.in).nextLine();
			put(key, value);
			
			i++;
		}while(i<10);
		
		// 避免程序退出
		new Scanner(System.in).nextLine();
	}

	public Object get(String key) {
		Element element = cache.get(key);
		return null == element ? null : element.getObjectValue();
	}

	public void put(String key, Object value) {
		Element element = new Element(key, value);
		cache.put(element);
	}
}