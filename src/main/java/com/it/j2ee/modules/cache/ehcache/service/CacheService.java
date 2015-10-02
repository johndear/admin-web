package com.it.j2ee.modules.cache.ehcache.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.j2ee.modules.cache.ehcache.util.EhcacheBean;
import com.it.j2ee.modules.cache.ehcache.util.EhcacheFactory;
import com.it.j2ee.modules.common.entity.DataDict;
import com.it.j2ee.modules.common.entity.FileAttach;
import com.it.j2ee.modules.common.service.DataDictService;
import com.it.j2ee.modules.common.service.FileAttachService;

@Service
public class CacheService {
	
	private static final String CACHE_NAME = "demoCache";
	
	@Autowired
	private EhcacheFactory ehcacheFactory;
	
	@Autowired
	private DataDictService dataDictService;
	
	@Autowired
	private FileAttachService fileAttachService;
	
	public void refreshAllCache() {
		EhcacheBean cache = ehcacheFactory.getCache(CACHE_NAME);
		
		List<DataDict> dicts = dataDictService.getDictAll();
		cache.put("dict", dicts);
		
		List<FileAttach> files = fileAttachService.findByforeignIdAndbizCode("aafee494a2a1", null);
		cache.put("file", files);
		
	}
	
	public void refreshCacheByType(String type) {
		EhcacheBean cache = ehcacheFactory.getCache(CACHE_NAME);
		if("dict".equals(type)){
			List<DataDict> dicts = dataDictService.getDictAll();
			cache.put("dict", dicts);
		}else if("file".equals(type)){
			List<FileAttach> files = fileAttachService.findByforeignIdAndbizCode("aafee494a2a1", null);
			cache.put("file", files);
		}
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getCacheDataByType(String type, String key) {
		EhcacheBean cache = ehcacheFactory.getCache(CACHE_NAME);
		
		List list = new ArrayList();
		if("dict".equals(type)){
			List<DataDict> cachedDicts = (List<DataDict>) cache.get("dict");
			for (DataDict dataDict : cachedDicts) {
				if(key.equals(dataDict.getDictType())){
					list.add(dataDict);
				}
			}
		}else if("file".equals(type)){
			List<FileAttach> cachedFiles = (List<FileAttach>) cache.get("file");
			for (FileAttach fileAttach : cachedFiles) {
				if(key.equals(fileAttach.getForeignId())){
					list.add(fileAttach);
				}
			}
		}
		return list;
	}

	
}
