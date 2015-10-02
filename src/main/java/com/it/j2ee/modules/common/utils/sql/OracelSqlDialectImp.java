/**
 * 
 */
package com.it.j2ee.modules.common.utils.sql;

import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 *
 */
@Repository("oracelSqlDialectImp")
public class OracelSqlDialectImp implements ISqlDialect {
	public String getLimitSql(int pageCount, int pageSize, String sql) {
		StringBuffer limitSql = new StringBuffer(sql.length() + 100);
		limitSql.append("SELECT * FROM (SELECT rownum AS row_num, a.*  FROM (");
		limitSql.append(sql);
		limitSql.append(") a  WHERE rownum <=");
		limitSql.append(pageCount * pageSize);
		limitSql.append(" )b where  b.row_num > ");
		limitSql.append((pageCount - 1) * pageSize);
		return limitSql.toString();
	}

	public String getCountSql(String sql) {
		String queryCountSql = " select count(*) from (" + sql + ") tds";
		return queryCountSql;
	}
}
