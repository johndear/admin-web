package com.it.j2ee.modules.solr.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.j2ee.modules.common.util.HttpUtils;
import com.it.j2ee.modules.solr.biz.SearchService;
import com.it.j2ee.modules.solr.util.BeanUtils;

/**
 * solr搜索
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/search")
public class SearchResource {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping
	public String searchPage(HttpServletRequest request){
		return "solr/index";
	}

	@RequestMapping(value = "/get")
	@ResponseBody
	public String search(HttpServletRequest request){
		Map<String,String> singleMap = HttpUtils.getParameters(request);
//		MultivaluedMap<String, String> multivaluedMap = uriInfo.getQueryParameters();
//		Map<String , String> singleMap = BeanUtils.convertMultiMap2Map(multivaluedMap);
		
		String word = null;
//		try {
//			word = URLEncoder.encode(singleMap.get("word"),"utf-8");
//		} catch (UnsupportedEncodingException e) {
//		}
		word = singleMap.get("word");
		int start= StringUtils.isEmpty(singleMap.get("start"))? 0: Integer.parseInt(singleMap.get("start"));
		int count= StringUtils.isEmpty(singleMap.get("count"))? 8: Integer.parseInt(singleMap.get("count"));
		String sortfield= singleMap.get("sortfield");
		String sortby= singleMap.get("sortby");
		boolean hightlight=StringUtils.isEmpty(singleMap.get("hightlight"))?false: Boolean.parseBoolean(singleMap.get("hightlight"));
		String hightlightField = StringUtils.isEmpty(singleMap.get("hightlightField"))?"name": singleMap.get("hightlightField");
		String resp = searchService.search(word, start, count, sortfield, sortby, hightlight, hightlightField);
		System.out.println("查询结果：" + resp);
		
		return resp;
	}
	
}
