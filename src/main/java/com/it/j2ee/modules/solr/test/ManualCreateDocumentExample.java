//package com.it.j2ee.modules.solr.test;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//import javax.xml.parsers.ParserConfigurationException;
//
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.search.TermQuery;
//import org.apache.lucene.search.TopDocs;
//import org.apache.solr.client.solrj.response.TermsResponse.Term;
//import org.apache.solr.core.CoreDescriptor;
//import org.apache.solr.core.SolrConfig;
//import org.apache.solr.core.SolrCore;
//import org.apache.solr.core.StandardDirectoryFactory;
//import org.apache.solr.schema.IndexSchema;
//import org.apache.solr.search.SolrIndexSearcher;
//import org.apache.solr.update.SolrIndexWriter;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//
//import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
//
//public class ManualCreateDocumentExample {
//
//	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException{  
//        System.setProperty("solr.solr.home","e:\\solrIndex");  
//            
//        //这下面三行代码主要是用于加载配置文件  
//        SolrConfig solrConfig = new SolrConfig("E:\\solrIndex\\conf\\solrconfig.xml");  
//        FileInputStream fis = new FileInputStream("E:\\solrIndex\\conf\\schema.xml");
//        InputSource is = null;
//        IndexSchema indexSchema = new IndexSchema(solrConfig,"solrconfig",is);  
//            
//        SolrIndexWriter siw = SolrIndexWriter.create("solrIndex","E:\\solrIndex",new StandardDirectoryFactory(),true,indexSchema,null,null,null);  
//        Document document = new Document();  
//        document.add(new Field("text","测试一下而已",Field.Store.YES,Field.Index.ANALYZED,Field.TermVector.WITH_POSITIONS_OFFSETS));  
//        document.add(new Field("test_t","再测试一下而已",Field.Store.YES,Field.Index.ANALYZED,Field.TermVector.WITH_POSITIONS_OFFSETS));  
//        siw.addDocument(document);  
//            
//        siw.commit();  
//        siw.close();  
//            
//        SolrCore solrCore = new SolrCore("E:\\solrIndex",new CoreDescriptor(null,null));  
//            
//        SolrIndexSearcher sis = new SolrIndexSearcher(solrCore,indexSchema,"solrIndex",  
//                        new StandardDirectoryFactory().open("E:\\solrIndex"),true);  
//        TopDocs docs = sis.search(new TermQuery(new Term("test_t","再")),1);  
//            
//        System.out.println("找到"+docs.totalHits+"个结果 ");  
//            
//        for (int i = 0; i < docs.scoreDocs.length; i++) {  
//            System.out.println(sis.doc(docs.scoreDocs[i].doc).get("test_t"));  
//        }  
//            
//    }
//}
