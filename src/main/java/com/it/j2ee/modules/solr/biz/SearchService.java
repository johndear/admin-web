package com.it.j2ee.modules.solr.biz;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.it.j2ee.modules.solr.SolrServerFactory;

@Service
public class SearchService {


	@SuppressWarnings("deprecation")
	public String search(String word,int start,
			int count, String sortfield,String sortby, Boolean hightlight,String hightlightField) {
		HttpSolrServer solr = SolrServerFactory.getInstance();
//		SolrQuery query = null;
//		
//		try {
//			// 初始化查询对象
//			query = new SolrQuery(word);
//			// 设置起始位置与返回结果数
//			query.setStart(start);
//			query.setRows(count);
//			// 设置排序
//			if(StringUtils.isNotEmpty(sortfield)){
//				if(StringUtils.isNotEmpty(sortby) && sortby.equalsIgnoreCase("asc")){
//					query.addSort(sortfield, SolrQuery.ORDER.asc);
//				} else {
//					query.addSort(sortfield, SolrQuery.ORDER.desc);
//				}
//			}
//			// 设置高亮
//			if (null != hightlight) {
//				query.setHighlight(true); // 开启高亮组件
//				query.addHighlightField(hightlightField);// 高亮字段
//				query.setHighlightSimplePre("<font color=\"red\">");// 标记
//				query.setHighlightSimplePost("</font>");
//				query.setHighlightSnippets(1);// 结果分片数，默认为1
//				query.setHighlightFragsize(1000);// 每个分片的最大长度，默认为100
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		ModifiableSolrParams query = new SolrQuery();
		query.set("qt", "/new_core/select");
		query.set("q",StringUtils.isEmpty(word)? "*:*" : ("name:\""+ word +"\"") );
//		query.set("highlight",true);
//		query.set("hightlightField","name");
//		query.set("highlightSimplePre", "<font color=\"red\">");
//		query.set("highlightSimplePost", "</font>");
//		query.set("highlightSnippets",1);// 结果分片数，默认为1
//		query.set("highlightFragsize",1000);// 每个分片的最大长度，默认为100
		query.set("hl",true);
		query.set("hl.fl","name");
		query.set("hl.simple.pre", "<font color=\"red\">");
		query.set("hl.simple.post", "</font>");
		query.set("f.name.hl.snippets",1);
		query.set("f.name.hl.fragsize",1000);

		QueryResponse rsp = null;
		try {
			rsp = solr.query(query);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		//获取搜索结果和搜索结果数
		SolrDocumentList docs = rsp.getResults();
		long counts = docs.getNumFound();

//		//获取所有高亮的字段
        Map<String,Map<String,List<String>>> highlightMap= rsp.getHighlighting();
        Iterator<SolrDocument> iter = docs.iterator();
        while (iter.hasNext()) {
            SolrDocument doc = iter.next();
            try {
				String idStr = doc.getFieldValue("id").toString();
				List<String> highlightSnippets=highlightMap.get(idStr).get(hightlightField);
				//获取并设置高亮的字段title
				if(highlightSnippets!=null && highlightSnippets.size()>0){
					doc.setField("name", highlightSnippets.get(0));
				}
			} catch (Exception e) {
				System.out.println("高亮时出现问题"+ e.getMessage());
			}
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", docs);
        resultMap.put("counts", counts);
        
        String json = JSONObject.toJSONString(resultMap);
		return json;
	}
}
