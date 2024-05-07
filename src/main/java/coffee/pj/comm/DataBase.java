package coffee.pj.comm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//DataBase 드라이버 로딩, 접속, 닫기 공통 모듈
public class DataBase {

	public DataBase() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}//Oracle Driver 로딩
	
	/**
	 * RDBMS를 연결하여 접속 객체인 java.sql.Connection을 반환하는 메서드
	 * 현재 접속중인 계정 : COFFEE
	 * @return java.sql.Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "COFFEE";
		String password = "COFFEE";
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}//RDBMS 연결. 접속 정보 객체 반환.
	
	/**
	 * 사용되어진 java.sql.* 객체를 닫아주는 메소드
	 * @param rs java.sql.ResultSet
	 * @param stmt java.sql.Statement
	 * @param conn java.sql.Connection
	 */
	public void closed(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}catch(SQLException e){
			System.out.println("sql 객체 닫기 실패");
			e.printStackTrace();
		}
	}//sql 객체 닫기 메서드
	
}
