package com.it.j2ee.modules.webservice.cxf.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.j2ee.base.DynamicSpecificationsUtil;
import com.it.j2ee.modules.common.util.QueryUtil;
import com.it.j2ee.modules.webservice.cxf.dao.WsInterfaceLogDao;
import com.it.j2ee.modules.webservice.cxf.entity.WsInterfaceLog;

@Service("wsInterfaceLogService")
@Transactional(readOnly = true)
public class WsInterfaceLogService {

    @Autowired
    private WsInterfaceLogDao wsInterfaceLogDao;

    public Page<WsInterfaceLog> findLogPage(Map<String, Object> searchParams, int pageNo, int pageSize, String direction, String... sortProperty) {
        //扩展date日期区间查询
        Specification<WsInterfaceLog> spec = DynamicSpecificationsUtil.bySearchFilter(searchParams, WsInterfaceLog.class);

        PageRequest pageRequest = QueryUtil.buildPageRequest(pageNo, pageSize, direction, sortProperty);
        return wsInterfaceLogDao.findAll(spec, pageRequest);
    }
    
    @Transactional
    public void save(WsInterfaceLog log){
    	wsInterfaceLogDao.save(log);
    }
}
