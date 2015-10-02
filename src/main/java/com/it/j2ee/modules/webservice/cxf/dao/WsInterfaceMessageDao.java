package com.it.j2ee.modules.webservice.cxf.dao;



import org.springframework.data.jpa.repository.Query;

import com.it.j2ee.base.BaseDao;
import com.it.j2ee.modules.webservice.cxf.entity.WsInterfaceMessage;

public interface WsInterfaceMessageDao extends BaseDao<WsInterfaceMessage> {
	
	 @Query("select o from WsInterfaceMessage o where o.messageId =?1")
	 WsInterfaceMessage findByMessageId(String messageId);

}
