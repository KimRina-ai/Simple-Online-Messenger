

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBCon {
static Connection con;
private DBCon(){}
static {
	try {
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost/users","root", "sqjfwk8282");
		//System.out.println("연결 성공");
	}catch(ClassNotFoundException e) {}
	catch (SQLException e){}
}
public static Connection getConnection() { return con;}
public static void close(ResultSet rs) {
	if(rs!=null)
		try {
			rs.close();
		} catch (SQLException e) {}
}
public static void close(Statement stmt) {
	if(stmt!=null)
		try {
			stmt.close();
		} catch (SQLException e) {}
}
public static void close(PreparedStatement pstmt) {
	if(pstmt!=null)
		try {
			pstmt.close();
		} catch (SQLException e) {}
}
public static void close(Connection con) {
	if(con!=null)
		try {
			con.close();
		} catch (SQLException e) {}
}

}