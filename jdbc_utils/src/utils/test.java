package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection con=JdbcUtils.getConnection();
		Statement st = null;
		String sql="insert into test1 values('����')";
		try {
			st = con.prepareStatement(sql);
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("������");
		JdbcUtils.destroy(con,st);

	}

}
