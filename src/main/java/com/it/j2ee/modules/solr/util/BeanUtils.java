package com.it.j2ee.modules.solr.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

public class BeanUtils {

	public static Map<String , String> convertMultiMap2Map(MultivaluedMap<String, String> multivaluedMap){
		Map<String , String> singleMap = new HashMap<String, String>();
		for (Entry<String, List<String>> entry : multivaluedMap.entrySet()) {
			singleMap.put(entry.getKey(), entry.getValue().get(0));
		}
		
		return singleMap;
	}
}
