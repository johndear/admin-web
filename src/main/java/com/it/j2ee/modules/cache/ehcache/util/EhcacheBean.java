package com.it.j2ee.modules.cache.ehcache.util;

import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhcacheBean {
	
	private Ehcache cache;
	
	public EhcacheBean(){}
	
	public EhcacheBean(Ehcache cache){
		this.cache = cache;
	}
	
	public Object get(String key) {
		Element element = cache.get(key);
		return null == element ? null : element.getObjectValue();
	}

	public void put(String key, Object value) {
		Element element = new Element(key, value);
		cache.put(element);
	}

	public void remove(String key) {
		cache.remove(key);		
	}

	public void putAll(List<Element> list) {
		cache.putAll(list);
		
	}

}
