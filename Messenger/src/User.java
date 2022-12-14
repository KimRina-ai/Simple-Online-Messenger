import java.sql.*;
import java.util.*;


public class User {
	String id = null;
	String pw = null;
	
	Statement stmt = null;
	ResultSet rs = null;
	String url = "jdbc:mysql://localhost:3306/chat?serverTimezone=UTC";
	String sql = null;
	Properties info = null;
	Connection conn = null;
	

	int checkIDPW(String id, String pw) {
		this.id = id;
		this.pw = pw;
		int result = 1;
		
        // Step 1: Loading or registering MySQL JDBC driver class
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException cnfex) {
            System.out.println("Problem in loading MySQL JDBC driver");
            cnfex.printStackTrace();
        }
		
		try {

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat?serverTimezone=UTC", "root", "sqjfwk8282"); 
			stmt = conn.createStatement();
			
			sql = "select * from users where id = '" + id +  "'";
			rs = stmt.executeQuery(sql);
			if(rs.next() == false || (id.isEmpty()) == true ) {
				result = 1;
			}else {
				sql = "select * from users where id = '" + id + "'";
				rs = stmt.executeQuery(sql);
				while(rs.next() == true) {
					if(rs.getString(2).equals(pw))
						result = 0;
					else
						result = 1;
				}
			}
		}catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return result;
	}
}