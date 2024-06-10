package test.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Q3 {
	 
	String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String USER = "c##madang";
	String PWD = "madang";
	
	Connection con;

	public Q3() {
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
	
	public void runSQL(int searchMenu, String searchWord) {
        String sql = "";
        
        switch(searchMenu) {
	        case 1:
	        	sql = "SELECT c.name, b.bookname, o.saleprice FROM book b, customer c, orders o "
	        		+ "WHERE c.custid = o.custid AND b.bookid = o.bookid "
	        		+ "AND b.bookname LIKE '%" + searchWord + "%' ORDER BY c.name";
	        	break;
	        case 2:
	        	sql = "SELECT c.name, b.bookname, o.saleprice FROM book b, customer c, orders o "
	        		+ "WHERE c.custid = o.custid AND b.bookid = o.bookid "
	        		+ "AND c.name LIKE '%" + searchWord + "%' ORDER BY c.name";
	        	break;        	
        }
        
        try {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.printf("%-10s", "고객명");
	    	System.out.printf("%-10s", "도서명");
	    	System.out.printf("%-10s\n", "판매가격");
	    	System.out.println("=========================");

            while(rs.next()) {
                System.out.printf("%-10s" , rs.getString("name"));
                System.out.printf("%-10s" , rs.getString("bookname"));
                System.out.printf("%10d%n" , rs.getInt("saleprice"));
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {

	    Q3 q3Obj= new Q3();
	    Scanner scanner1= new Scanner(System.in);
	    Scanner scanner2= new Scanner(System.in);
	    
	    while(true){
	    	System.out.println("[검색메뉴]");
	    	System.out.println("1. 도서명");
	    	System.out.println("2. 고객명");
	    	System.out.println("3. 종료");
	    	System.out.print("* 검색메뉴를 선택하세요.(1 또는 2), 3은 종료 : ");
	    
	    	int searchMenu= scanner1.nextInt();
	    
	    	if(searchMenu == 3){
	    		q3Obj.runSQL(searchMenu,null);
	    		break; // 반복문 탈출
	    	}
	    
		    String searchWord= "";
		    
		    switch(searchMenu){
		    	case 1:
		    		System.out.print("* 도서명의 검색어를 입력 : ");
		    		break;
		    	case 2:
		    		System.out.print("* 고객명의 검색어를 입력 : ");
		    		break;    	
    	    	default:
    	    		continue; // 다음 반복으로 진행
    	    }
		    
    	    searchWord= scanner2.nextLine();

    	    q3Obj.runSQL(searchMenu,searchWord);  
    	}

    	try{
    		q3Obj.con.close(); // DB 연결 종료
    	} catch(SQLException e){
    		e.printStackTrace();
    	}

    	scanner1.close();
    	scanner2.close();  
    }
}