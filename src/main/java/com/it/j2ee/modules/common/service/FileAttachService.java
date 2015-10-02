package com.it.j2ee.modules.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.j2ee.modules.common.dao.FileAttachDao;
import com.it.j2ee.modules.common.entity.FileAttach;

@Service
public class FileAttachService {
	
	@Autowired
	private FileAttachDao fileAttachDao;

	public List<FileAttach> findByforeignIdAndbizCode(String foreignId, String bizCode) {
		return fileAttachDao.findByForeignIdAndBizCode(foreignId, bizCode);
	}

}
