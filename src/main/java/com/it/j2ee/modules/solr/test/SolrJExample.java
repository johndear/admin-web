package com.it.j2ee.modules.solr.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;

/**
 * 使用SolrJ进行对Solr的增、删、查功能。
 * @author Administrator
 *
 */
public class SolrJExample {

	public static void main(String[] args) throws SolrServerException, IOException {
		HttpSolrServer solr = new HttpSolrServer("http://localhost:1888/solr");

		// http://localhost:8983/solr/spellCheckCompRH?q=epod&spellcheck=on&spellcheck.build=true
		// ModifiableSolrParams params = new ModifiableSolrParams();
		// // params.set("qt", "/spellCheckCompRH");
		// params.set("qt", "/new_core/select");
		// params.set("q", "*:*");
		// // params.set("wt", "json");
		// // params.set("spellcheck", "on");
		// // params.set("spellcheck.build", "true");
		// // params.set("df", "title");

		ModifiableSolrParams params = new SolrQuery();
		params.set("qt", "/new_core/select");
		params.set("q","*:*");
//		params.addSortField("id", SolrQuery.ORDER.asc);
		 //indicate we want facets 
//		params.setFacet(true); 
//		 //indicate what field to facet on 
//		params.addFacetField("name"); 
//		 //we only want facets that have at least one entry 
//		params.setFacetMinCount(1); 
		ModifiableSolrParams params2 =null;
		QueryResponse response = solr.query(params);
		System.out.println("response = " + response);

		System.out.println(response.toString());
		System.out.println("共找到:" + response.getResults().getNumFound() + "个结果");
		// 解析返回的参数
		SolrDocumentList sdl = (SolrDocumentList) response.getResponse().get("response");
		for (int i = 0; i < sdl.size(); i++) {
			System.out.println("-------------");
			SolrDocument doc = sdl.get(i);
			for (String key : doc.getFieldNames()) {
				System.out.println(key + "=" + sdl.get(i).getFieldValue(key));
			}
		}

		System.out.println("查询时间：" + response.getQTime());
		List<FacetField> facets = response.getFacetFields();// 返回的facet列表
		for (FacetField facet : facets) {
			System.out.println(facet.getName());
			System.out.println("----------------");
			List<Count> counts = facet.getValues();
			for (Count count : counts) {
				System.out.println(count.getName() + ":" + count.getCount());
			}
			System.out.println();
		}

	}

}
