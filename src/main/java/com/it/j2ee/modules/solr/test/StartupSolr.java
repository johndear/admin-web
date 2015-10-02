package com.it.j2ee.modules.solr.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.codecs.lucene50.Lucene50Codec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.CoreDescriptor;
import org.apache.solr.core.SolrConfig;
import org.apache.solr.core.SolrCore;
import org.apache.solr.core.SolrDeletionPolicy;
import org.apache.solr.core.StandardDirectoryFactory;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.search.SolrIndexSearcher;
import org.apache.solr.update.SolrIndexConfig;
import org.apache.solr.update.SolrIndexWriter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 研究solr启动过程
 * @author Administrator
 *
 */
public class StartupSolr {

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
		String solrHome = "D:\\A-Soft\\solr-5.2.1\\server\\solr\\new_core";
		System.setProperty("solr.solr.home", solrHome);  
		
//		SolrResourceLoader loader = new SolrResourceLoader(solrHome + "\\new_core");
//		NodeConfigBuilder builder =new NodeConfigBuilder("new_core", loader);
//		builder.setShardHandlerFactoryConfig(new PluginInfo(null,null,true,true));
//		NodeConfig noteConfig = builder.build();
//		CoreContainer container = new CoreContainer(noteConfig);
		CoreContainer container = new CoreContainer("D:\\A-Soft\\solr-5.2.1\\server\\solr");
		
//		ShardHandler shardHandler = container.getShardHandlerFactory().getShardHandler();  
		
		SolrConfig config = new SolrConfig(solrHome + "\\conf\\solrconfig.xml"); 
		IndexSchema schema = new IndexSchema(config, "schema.xml", new InputSource(new FileInputStream(solrHome + "\\conf\\schema.xml")));
		CoreDescriptor dcore = new CoreDescriptor(container,"new_core", solrHome + "\\new_core", new Properties());
		SolrCore solrCore = new SolrCore("test", null, config, schema, dcore);
		
		SolrIndexConfig solrIndexConfig = new SolrIndexConfig(config,null,null);
		StandardDirectoryFactory directFactory = new StandardDirectoryFactory();
//		SolrIndexWriter siw = SolrIndexWriter.create(solrCore,"solrIndex","D:\\A-Soft\\solr-5.2.1\\server\\solr\\new_core\\data\\index", directFactory,true,schema,solrIndexConfig,new SolrDeletionPolicy(),new Lucene50Codec());
//		Document document = new Document();
////		document.add(new Field("id","666",Field.Store.YES,Field.Index.ANALYZED,Field.TermVector.WITH_POSITIONS_OFFSETS));
//		document.add(new Field("name","测试一下而已",Field.Store.YES,Field.Index.ANALYZED,Field.TermVector.WITH_POSITIONS_OFFSETS));  
//		document.add(new Field("actions","再测试一下而已",Field.Store.YES,Field.Index.ANALYZED,Field.TermVector.WITH_POSITIONS_OFFSETS));  
//		siw.addDocument(document);  
//		siw.commit();  
//		siw.close();  
		
		SolrIndexSearcher sis = new SolrIndexSearcher(solrCore,"D:\\A-Soft\\solr-5.2.1\\server\\solr\\new_core\\data\\index",schema,solrIndexConfig,"solrIndex", true,directFactory);  
		TopDocs docs = sis.search(new TermQuery(new Term("name","测试")),1);
		
		System.out.println("找到"+docs.totalHits+"个结果 ");  
        
        for (int i = 0; i < docs.scoreDocs.length; i++) {  
            System.out.println(sis.doc(docs.scoreDocs[i].doc).get("test_t"));  
        }  
	}

}
