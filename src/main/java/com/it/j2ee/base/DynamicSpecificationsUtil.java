package com.it.j2ee.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.j2ee.modules.common.util.Collections3;

/**
 * 对DynamicSpecifications扩展date日期区间查询
 * @author Administrator
 *
 */
public class DynamicSpecificationsUtil {
	
	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE, BWD
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public DynamicSpecificationsUtil(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public static <T> Specification<T> bySearchFilter(final Map<String, Object> params, Class<T> entityClazz) {
		Map<String, DynamicSpecificationsUtil> searchParams = parse(params);
		final Collection<DynamicSpecificationsUtil> filters = searchParams.values();
		return new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (Collections3.isNotEmpty(filters)) {

					List<Predicate> predicates = Lists.newArrayList();
					for (DynamicSpecificationsUtil filter : filters) {
						// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
						String[] names = StringUtils.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						// logic operator
						switch (filter.operator) {
						case EQ:
							predicates.add(builder.equal(expression, filter.value));
							break;
						case LIKE:
							predicates.add(builder.like(expression, "%" + filter.value + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) filter.value));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case BWD:
							String dateString = (String) filter.value;
							String[] dates = dateString.split(",");
							
							SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
							Date startTime = null;
							Date endTime = null;
							try {
								startTime = dft.parse(dates[0]);
								endTime = dft.parse(dates[1]);
							} catch (ParseException e) {
							}
							Expression<Date> start = builder.literal(startTime);
							Expression<Date> end = builder.literal(endTime); 
							predicates.add(builder.between(expression, start, end));
							break;
						}
					}

					// 将所有条件用 and 联合起来
					if (!predicates.isEmpty()) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
	}
	
	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	private static Map<String, DynamicSpecificationsUtil> parse(Map<String, Object> params) {
		Map<String, DynamicSpecificationsUtil> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : params.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (StringUtils.isBlank(String.valueOf(value))) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			Operator operator = Operator.valueOf(names[0]);

			// 创建searchFilter
			DynamicSpecificationsUtil filter = new DynamicSpecificationsUtil(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}

}
