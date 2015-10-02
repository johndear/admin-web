package com.it.j2ee.base;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * 
 *〈一句话功能简述〉
 * 数据表格查询返回VO
 * @author liusu
 * @create 2014年10月29日 下午4:11:05 
 * @version 1.0.0
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DatagridVO {
	
	/**
	 * 总行数
	 */
	private Long total;
	
	/**
	 * 返回当前页面记录列表
	 */
	private List<?> rows;
	
	public DatagridVO(Long total,List<?> rows){
		this.total = total;
		this.rows = rows;
	}
	
	public DatagridVO(Page<?> pageResult){
		this.total = pageResult.getTotalElements();
		this.rows = pageResult.getContent();
	}
	
	public Long getTotal() {
		return total;
	}

	public List<?> getRows() {
		return rows;
	}
	
	
}
