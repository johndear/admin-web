package com.it.j2ee.modules.cache.ehcache.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.j2ee.base.ResultVO;
import com.it.j2ee.modules.cache.ehcache.service.CacheService;

@Controller
@RequestMapping(value = "/ehcache")
public class CacheController {
	
	@Autowired
	private CacheService CacheService;
	
	@RequestMapping(value="/refreshAllCache", method = RequestMethod.GET)
	@ResponseBody
	public ResultVO refreshAllCache(Model model) {
		try {
			CacheService.refreshAllCache();
			return new ResultVO("success", "刷新成功!");
		} catch (Exception e) {
			return new ResultVO("failure", "刷新失败!" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/refreshCacheByType", method = RequestMethod.GET)
	@ResponseBody
	public ResultVO refreshCacheByType(Model model,String type) {
		try {
			CacheService.refreshCacheByType(type);
			return new ResultVO("success", "刷新成功!");
		} catch (Exception e) {
			return new ResultVO("failure", "刷新失败!" + e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getCacheDataByType", method = RequestMethod.GET)
	@ResponseBody
	public List getCacheDataByType(Model model,String type,String key) {
		return CacheService.getCacheDataByType(type, key);
	}

}
