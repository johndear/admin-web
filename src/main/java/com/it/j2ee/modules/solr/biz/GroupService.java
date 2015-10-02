package com.it.j2ee.modules.solr.biz;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;

import com.alibaba.fastjson.JSONObject;
import com.it.j2ee.modules.solr.SolrServerFactory;


public class GroupService {

	private static HttpSolrServer solr = SolrServerFactory.getInstance();
	
	public static String category() {
		// http://localhost:8080/solr/select?q=*:*&facet=true&facet.field=facet
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("qt", "/select");
		params.set("q", "*:*");
		params.set("facet", "true");
		params.set("facet.field", "facet");

		QueryResponse response = null;
		try {
			response = solr.query(params);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FacetField facetField = response.getFacetFields().get(0);
		List<Count> counts = facetField.getValues();
		Map<String, Long> categoryMap = new HashMap<String, Long>();
		for (Count count : counts) {
//			System.out.println(count.getName() + " = " + count.getCount());
			categoryMap.put(count.getName(), count.getCount());
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", categoryMap);
        
        String json = JSONObject.toJSONString(resultMap);
		return json;

	}
}
