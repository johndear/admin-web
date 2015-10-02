package com.it.j2ee.modules.common.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.it.j2ee.modules.common.entity.FileAttach;

@Repository
public interface FileAttachDao extends PagingAndSortingRepository<FileAttach, Serializable>,JpaSpecificationExecutor<FileAttach>{

	public List<FileAttach> findByForeignIdAndBizCode(String foreignId, String bizCode);

}
