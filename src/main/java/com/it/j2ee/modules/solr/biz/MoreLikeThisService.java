package com.it.j2ee.modules.solr.biz;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

import com.alibaba.fastjson.JSONObject;
import com.it.j2ee.modules.solr.SolrServerFactory;

public class MoreLikeThisService {

	private static HttpSolrServer solr = SolrServerFactory.getInstance();
	
	public static String moreLikeThis(String id){
		// http://localhost:8080/solr/mlt?q=id:10&mlt.fl=title&mlt.mindf=1&mlt.mintf=1
	    ModifiableSolrParams params = new SolrQuery();
	    params.set("qt", "/mlt");
	    params.set("q", "id:"+ id);
	    params.set("mlt.fl", "title");
	    params.set("mlt.mindf", "1");
	    params.set("mlt.mintf", "1");

	    QueryResponse response = null;
		try {
			response = solr.query(params);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SolrDocumentList docs = (SolrDocumentList) response.getResponse().get("response");
	    System.out.println("response = " + docs);
	    
	    Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", docs);
        
        String json = JSONObject.toJSONString(resultMap);
		return json;
	
	}
	
	public static void main(String[] args) throws MalformedURLException, SolrServerException {
	  
	}
}
