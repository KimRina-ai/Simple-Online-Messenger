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
         Class.forName("com.mysql.cj.jdbc.Driver"); // �����ͺ��̽� ����
         String user = "root", passwd = "sqjfwk8282";
         conn = DriverManager.getConnection(url, user,passwd);
         stmt = conn.createStatement();
         stmt2 = conn.createStatement();
      
         
         sql = "select * from member where id = '" + id +  "'"; // ���̵� ��ǲ�� ���� ���� �˻�
         rs = stmt.executeQuery(sql);
         if(rs.next() == false || (id.isEmpty()) == true ) { // ȸ���� �ƴϰų� �ƹ��͵� ���� �ʾ��� �� 1 ��ȯ
            result = 1;
         }else {
            sql = "select * from (select * from member where id = '" + id + "') as a"; 
            rs2 = stmt2.executeQuery(sql);
            while(rs2.next() == true) {
               String on_off = rs2.getString("on_off"); // ������ �¶��� �������� ���¸� Ȯ���Ͽ� �ߺ� �α����� ���� �ʵ��� ��
               if(rs2.getString("pw").equals(pw))
                  {
                  if(on_off.equals("on")) { // ���� �α����� �Ǿ��ִٸ� 2 ��ȯ
                     result = 2;
                  }
                  else
                  {
                  result = 0; // �α����� �Ǿ����� �ʰ� ȸ���� ��� 0 ��ȯ
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