/**    
 * Copyright (C),Kingmed
 * @FileName: BeanUtil.java  
 * @Package: com.kingmed.common.util  
 * @Description: //模块目的、功能描述  
 * @Author zhaojianhua  
 * @Date 2014年8月29日 下午4:06:30  
 * @History: //修改记录
 * 〈author〉      〈time〉      〈version〉       〈desc〉
 * 修改人姓名            修改时间            版本号              描述   
 */

package com.it.j2ee.base;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉 Bean工具类
 * 
 * @author liusu
 * @create 2014年8月29日 下午4:06:30
 * @version 1.0.0
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */

public class BeanUtil {

	private static final Logger LOG = LoggerFactory.getLogger(BeanUtil.class);

	static String BEGIN_DATE_PREFIX = "start_";
	static String END_DATE_PREFIX = "end_";

	/**
	 * 
	 * 利用Introspector和PropertyDescriptor 将Bean --> Map
	 * 
	 * @propertyValue obj
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	public static Map<String, Object> transBean2Map(Object obj) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		bean2Map(obj, paramsMap, null);

		Map<String, Object> filterMap = new HashMap<String, Object>();
		for (Entry<String, Object> entrys : paramsMap.entrySet()) {
			String fieldName = entrys.getKey();
			Object fieldValue = entrys.getValue();
			if (fieldValue != null) {
				String[] values = StringUtils.split(String.valueOf(fieldValue), "_");
				if (values != null && values.length > 0) {
					// EQ, LIKE, GT, LT, GTE, LTE, BWD
					String prefix = values[0];
					if ("EQ".equals(prefix) || "LIKE".equals(prefix)|| "GT".equals(prefix) || "LT".equals(prefix)|| "GTE".equals(prefix) || "LTE".equals(prefix) || "BWD".equals(prefix)) {
						if("GTE".equals(prefix)||"GT".equals(prefix)){
							//删除start_
							fieldName = fieldName.substring(6);
						}else if("LTE".equals(prefix)||"LT".equals(prefix)){
							//删除end_
							fieldName = fieldName.substring(4);
						}
						filterMap.put(prefix + "_" + fieldName, values[1]);
					} else {
						filterMap.put("EQ_" + fieldName, fieldValue);
					}
				}
			}
		}

		for (Entry<String, Object> entrys : filterMap.entrySet()) {
			LOG.info(entrys.getKey() + " = " + entrys.getValue());
		}

		return filterMap;
	}

	/**
	 * javabean递归调用对象属性，用"."符号拼接
	 * 
	 * @param obj
	 * @param map
	 * @param parentNodeName
	 */
	private static void bean2Map(Object obj, Map<String, Object> map, String parentNodeName) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				// 得到property对应的getter方法
				Method getter = property.getReadMethod();
				String propertyName = property.getName();
				
				
				Object propertyValue = getter.invoke(obj);
				
				// 参数值为空的不处理
				if (propertyName.equals("class") || propertyValue == null) {
					continue;
				}
				
				Field field = obj.getClass().getDeclaredField(propertyName);
				Transient excel = field.getAnnotation(Transient.class);
				if(excel!=null){
					continue;
				}

				// 过滤掉基本类型默认值不作为查询条件
				// 所以建议使用Long、Integer、Double类型声明字段就不需要这段逻辑进行判断
				if (getter.getReturnType() == long.class|| getter.getReturnType() == int.class || getter.getReturnType() == double.class) {
					if ("0".equals(String.valueOf(propertyValue))) {
						propertyValue = null;
						continue;
					}
				}

