package com.it.j2ee.modules.solr.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.it.j2ee.modules.solr.biz.IndexService;

@Path("index")
public class IndexResource {

	@GET
	@Path("add")
	public void addIndex(@Context UriInfo uriInfo) {
		List<Map<String, Object>> list = null;
		IndexService.addIndex(list);
	}

	@DELETE
	@Path("delete")
	public void deleteIndex(@Context UriInfo uriInfo) {
		List<String> ids = null;
		IndexService.deleteIndex(ids);
	}
}
