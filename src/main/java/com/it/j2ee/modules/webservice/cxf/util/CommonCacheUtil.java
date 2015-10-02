/**
 * 
 */
package com.it.j2ee.modules.webservice.cxf.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.it.j2ee.modules.webservice.cxf.dao.EndpointDao;
import com.it.j2ee.modules.webservice.cxf.entity.Endpoint;

/**
 * 公共缓存工具类
 * @author
 *
 */
@Component
public class CommonCacheUtil {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    EndpointDao endpointDao;

    private Map<String, Endpoint> endpointMap = new HashMap<String, Endpoint>();
  
	@PostConstruct
    public void initialize() {
    	
        logger.info("CommonCacheUtil --> initialize --> start");
        
    	List<Endpoint> pList = (List<Endpoint>) endpointDao.findAll();
    	
    	for (Endpoint endpoint : pList) {
    		endpointMap.put(endpoint.getKind(), endpoint);
		}
       
        logger.info("CommonCacheUtil --> initialize  -->  end");
    }



	public Map<String, Endpoint> getEndpointMap() {
		return endpointMap;
	}



	public void setEndpointMap(Map<String, Endpoint> endpointMap) {
		this.endpointMap = endpointMap;
	}
	
	public void clearData(){
		endpointMap.clear();
		
		initialize();
		
	}

  
}
