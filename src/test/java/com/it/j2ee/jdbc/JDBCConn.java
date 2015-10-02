package com.it.j2ee.jdbc;

import java.sql.*;
import java.io.*;

/**
 * <p>
 * Title: JDBC连接数据库
 * </p>
 * <p>
 * Description: 本实例演示如何使用JDBC连接Oracle数据库，并演示添加数据和查询数据。
 * </p>
 */
public class JDBCConn {
//	private String url = "jdbc:oracle:thin:@192.168.137.130:1521:scmdev";
//	private String username = "scm";
//	private String password = "scm12345";
	
	private String url = "jdbc:oracle:thin:@192.168.1.105:1521:orcl";
	private String username = "ydscm";
	private String password = "ydscm12345";

	/**
	 * <br>
	 * 方法说明：获得数据连接 <br>
	 * 输入参数： <br>
	 * 返回类型：Connection 连接对象
	 */
	public Connection conn() {
		try {
			// 第一步：加载JDBC驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 第二步：创建数据库连接
			Connection con = DriverManager.getConnection(url, username,
					password);
			return con;
		} catch (ClassNotFoundException cnf) {
			System.out.println("driver not find:" + cnf);
			return null;
		} catch (SQLException sqle) {
			System.out.println("can't connection db:" + sqle);
			return null;
		} catch (Exception e) {
			System.out.println("Failed to load JDBC/ODBC driver.");
			return null;
		}
	}

	/**
	 * <br>
	 * 方法说明：执行查询SQL语句 <br>
	 * 输入参数：Connection con 数据库连接 <br>
	 * 输入参数：String sql 要执行的SQL语句 <br>
	 * 返回类型：void
	 */
	public void query(Connection con, String sql) {
		try {
			if (con == null) {
				throw new Exception("database connection can't use!");
			}
			if (sql == null)
				throw new Exception(
						"check your parameter: 'sql'! don't input null!");
			// 第三步：获取Staetment对象
			Statement stmt = con.createStatement();
			// 第四步：执行数据库操作（查询操作）
			ResultSet rs = stmt.executeQuery(sql);
			// 第五步：处理结果集
			ResultSetMetaData rmeta = rs.getMetaData();
			// 获得数据字段个数
			int numColumns = rmeta.getColumnCount();
			while (rs.next()) {
				for (int i = 0; i < numColumns; i++) {
					String sTemp = rs.getString(i + 1);
					System.out.print(sTemp + "  ");
				}
				System.out.println("");
			}
		} catch (Exception e) {
			System.out.println("query error:" + e);
		}
	}

	/**
	 * <br>
	 * 方法说明：执行插入、更新、删除等没有返回结果集的SQL语句 <br>
	 * 输入参数：Connection con 数据库连接 <br>
	 * 输入参数：String sql 要执行的SQL语句 <br>
	 * 返回类型：void
	 */
	public void execute(Connection con, String sql) {
		try {
			if (con == null)
				return;
			// 第三步：获取Statement对象
			Statement stmt = con.createStatement();
			// 第四步：执行数据库操作（更新操作）
			stmt.executeUpdate(sql);
			System.out.println("update executed successly");
		} catch (Exception e) {
			System.out.println("execute error: sql = " + sql);
			System.out.println(e);
		}
	}

	// end try catch}//end execute
	/**
	 * <br>
	 * 方法说明：实例演示 <br>
	 * 输入参数：无 <br>
	 * 返回类型：void
	 */
	public void demo() {
		String sSQL = "";
//		BufferedReader stdin = new BufferedReader(new InputStreamReader(
//				System.in));
		try {
			System.out.println("please input update SQL string");
			//sSQL = stdin.readLine();// 获取命令行输入（更新字符串）
			Connection conn = conn();// 执行自定义连接方法（获取数据库连接对象）
			//execute(conn, sSQL);// 执行自定义更新方法
			String sql = "select * from contract_inf";
			query(conn, sql);// 执行自定义查询方法（查询并处理结果集）//第六步：关闭数据库连接
			conn.close();
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * <br>
	 * 方法说明：主方法 <br>
	 * 输入参数：String[] args 命令行参数（包括：数据库连接URL， <br>
	 * 用户名，密码） <br>
	 * 返回类型：void
	 */
	public static void main(String[] arg) {
//		if (arg.length != 3) {
			System.out.println("use: java JDBCConn url username password");
			JDBCConn oc = new JDBCConn();
//			oc.url = arg[0];
//			oc.username = arg[1];
//			oc.password = arg[2];
			oc.demo();
//		}
	}
}
