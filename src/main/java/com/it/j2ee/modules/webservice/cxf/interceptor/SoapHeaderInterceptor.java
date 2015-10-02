package com.it.j2ee.modules.webservice.cxf.interceptor;
//package ibm.gdmcc.scm.wsm.interceptor;
//
//import java.util.List;
//
//import javax.xml.namespace.QName;
//
//import org.apache.cxf.binding.soap.SoapHeader;
//import org.apache.cxf.binding.soap.SoapMessage;
//import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
//import org.apache.cxf.headers.Header;
//import org.apache.cxf.helpers.DOMUtils;
//import org.apache.cxf.interceptor.Fault;
//import org.apache.cxf.phase.Phase;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//public class SoapHeaderInterceptor extends AbstractSoapInterceptor
//{
//    public SoapHeaderInterceptor()
//    {
//        super(Phase.WRITE);
//    }
//
//    @Override
//    public void handleMessage(SoapMessage message) throws Fault
//    {
//        List headers=message.getHeaders(); 
//        headers.add(getHeader("Username", "root"));
//        headers.add(getHeader("Password", "92847F79ACBD87F1A5BAB25AE4FE1199"));
//    }
//
//    private Header getHeader(String key, String value) 
//    { 
//        QName qName=new QName("", key); 
//
//        Document document=DOMUtils.createDocument(); 
//        Element element=document.createElementNS("", key); 
//        element.setTextContent(value); 
//
//        SoapHeader header=new SoapHeader(qName, element); 
//        return(header); 
//    }
//
//
//}
