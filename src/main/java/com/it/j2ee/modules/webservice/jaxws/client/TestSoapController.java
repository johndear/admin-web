package com.it.j2ee.modules.webservice.jaxws.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.j2ee.modules.webservice.cxf.service.EndpointService;
import com.it.j2ee.modules.webservice.jaxws.server.SoapUserService;
import com.it.j2ee.modules.webservice.jaxws.server.response.IdResult;

@Controller
@RequestMapping(value="/webservice/soap")
public class TestSoapController {
	
	@Autowired
	private EndpointService EndpointService;
	
	/**
	 * 
	 * 功能详细描述 
	 * 弹出窗时需要
	 * @author Liusu
	 * @create 2014年12月15日 下午4:33:38
	 */
	@RequestMapping(value = "/createUser")
	@ResponseBody
	public IdResult createUser(HttpServletRequest request,HttpServletResponse response, String view, Model model) {
		SoapUserService service = (SoapUserService) EndpointService.getClient("accountservice");
		IdResult id = service.createUser(11L);
		return id;
	}

}
