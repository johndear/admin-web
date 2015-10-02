package com.it.j2ee.modules.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


public class XMLUtils
{
	private Document document;
	
	private XMLUtils()
	{
	}
	
	public static XMLUtils newInstanceByFile(String file) throws Exception
	{
		SAXReader saxReader = new SAXReader();
		Document document = null;
		document = saxReader.read(new File(file));
		XMLUtils util = new XMLUtils();
		util.setDocument(document);
		return util;
	}
	public static XMLUtils newInstanceByStream(InputStream input,String encoding) throws Exception
	{
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding(encoding);
		Document document = null;
		document = saxReader.read(input);
		XMLUtils util = new XMLUtils();
		util.setDocument(document);
		return util;
	}
	public static XMLUtils newInstanceByStream(InputStream input) throws Exception
	{
		SAXReader saxReader = new SAXReader();
		Document document = null;
		document = saxReader.read(input);
		XMLUtils util = new XMLUtils();
		util.setDocument(document);
		return util;
	}
	
	public static XMLUtils newInstanceByString(String xmlString)
	{
		
		xmlString = xmlString.trim();
		if(!(xmlString.startsWith("<?xml")))
		{
			xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"+xmlString;
		}
		SAXReader saxReader = new SAXReader();
        saxReader.setEncoding("utf-8");
		Document document = null;
		try
		{
			document = saxReader.read(new ByteArrayInputStream(xmlString.getBytes()));
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		XMLUtils util = new XMLUtils();
		util.setDocument(document);
		return util;
	}
	
	public static XMLUtils newInstanceByString(String xmlString,String encoding) throws UnsupportedEncodingException
	{
		
		xmlString = xmlString.trim();
		if(!(xmlString.startsWith("<?xml")))
		{
			xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"+xmlString;
		}
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try
		{
			document = saxReader.read(new ByteArrayInputStream((new String(xmlString.getBytes(),encoding)).getBytes()));
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		XMLUtils util = new XMLUtils();
		util.setDocument(document);
		return util;
	}
	
	public static XMLUtils newInstanceByDocument(Document document)
	{
		XMLUtils util = new XMLUtils();
		util.setDocument(document);
		return util;
	}
	
	@SuppressWarnings("rawtypes")
	public List getNodesByXPath(String XPath)
	{	
		if(this.document != null)
		{
			return this.document.selectNodes(XPath);
		}
		return null;
		
	}
	
	public Node getSingleNodeByXPath(String XPath)
	{	
		if(this.document != null)
		{
			return this.document.selectSingleNode(XPath);
		}
		return null;
		
	}
	
	public static List getNodesByXPath(String dataXML, String XPath)
	{	
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try
		{
			document = saxReader.read(new ByteArrayInputStream(dataXML.getBytes()));
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		
		if(document != null)
		{
			return document.selectNodes(XPath);
		}
		return null;
		
	}
	
	public static List getNodesByXPath(Document document,String XPath)
	{	
		if(document != null)
		{
			return document.selectNodes(XPath);
		}
		return null;
		
	}
	
	public  String getXMLStringByXPath(String XPath)
	{
		org.dom4j.Element e = DocumentHelper.createElement("root");
		List l = document.selectNodes(XPath);
		Node n = (Node)l.get(0);
		return n.asXML();
	}


	public Document getDocument()
	{
		return document;
	}

	public void setDocument(Document document)
	{
		this.document = document;
	}
	
	public Element getRootElement()
	{
		return this.document.getRootElement();
	}
	
	
	public static void main(String[] args) {
		try{
			XMLUtils xl = XMLUtils.newInstanceByFile("E:\\Program Files\\tomcat-6.0.16\\webapps\\ResConfigAdaper\\WEB-INF\\classes\\config\\task\\hlr.xml");
			//xl.getDocument();= 
			Element e = (Element)xl.getSingleNodeByXPath("root//battery_query//tasks");
			e.elements("task");
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+e.attributeValue("name"));
			Element n = (Element)xl.getNodesByXPath("root//battery_query//tasks//task").get(0);
			//Element e = (Element)n;
			//System.out.println(n.element("taskConfig").element("sql").asXML());
			
		}catch(Exception e){
				e.printStackTrace();
		}
		
		
		
	}
}
