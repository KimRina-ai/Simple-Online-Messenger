import java.sql.*;
import java.util.*;

public class LoginDB{
   String id = null;
   String pw = null;
   
   Statement stmt = null;
   Statement stmt2 = null;
   Statement stmt3 = null;
   Statement stmt4 = null;
   
   ResultSet rs = null;
   ResultSet rs2 = null;
   
   String url = "jdbc:mysql://localhost/users?serverTimezone=UTC&useSSL=false&&allowPublicKeyRetrieval=true&useSSL=false";
   
   String sql = null;
   Connection conn = null;
   String_crypto crypto = new String_crypto();
   
   int checkIDPW(String id, String pw) {
      this.id = id;
      this.pw = pw;
      int result = 0;
      
      try {
         Class.forName("com.mysql.cj.jdbc.Driver"); // 데이터베이스 연결
         String user = "root", passwd = "sqjfwk8282";
         conn = DriverManager.getConnection(url, user,passwd);
         stmt = conn.createStatement();
         stmt2 = conn.createStatement();
      
         
         sql = "select * from member where id = '" + id +  "'"; // 아이디 인풋을 통해 정보 검색
         rs = stmt.executeQuery(sql);
         if(rs.next() == false || (id.isEmpty()) == true ) { // 회원이 아니거나 아무것도 적지 않았을 때 1 반환
            result = 1;
         }else {
            sql = "select * from (select * from member where id = '" + id + "') as a"; 
            rs2 = stmt2.executeQuery(sql);
            while(rs2.next() == true) {
               String on_off = rs2.getString("on_off"); // 유저의 온라인 오프라인 상태를 확인하여 중복 로그인이 되지 않도록 함
               if(rs2.getString("pw").equals(pw))
                  {
                  if(on_off.equals("on")) { // 만약 로그인이 되어있다면 2 반환
                     result = 2;
                  }
                  else
                  {
                  result = 0; // 로그인이 되어있지 않고 회원일 경우 0 반환
                  login(id);
                  }
                  }
               else {
                  result = 1;
               }
            
            }
         }
      }catch(Exception e) {
         System.out.println("Error");
         e.printStackTrace();
      }
      return result;
   }
   public void logout(String id) {
   try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      String user = "root", passwd = "sqjfwk8282";
      conn = DriverManager.getConnection(url, user,passwd);
      String sql;
      stmt3 = conn.createStatement();
      sql = "update member set on_off = 'off' where id = '" + id + "'";
      stmt3.executeUpdate(sql);
   }catch(Exception e1) {
      e1.printStackTrace();
   }
      
   }
   public void login(String id) {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         String user = "root", passwd = "sqjfwk8282";
         conn = DriverManager.getConnection(url, user,passwd);
         String sql;
         stmt3 = conn.createStatement();
         sql = "update member set on_off = 'on' where id = '" + id + "'";
         stmt3.executeUpdate(sql);
      }catch(Exception e1) {
         e1.printStackTrace();
   
      }
}
}