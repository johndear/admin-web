package com.it.j2ee.modules.cache.ehcache.util;

import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EhcacheFactory {
	
	@Autowired
	private CacheManager ehcacheManager;
	
	public EhcacheFactory(){}

	public EhcacheBean getCache(String cacheName){
		EhcacheBean cache = new EhcacheBean(ehcacheManager.getEhcache(cacheName));
		return cache;
	}
	
	
	
	
	
}
