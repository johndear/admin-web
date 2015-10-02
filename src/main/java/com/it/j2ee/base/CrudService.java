
/**    
 * Copyright (C),Kingmed
 * @FileName: AbstractCrudService.java  
 * @Package: com.kingmed.cs.modules.workorder.service.impl  
 * @Description: //模块目的、功能描述  
 * @Author Liusu  
 * @Date 2014年8月30日 上午9:52:07  
 * @History: //修改记录
 * 〈author〉      〈time〉      〈version〉       〈desc〉
 * 修改人姓名            修改时间            版本号              描述   
*/  

package com.it.j2ee.base;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;



/**  
 * service层对数据库单表操作提供的默认实现类
 *
 * @author liusu
 * @create 2014年8月30日 上午9:52:07 
 * @version 1.0.0
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class CrudService<T> {

	private BaseDao<T> baseDao;
	
	/**  
	 * 构造函数
	 * 注意事项：
	 * 子类实现该构造方法时应将baseDao改为相应dao的别名（具体可参考WorkOrderTemplateServiceImpl实现）
	 * 
	 * @param baseDao  
	*/ 
	public CrudService(BaseDao<T> baseDao) {
		super();
		this.baseDao = baseDao;
	}
	
	/**
	 * 新增操作
	 * @param t
	 */
	public T create(T t){
		return baseDao.save(t);
	}
	
	/**
	 * 批量新增操作
	 * @param t
	 */
	public Iterable<T> createBatch(Iterable<T> t){
		return baseDao.save(t);
	}
	
	/**
	 * 根据主键查询获取单个对象
	 * @param pk
	 * @return
	 */
	public T findByPK(String pk) {
		return baseDao.findOne(Long.parseLong(pk));
	}
	
	/**
	 * 根据主键查询获取单个对象
	 * @param pk
	 * @return
	 */
	public T findByLongPK(Long pk) {
		return baseDao.findOne(pk);
	}
	
	/**
	 * 更新操作
	 * @param t
	 */
	public T update(T t) {
		return baseDao.save(t);
	}
	
	/**
	 * 按主键删除
	 * @param pk
	 */
	public void delete(String pk) {
		baseDao.delete(Long.parseLong(pk));
	}
	
	/**
	 * 按对象条件删除
	 * @param
	 */
	public void delete(T t) {
		baseDao.delete(t);
	}
	
	/**
	 * 批量删除
	 * @param
	 */
	public void deleteBatch(Iterable<T> t) {
		baseDao.delete(t);
	}
	
	/**
	 * 按条件查询所有
	 * @param params
	 * @param beanClazz
	 * @return
	 */
	public List<T> findAll(Map<String, Object> params, Class<T> beanClazz) {
//		Map<String, SearchFilter> filters = SearchFilter.parse(params);
//		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), beanClazz);
		
		//扩展date日期区间查询
		Specification<T> spec = DynamicSpecificationsUtil.bySearchFilter(params, beanClazz);
				
		return baseDao.findAll(spec);
	}
	
	/**
	 * 按条件查询所有
	 * @param params
	 * @param beanClazz
	 * @return
	 */
	public List<T> findAll(Object obj, Class<T> beanClazz) {
		Map<String, Object> params = BeanUtil.transBean2Map(obj);		
		//扩展date日期区间查询
		Specification<T> spec = DynamicSpecificationsUtil.bySearchFilter(params, beanClazz);
				
		return baseDao.findAll(spec);
	}
	
	/**
	 * 按条件进行分页查询(参数为object)
	 * @param beanClazz
	 * @param obj
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param sortProperty
	 * @return
	 */
	public Page<T> findByParams(final Class<T> beanClazz, Object obj, int pageNumber,
			int pageSize, String sortType, String... sortProperty) {
		Sort sort = null;
		if (sortType != null && sortProperty != null
				&& sortProperty.length > 0
				&& StringUtils.isNotBlank(sortProperty[0])) {
			Direction d = null;
			if ("desc".equalsIgnoreCase(sortType)) {
				d = Direction.DESC;
			} else {
				d = Direction.ASC;
			}
			sort = new Sort(d, sortProperty);
		}
		PageRequest pageReq = new PageRequest(pageNumber - 1, pageSize, sort);
		
		Map<String, Object> params = BeanUtil.transBean2Map(obj);
		
//		Map<String, SearchFilter> filters = SearchFilter.parse(params);
//		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), beanClazz);
		
		//扩展date日期区间查询
		Specification<T> spec = DynamicSpecificationsUtil.bySearchFilter(params, beanClazz);
				
		return baseDao.findAll(spec, pageReq);
	}
	
	/**
	 * 按条件进行分页查询(参数为map)
	 * @param beanClazz
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param sortProperty
	 * @return
	 */
	public Page<T> findByParams(Class<T> beanClazz, Map<String, Object> params, int pageNumber, int pageSize, String sortType, String... sortProperty) {
		Sort sort = null;
		if (sortType != null && sortProperty != null
				&& sortProperty.length > 0
				&& StringUtils.isNotBlank(sortProperty[0])) {
			Direction d = null;
			if ("desc".equalsIgnoreCase(sortType)) {
				d = Direction.DESC;
			} else {
				d = Direction.ASC;
			}
			sort = new Sort(d, sortProperty);
		}
		PageRequest pageReq = new PageRequest(pageNumber - 1, pageSize, sort);
		
//		Map<String, SearchFilter> filters = SearchFilter.parse(params);
//		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), beanClazz);
		
		//扩展date日期区间查询
		Specification<T> spec = DynamicSpecificationsUtil.bySearchFilter(params, beanClazz);
		
		return baseDao.findAll(spec, pageReq);
	}
	
}
