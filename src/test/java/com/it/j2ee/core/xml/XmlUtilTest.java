package com.it.j2ee.core.xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.it.j2ee.core.util.XmlUtil;

public class XmlUtilTest {

	public static void main(String[] args) throws ParserConfigurationException {
		String xsdPath = "D:\\test.xsd";
		String xmlPath = "D:\\testXsd1.xml";

		try {
			if (XmlUtil.validateXmlByXsd(xsdPath, xmlPath)) {
				System.out.println("校验通过");
			}
		} catch (SAXException e) {
			System.out.println("校验失败");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("校验失败");
			e.printStackTrace();
		}
		
		xmlPath = "D:\\testDtd1.xml";

		try {
			if (XmlUtil.validateXmlByDtd(xmlPath)) {
				System.out.println("校验通过");
			}
		} catch (SAXException e) {
			System.out.println("校验失败");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("校验失败");
			e.printStackTrace();
		}

	}
}
