package com.ls.quartz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * <p>
 * Title: JDBC连接数据库
 * </p>
 * <p>
 * Description: 本实例演示如何使用JDBC连接Oracle数据库，并演示添加数据和查询数据。
 * </p>
 */
public class JdbcUtil {

	private static String driver="";
	private static String url = "";// 数据库连接字符串
	private static String username = "";// 数据库用户名
	private static String password = "";// 数据库密码

	static{
		try {
			Properties prop  = new Properties();
			prop.load(JdbcUtil.class.getClassLoader().getResourceAsStream("quartz.properties"));
			
			JdbcUtil.driver=prop.getProperty("org.quartz.dataSource.qzDS.driver");
			JdbcUtil.url = prop.getProperty("org.quartz.dataSource.qzDS.URL");
			JdbcUtil.username = prop.getProperty("org.quartz.dataSource.qzDS.user");
			JdbcUtil.password = prop.getProperty("org.quartz.dataSource.qzDS.password");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	/**
	 *<br>
	 * 方法说明：获得数据连接 <br>
	 * 输入参数： <br>
	 * 返回类型：Connection 连接对象
	 */
	public static Connection getConnection() {
		try {
			// 第一步：加载JDBC驱动
			Class.forName(driver);
			// 第二步：创建数据库连接
			Connection con = DriverManager.getConnection(url, username,password);
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
	 *<br>
	 * 方法说明：执行查询SQL语句 <br>
	 * 输入参数：Connection con 数据库连接 <br>
	 * 输入参数：String sql 要执行的SQL语句 <br>
	 * 返回类型：void
	 */
	public static ResultSet query(Connection con, String sql, Object... args) {
		try {
			if (con == null) {
				throw new Exception("database connection can't use!");
			}
			if (sql == null)
				throw new Exception(
						"check your parameter: 'sql'! don't input null!");
			// 第三步：获取Staetment对象
			PreparedStatement stmt = con.prepareStatement(sql);  
            for (int i = 0; i < args.length; i++) {  
            	stmt.setObject(i + 1, args[i]);  
            }
			// 第四步：执行数据库操作（查询操作）
			ResultSet rs = stmt.executeQuery();
//			// 第五步：处理结果集
//			ResultSetMetaData rmeta = rs.getMetaData();
//			// 获得数据字段个数
//			int numColumns = rmeta.getColumnCount();
//			while (rs.next()) {
//				for (int i = 0; i < numColumns; i++) {
//					String sTemp = rs.getString(i + 1);
//					System.out.print(sTemp + "  ");
//				}
//				System.out.println("");
//			}
			return rs;
		} catch (Exception e) {
			System.out.println("query error:" + e);
		}
		return null;
	}
	
	/** 
     * 根据传进来的sql语句,来跟新数据库的表记录 
     *  
     * @param sql 
     * @param args 
     */  
    public static void update(String sql, Object[] args) {
    	Connection conn = null;
    	PreparedStatement pstate = null;
    	ResultSet rs = null;
        try {  
            if (args.length < 0) {  
                throw new RuntimeException("Object[] args的数组长度,不应该为空");  
            }  
            conn = getConnection();  
            pstate = conn.prepareStatement(sql);  
            for (int i = 0; i < args.length; i++) {  
                pstate.setObject(i + 1, args[i]);  
            }  
            // 注意要调用这个方法  
            pstate.executeUpdate();  
  
        } catch (Exception e) {  
            throw new RuntimeException("更新数据库中的记录失败" + e);  
        } finally {  
            // 释放资源,关闭与数据库的连接  
            release(conn, pstate, rs);  
        }  
    } 
    
    /** 
     * 根据sql语句来更新数据库表中的记录 
     *  
     * @param sql 
     */  
    public static void update(String sql) { 
    	Connection conn = null;
    	PreparedStatement pstate = null;
    	ResultSet rs = null;
        try {  
            conn = getConnection();  
            pstate = conn.prepareStatement(sql);  
            // 注意要调用这个方法  
            pstate.executeUpdate();  
  
        } catch (Exception e) {  
            throw new RuntimeException("更新数据库中的记录失败" + e);  
        } finally {  
            // 释放资源,关闭与数据库的连接  
            release(conn, pstate, rs);  
        }  
    }  
    
    /** 
     * 释放数据库的连接 
     *  
     * @param conn 
     * @param st 
     * @param rs 
     */  
    public static void release(Connection conn, Statement st, ResultSet rs) {  
  
        if (rs != null) {  
            try {  
                rs.close();  
            } catch (Exception e) {  
                throw new RuntimeException("ResultSet关闭异常");  
            }  
            rs = null;  
        }  
        if (st != null) {  
            try {  
                st.close();  
            } catch (Exception e) {  
                throw new RuntimeException("Statement关闭异常");  
            }  
            st = null;  
        }  
        if (conn != null) {  
            try {  
                conn.close();  
            } catch (Exception e) {  
                throw new RuntimeException("Connection关闭异常");  
            }  
            conn = null;  
        }  
  
    }

	/**
	 *<br>
	 * 方法说明：执行插入、更新、删除等没有返回结果集的SQL语句 <br>
	 * 输入参数：Connection con 数据库连接 <br>
	 * 输入参数：String sql 要执行的SQL语句 <br>
	 * 返回类型：void
	 */
	public static void execute(Connection con, String sql) {
		try {
			if (con == null){
				return;
			}
			// 第三步：获取Statement对象
			Statement stmt = con.createStatement();
			// 第四步：执行数据库操作（更新操作）
			stmt.executeUpdate(sql);
			con.commit();
			con.close();
			System.out.println("update executed successly");
		} catch (Exception e) {
			System.out.println("execute error: sql = " + sql);
			System.out.println(e);
		}
		// end try catch
	}// end execute

	/**
	 *<br>
	 * 方法说明：实例演示 <br>
	 * 输入参数：无 <br>
	 * 返回类型：void
	 */
	public void demo() {
		String sSQL = "";
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("please input update SQL string");
			sSQL = stdin.readLine();// 获取命令行输入（更新字符串）
			Connection conn = JdbcUtil.getConnection();// 执行自定义连接方法（获取数据库连接对象）
			execute(conn, sSQL);// 执行自定义更新方法
			String sql = "select * from TBL_USER";
			ResultSet rs = query(conn, sql, "");// 执行自定义查询方法（查询并处理结果集）
			System.out.println(rs);
			//第六步：关闭数据库连接
			conn.close();
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

//	/**
//	 *<br>
//	 * 方法说明：主方法 <br>
//	 * 输入参数：String[] args 命令行参数（包括：数据库连接URL， <br>
//	 * 用户名，密码） <br>
//	 * 返回类型：void
//	 * @throws URISyntaxException 
//	 * @throws IOException 
//	 * @throws FileNotFoundException 
//	 */
//	public static void main(String[] arg) throws FileNotFoundException, IOException, URISyntaxException {
//		Properties prop  = new Properties();
//		prop.load(new FileInputStream(new File(JdbcUtils.class.getClassLoader().getResource("com/it/jdbc/jdbc.properties").toURI())));
//		
//		JdbcUtils oc = new JdbcUtils();
////		oc.driver="oracle.jdbc.driver.OracleDriver";
//		oc.driver=prop.getProperty("idb.jdbc.driverClassName");
//		oc.url = prop.getProperty("idb.jdbc.url");
//		oc.username = prop.getProperty("idb.jdbc.username");
//		oc.password = prop.getProperty("idb.jdbc.password");
////		oc.demo();
//		
//		Connection conn = JdbcUtils.getConnection();// 执行自定义连接方法（获取数据库连接对象）
//		String sql = "select * from ylxwjg.tb_emr";
//		query(conn, sql);// 执行自定义查询方法（查询并处理结果集）
//		
//	}

}
