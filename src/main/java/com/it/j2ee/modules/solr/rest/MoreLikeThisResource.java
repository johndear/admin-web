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
import com.it.j2ee.modules.solr.biz.MoreLikeThisService;
import com.it.j2ee.modules.solr.util.BeanUtils;

@Controller
@RequestMapping(value = "/related")
public class MoreLikeThisResource {

	@RequestMapping(value = "/get")
	@ResponseBody
	public String moreLikeThis(HttpServletRequest request){
		Map<String,String> singleMap = HttpUtils.getParameters(request);
		
		String id = singleMap.get("id");
		String resp = MoreLikeThisService.moreLikeThis(id);
		System.out.println("推荐结果：" + resp);
		
		return resp;
	}
}
