package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtils {
	private static String driverClass;
	private static String dburl;
	private static String user;
	private static String password;
	private static String characterEncoding;
	private JdbcUtils(){}
	
	static {
		Properties prop = new Properties();
		InputStream in = Object.class.getResourceAsStream("/jdbc.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driverClass = prop.getProperty("driverClass");
		dburl = prop.getProperty("dburl");
		user = prop.getProperty("user");
		password = prop.getProperty("password");
		characterEncoding = prop.getProperty("characterEncoding");
		
	}
	
	public static Connection getConnection() {
		Connection con=null;
		try {
			Class.forName(driverClass);
			dburl += "?user=" + user + "&password=" + password + "&useUnicode=true&characterEncoding=" + characterEncoding;
			con=DriverManager.getConnection(dburl);
		} catch (SQLException e) {
			throw new RuntimeException("connect database[connection_url:["
					+ dburl + "], user:[" + user + "], password:[" + password
					+ "]] failed, msg:" + e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("not found Class[" + e.getMessage() + "]", e);
		}
		return con;
	}
	
	public static void destroy(Connection con) {
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				throw new RuntimeException("connection close failed, msg:" + e.getMessage(), e);
			}
		}
	}
	public static void destroy(Connection con,Statement st) {
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				throw new RuntimeException("statement close failed, msg:" + e.getMessage(), e);
			}
		}
		destroy(con);
	}
	
	public static void destroy(Connection con,Statement st, ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException("resultset close failed, msg:" + e.getMessage(), e);
			}
		}
		destroy(con,st);
	}
}

