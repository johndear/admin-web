package com.it.j2ee.modules.common.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.it.j2ee.modules.common.entity.DataDict;

@Repository
public interface DataDictDao extends PagingAndSortingRepository<DataDict, Serializable>,JpaSpecificationExecutor<DataDict>{

	@Query("from DataDict t where t.dictType = :dictType and t.status = 1 order by t.dictDisplayOrder asc")
	List<DataDict> findDictByType(@Param("dictType")String dictType);
}
