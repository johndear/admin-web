package com.it.j2ee.modules.solr.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.j2ee.modules.common.util.HttpUtils;
import com.it.j2ee.modules.solr.biz.SuggestService;
import com.it.j2ee.modules.solr.util.BeanUtils;

/**
 * solr搜索词推荐
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/suggest")
public class SuggestResource {
	
	@RequestMapping(value = "/get")
	@ResponseBody
	public String suggest(HttpServletRequest request){
		Map<String,String> singleMap = HttpUtils.getParameters(request);
		
		String word = singleMap.get("term");
		String resp = SuggestService.suggest(word);
		System.out.println("搜索提示词" + resp);
		
		return resp;
		   
	}
}
