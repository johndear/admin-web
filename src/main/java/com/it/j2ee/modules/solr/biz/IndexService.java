package com.it.j2ee.modules.solr.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import com.it.j2ee.modules.solr.SolrServerFactory;

public class IndexService {

	private static HttpSolrServer solr = SolrServerFactory.getInstance();

	public static void addIndex(List<Map<String, Object>> list) {
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (int i = 0; i < list.size(); i++) {
			// 设置每个字段不得为空，可以在提交索引前进行检查
			SolrInputDocument doc = new SolrInputDocument();
			Map<String, Object> map = list.get(i);
			for (Entry<String, Object> entry : map.entrySet()) {
				// 在这里请注意date的格式，要进行适当的转化，上文已提到
				doc.addField(entry.getKey(), entry.getValue());
			}
			docs.add(doc);
		}

		try {
			solr.add(docs);
			// 对索引进行优化
			solr.optimize();
			solr.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteIndex(List<String> ids) {
		try {
			solr.deleteById(ids);
			solr.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
