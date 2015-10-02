package com.it.j2ee.modules.webservice.cxf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.j2ee.base.BaseDao;
import com.it.j2ee.base.CrudService;
import com.it.j2ee.modules.webservice.cxf.dao.WsInterfaceMessageDao;
import com.it.j2ee.modules.webservice.cxf.entity.WsInterfaceMessage;


@Service
@Transactional(readOnly = true)
public class WsInterfaceMessageService extends CrudService<WsInterfaceMessage> {

	@SuppressWarnings("unused") 
	private WsInterfaceMessageDao wsInterfaceMessageDao;
	
	 @Autowired
	public WsInterfaceMessageService(BaseDao<WsInterfaceMessage> wsInterfaceMessageDao) {
		super(wsInterfaceMessageDao);
		this.wsInterfaceMessageDao=(WsInterfaceMessageDao)wsInterfaceMessageDao;
	
	}
	 
	public  WsInterfaceMessage findByMessageId(String messageId){
		return wsInterfaceMessageDao.findByMessageId(messageId);
	}

	
	
}
