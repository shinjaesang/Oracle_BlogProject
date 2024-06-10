package test.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Q2 {
	 
	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String USER = "c##madang";
	String PWD = "madang";
	
	Connection con;

	public Q2() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");

			con = DriverManager.getConnection(URL, USER, PWD);
			System.out.println("DB 연결 성공");
		} catch (ClassNotFoundException e) {
				e.printStackTrace();
		} catch (SQLException e) {
				e.printStackTrace();
		}
    }
	
	public void runSQL(String bookName) {
        String sql = "SELECT * FROM book WHERE bookname LIKE ? ORDER BY bookid";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            // Set the value for the placeholder
            pstmt.setString(1, "%" + bookName + "%");

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                System.out.print("\t" + rs.getInt("bookid"));
                System.out.print("\t" + rs.getString("bookname"));
                System.out.println("\t" + rs.getInt("price"));
                System.out.println();
            }

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {

	    Scanner scanner = new Scanner(System.in);
	    System.out.print("책 이름을 입력하세요 ");
	    String inputBookName = scanner.nextLine();

	    Q2 bList = new Q2();
	    bList.runSQL(inputBookName);

	    scanner.close();
    }
}