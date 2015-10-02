package com.it.j2ee.modules.common.service;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.it.j2ee.modules.common.dao.DataDictDao;
import com.it.j2ee.modules.common.entity.DataDict;
import com.it.j2ee.modules.common.util.QueryUtil;

/**
 * 
 * @author leihong
 *
 */
@Service
public class DataDictService {

	@Autowired
	private DataDictDao dictDao;
	
	/**
	 * 根据字典类型获取字典List
	 * @param dictType
	 * @return
	 */
	public List<DataDict> getDict(String dictType){
		return dictDao.findDictByType(dictType);
	}
	
	/**
	 * 根据字典类型获取字典List
	 * @param dictType
	 * @return
	 */
	public List<DataDict> getDictAll(){
		return (List<DataDict>) dictDao.findAll();
	}

	/**
	 * 根据字典类型获取字典Map
	 * @param dictType
	 * @return
	 */
	public Map<String,String> getDictMap(String dictType){

		List<DataDict> dicts=this.getDict(dictType);
		SortedMap<String,String> map=new TreeMap<String,String>();
		for(DataDict dict:dicts){
			map.put(dict.getDictCode(), dict.getDictName());
		}
		return map;
	}
	
	public Page<DataDict> findDictCategoryPage(
			Map<String, Object> searchParams, int pageNumber, int pageSize,
			String direction, String... sortProperty) {
		PageRequest pageRequest = QueryUtil.buildPageRequest(pageNumber,
				pageSize, direction, sortProperty);
		Specification<DataDict> spec = QueryUtil.buildSpecification(
				searchParams, DataDict.class);

		return dictDao.findAll(spec, pageRequest);
	}
}
