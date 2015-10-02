package com.it.j2ee.modules.common.web;

import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.j2ee.base.DatagridVO;
import com.it.j2ee.modules.common.entity.DataDict;
import com.it.j2ee.modules.common.service.DataDictService;

@Controller
public class DataDictController {

	@Autowired
	private DataDictService dataDictService;
	
	@RequestMapping(value = "/dict", method = RequestMethod.GET)
	public String getDictMap(HttpServletRequest request,
			HttpServletResponse response){
		return "dict/dictList";
	}
	
	@RequestMapping(value = "/dict/all", method = RequestMethod.POST)
	@ResponseBody
	public DatagridVO getAllDict(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			HttpServletRequest request, HttpServletResponse response){
		Page<DataDict> dicts=dataDictService.findDictCategoryPage(new HashMap<String, Object>(),page,rows,null,null);
		DatagridVO datagrid =new DatagridVO(dicts.getTotalElements(), dicts.getContent());
		return datagrid;
	}
	
	@RequestMapping(value = "/dict/findDictMap", method = RequestMethod.GET)
	@ResponseBody
	public SortedMap<String,String> getDictMap(String category, HttpServletRequest request,
			HttpServletResponse response){
		
		List<DataDict> dicts=dataDictService.getDict(category);
		SortedMap<String,String> map=new TreeMap<String,String>();
		for(DataDict dict:dicts){
			map.put(dict.getDictCode(), dict.getDictName());
		}
		return map;
	}
	
	@RequestMapping(value = "/dict/findDictMap1", method = RequestMethod.GET)
	@ResponseBody
	public String getDictMap2(String category, HttpServletRequest request,
			HttpServletResponse response){
		return "123";
	}
	
}