				String key = StringUtils.isNotBlank(parentNodeName) ? parentNodeName + "." + propertyName : propertyName;
				if (propertyValue instanceof Integer) {
					map.put(key, propertyValue);
					
				} else if (propertyValue instanceof String) {
					map.put(key, propertyValue);
					
				} else if (propertyValue instanceof Double) {
					map.put(key, propertyValue);
					
				}else if (propertyValue instanceof Byte) {
					map.put(key, propertyValue);
					
				} else if (propertyValue instanceof Float) {
					map.put(key, propertyValue);
					
				} else if (propertyValue instanceof Long) {
					map.put(key, propertyValue);
					
				}else if (propertyValue instanceof Short) {
					map.put(key, propertyValue);
					
				} else if (propertyValue instanceof Boolean) {
					map.put(key, propertyValue);
					
				} else if (propertyValue instanceof Date) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					// 日期类型暂时只支持区间查询 , 如：按createtime区间查询，只需要传递参数start_createtime（必填项）和 end_createtime（可选）
					if(propertyName.startsWith(BEGIN_DATE_PREFIX) || propertyName.startsWith(END_DATE_PREFIX)){
						propertyName = propertyName.split("_")[1];
						String startTimeFieldName = BEGIN_DATE_PREFIX + propertyName;
						String endTimeFieldName = END_DATE_PREFIX + propertyName;
						if(containsFiled(obj.getClass(), propertyName) && containsFiled(obj.getClass(), startTimeFieldName) && containsFiled(obj.getClass(), endTimeFieldName)){
							// 时间字段名需要截取掉"start_"||"end_"作为查询条件
							key = StringUtils.isNotBlank(parentNodeName) ? parentNodeName + "." + propertyName : propertyName;
							// 时间区间处理
							Object startTimeFieldValue = getFiledValue(obj,startTimeFieldName);
							Object endTimeFieldValue = getFiledValue(obj,endTimeFieldName);
							if (startTimeFieldValue != null) {
								String startTime = format.format(startTimeFieldValue);
								String endTime = format.format(endTimeFieldValue == null ? new Date() : endTimeFieldValue);
								map.put(key, "BWD_" + startTime + "," + endTime);
							}
							// 将开始/结束时间设置为空
							setFiledValue(obj, startTimeFieldName, null);
							setFiledValue(obj, endTimeFieldName, null);
						}
						
					}else{
						String startTimeFieldName = BEGIN_DATE_PREFIX + propertyName;
						String endTimeFieldName = END_DATE_PREFIX + propertyName;
						if (containsFiled(obj.getClass(), startTimeFieldName) && containsFiled(obj.getClass(), endTimeFieldName)) {
							// 处理逻辑同上。
							Object startTimeFieldValue = getFiledValue(obj,startTimeFieldName);
							Object endTimeFieldValue = getFiledValue(obj,endTimeFieldName);
							if (startTimeFieldValue != null) {
								String startTime = format.format(startTimeFieldValue);
								String endTime = format.format(endTimeFieldValue == null ? new Date() : endTimeFieldValue);
								map.put(key, "BWD_" + startTime + "," + endTime);
							}
							// 将开始/结束时间设置为空
							setFiledValue(obj, startTimeFieldName, null);
							setFiledValue(obj, endTimeFieldName, null);
						} else { 
							// 其他日期值暂不处理
							map.put(key, null);
						}
					}
					
				} else if (propertyValue instanceof List) {
					// list不作为查询条件
					map.put(key, null);
					
				} else if (propertyValue instanceof Set<?>) {
					// set不作为查询条件
					map.put(key, null);
					
				} else {
					// 递归调用
					bean2Map(propertyValue, map, key);
					
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
	}

	public static boolean containsFiled(Class<? extends Object> clazz, String filedname) throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (filedname.equals(key)) {
				return true;
			}
		}
		return false;
	}

	public static Object getFiledValue(Object obj, String filedname) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (filedname.equals(key)) {
				Method getter = property.getReadMethod();
				Object value = getter.invoke(obj);
				return value;
			}
		}
		return null;
	}

	public static void setFiledValue(Object obj, String filedname, Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (filedname.equals(key)) {
				Method setter = property.getWriteMethod();
				setter.invoke(obj, value);
			}
		}
	}
	 
    public static Map<String, Object> transBeanToMap(Object obj) {  
    	Map<String, Object> map = new HashMap<String, Object>();  
        if(obj == null){  
            return map;  
        }          
        
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
            	Method getter = property.getReadMethod();
				//String propertyName = property.getName();
				
				
				Object propertyValue = getter.invoke(obj);
                // 过滤class属性  
                if (!key.equals("class") && propertyValue != null && !"".equals(propertyValue)) {  
                    // 得到property对应的getter方法  
                   // Method getter = property.getReadMethod();  
                   // Object value = getter.invoke(obj);  
  
                    map.put(key, propertyValue);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    }  
	
	
}
