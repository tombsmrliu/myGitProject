package cn.edu.dlnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn{

	static String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
	static String user ="System";
	static String password ="Oracle123";
	//获取数据库连接函数	
	public static Connection getConnect(){
			Connection con =null;

			try {
				//加载驱动
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(url, user, password);
				
				
			} catch (ClassNotFoundException e) {	
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
	}
		/**测试用例
		public static void main(String[] args){
			Conn conn = new Conn();
			conn.getConnect();
			System.out.println("dghedrh");
		}
		*/
		
}

