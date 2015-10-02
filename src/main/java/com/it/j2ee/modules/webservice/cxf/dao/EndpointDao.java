package com.it.j2ee.modules.webservice.cxf.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.it.j2ee.modules.webservice.cxf.entity.Endpoint;

@Repository
public interface EndpointDao extends PagingAndSortingRepository<Endpoint, Serializable>,JpaSpecificationExecutor<Endpoint>{

}
