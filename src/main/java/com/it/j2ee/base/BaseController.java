package com.it.j2ee.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.it.j2ee.modules.common.util.HttpUtils;

public class BaseController {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object request2Bean(HttpServletRequest request,Class c){
		Map<String,String> par = HttpUtils.getParameters(request);
		String json = JSONObject.toJSONString(par);
		return  JSONObject.parseObject(json,c);
	}
	
}
