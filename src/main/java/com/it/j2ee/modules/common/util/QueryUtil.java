/**
 * 
 */
package com.it.j2ee.modules.common.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author leihong
 *
 */
public abstract class QueryUtil {

	/**
	 * 
	 * 构造函数
	 */
	private QueryUtil() {
	}

	/**
	 * 创建分页请求. direction且sortProperty不为空才创建排序器
	 * 
	 * @param pageNumber
	 *            页码：从1开始
	 * @param pagzSize
	 *            每页大小
	 * @param direction
	 *            顺序或降序：取值{DESC,ASC},不区分大小写
	 * @param sortProperty
	 *            需排序的字典
	 * @return
	 */
	public static PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String direction, String... sortProperty) {

		Sort sort = null;
		if (direction != null && sortProperty != null
				&& sortProperty.length > 0
				&& StringUtils.isNotBlank(sortProperty[0])) {
			Direction d = null;
			if ("desc".equalsIgnoreCase(direction)) {
				d = Direction.DESC;
			} else {
				d = Direction.ASC;
			}
			sort = new Sort(d, sortProperty);
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 * 
	 * @param searchParams
	 *            查询参数，key为"操作符_字段名"，value为查询的值<br/>
	 *            如key为EQ_type,value为1<br/>
	 *            操作符枚举范围：{EQ, LIKE, GT, LT, GTE, LTE}<br/>
	 * @see org.springside.modules.persistence.SearchFilter.Operator
	 * @param beanClazz
	 *            实体类Class
	 * @return
	 */
	public static <E> Specification<E> buildSpecification(
			Map<String, Object> searchParams, Class<E> beanClazz) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<E> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), beanClazz);
		return spec;
	}

}
