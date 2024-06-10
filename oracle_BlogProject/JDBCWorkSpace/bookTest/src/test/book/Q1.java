package test.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Q1 {
	 
	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String USER = "c##madang";
	String PWD = "madang";
	
	
	Connection con;
	public Q1() {
//연결하기 위해 필요한 정보(url, username, pwd)
			
			
//JDBC Driver Load 한다
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
				
//Connection 객체 생성
			con = DriverManager.getConnection(URL, USER, PWD);
			System.out.println("DB 연결 성공");
		} catch (ClassNotFoundException e) {
				e.printStackTrace();
		} catch (SQLException e) {
				e.printStackTrace();
		}
	 }
	
	public void runSQL() {
		String sql = "select bookid, bookname from book order by bookid asc";
		try {
			//Connection 객체를 사용해서 문장객체를 생성 및 반환
			Statement stmt = con.createStatement();
			//Statement 객체를 사용해서 SQL문을 실행
			ResultSet rs = stmt.executeQuery(sql);
			//반복문을 사용해서 ResultSet 객체의 행개수 만큼 데이터를 가져와서 콘솔에 출력
			//next() 메소드는 데이터행을 접근할 수 있는 커서를 다음 행으로 이동시키는 기능을 함
//			실제 cursor가 가리키는 데이터행이 존재하면 true를 반환, 데이터행이 존재하지 않으면 false 반환
			while(rs.next()) {
				System.out.print("\t" + rs.getInt("bookid"));
				System.out.println("\t" + rs.getString("bookname"));
				System.out.println();
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	
	public static void main(String[] args) {
		Q1 bList = new Q1();
		bList.runSQL();

	}

}