package com.it.j2ee.modules.solr.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.it.j2ee.modules.solr.biz.GroupService;

@Path("group")
public class GroupResource {

	@GET
	@Path("get")
	public Response category(){
		String resp = GroupService.category();
		System.out.println("分类导航：" + resp);
		
		return Response.ok(resp,"text/html").status(200).build();
	}
}
