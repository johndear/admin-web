package com.it.j2ee.modules.solr;

import java.io.IOException;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.stereotype.Component;

public class SolrServerFactory {
	
	private static HttpSolrServer server = null;

	private static Properties prop;
	static{
//		prop = new Properties();
//		try {
//			prop.load(SolrServerFactory.class.getResourceAsStream("dev.properties"));
//		} catch (IOException e) {
//		}
		
	}
	
	private SolrServerFactory(){
		
	}
	
	public static HttpSolrServer getInstance(){
		if(server == null){
			String baseURL = "http://localhost:1888/solr";//prop.getProperty("solr.url");
			server = new HttpSolrServer(baseURL);
		}
	
		return server;
	}
	
	
}
