/**
 * 
 */
package com.it.j2ee.modules.common.utils.sql;

/**
 * @author Administrator
 *
 */
public interface ISqlDialect {
	/**
	 * 
	 * @param pageCount
	 * @param pageSize
	 * @param sql
	 * @return 封装好的分页sql语句
	 */
	public String getLimitSql(int pageCount,int pageSize,String sql);
	
	/**
	 * 
	 * @param sql
	 * @return
	 */
	public String getCountSql(String sql);
}
