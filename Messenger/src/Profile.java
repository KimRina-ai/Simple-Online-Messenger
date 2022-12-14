import javax.swing.JFrame;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Profile extends JFrame implements ActionListener{
   JPanel panel_big;
   JLabel name2;
   JLabel nickname2;
   JLabel id2;
   String my_id;
   int friend_or_not = 0;
   Statement stmt = null;
   Statement stmt2 = null;
   Statement stmt3 = null;
   Statement stmt4 = null;
   ResultSet rs = null;
   String url = "jdbc:mysql://localhost/users?serverTimezone=UTC&useSSL=false&&allowPublicKeyRetrieval=true&useSSL=false";
   String sql = null;
   Connection conn = null;
   
   String information;
   
   JButton friend;
   JButton back; 
   JPanel bottom;
   JPanel button;
   
   String[] id = {""};
   String[] nickname = {""};
   String[] name = {""};
   
   Profile(String my_id, String text){
      this.my_id = my_id;
      try {
         Class.forName("com.mysql.cj.jdbc.Driver"); // db 연결
         String user = "root", passwd = "sqjfwk8282";
         conn = DriverManager.getConnection(url, user,passwd);
         stmt = conn.createStatement();
         stmt2 = conn.createStatement();
         stmt3 = conn.createStatement();
         stmt4 = conn.createStatement();
         
         sql = "select * from member where id = '" + text +  "' or nickname = '" + text + "'";
         rs = stmt.executeQuery(sql); // 프로필에 띄울 유저의 정보를 불러옴
         
         int i = 0;
         
         while(rs.next())
         {
            id[i] = rs.getString("id");
            nickname[i] = rs.getString("nickname");
            name[i] = rs.getString("name");
         }
         
   
      }catch(Exception e) {
         e.printStackTrace();
      }
   panel_big = new JPanel(new GridLayout(4,1));
   bottom = new JPanel();
   
   id2 = new JLabel();
   id2.setText(id[0]);
   nickname2 = new JLabel();
   nickname2.setText(nickname[0]);
   name2 = new JLabel();
   name2.setText(name[0]);
   
   button = new JPanel(new GridLayout(1,2));
   friend = new JButton("친구 추가"); // 친구 추가 기능
   friend.setBackground(Color.YELLOW);
   friend.setOpaque(true);
   back = new JButton("뒤로 가기"); // 뒤로 가기 버튼
   
   try {
      sql = "select * from friend where id = '" + my_id + "' and friend_id = '" + id[0] + "'";
      rs = stmt2.executeQuery(sql); // 먼저 친구인지 아닌지 데베 쿼리를 통해 확인
      
      if(rs.next() == true)
      {
         friend_or_not = 1; // 만약 친구라면 버튼 색을 달리함 그리고 0 or 1의 값을 갖는 정수 변수를 1로 함
         friend.setBackground(Color.LIGHT_GRAY);
      }
      
   }catch(Exception e) {
      e.printStackTrace();
   }
   if(my_id.trim().equals(text)) // 만약 검색한 유저가 자기 자신이라면 친구 추가 버튼 텍스트를 바꾸고 비활성화됨
   {
      friend.setText("나야나");
      friend.setBackground(Color.CYAN);
      friend.setEnabled(false);
   }
   friend.addActionListener(this);
   back.addActionListener(this);
   
   button.add(friend);
   button.add(back);
   
   name2.setHorizontalAlignment(JLabel.CENTER);
   nickname2.setHorizontalAlignment(JLabel.CENTER);
   id2.setHorizontalAlignment(JLabel.CENTER);
   
   panel_big.add(name2, BorderLayout.CENTER);
   panel_big.add(nickname2, BorderLayout.CENTER);
   panel_big.add(id2, BorderLayout.CENTER);
   panel_big.add(button, BorderLayout.CENTER);
   
   this.setLayout(new BorderLayout(1,2));
   this.add(panel_big, BorderLayout.NORTH);
   this.add(bottom, BorderLayout.SOUTH);
   
   this.setSize(200, 50);
   this.pack();
   this.setVisible(true);
   }
   
   @Override
   public void actionPerformed(ActionEvent e) { // 친구 추가 버튼을 눌렀을 때
      if(e.getSource() == friend ) {
         String sql;
         if(friend_or_not == 0) // 만약 친구가 아니라면
         {
            try
            {
               
               sql = "insert into friend value ('" + my_id.trim() + "', '" + id[0] + "')"; 
               stmt3.executeUpdate(sql); // 친구 추가 실행
               friend_or_not = 1;
               friend.setBackground(Color.LIGHT_GRAY);
            }catch(Exception e1) {
               e1.printStackTrace();
            }
         }
         else { // 만약 이미 친구라면
 
            try
            {
               
               sql = "delete from friend where id = '" + my_id.trim() + "' and friend_id = '" + id[0].trim() + "'"; 
               stmt4.executeUpdate(sql); // 친구 삭제 진행
               friend_or_not = 0;
               friend.setBackground(Color.YELLOW);
            }catch(Exception e1) {
               e1.printStackTrace();
            }
         }
         
         
      }
      else if(e.getSource() == back) {// 뒤로 가기 누를 시 프렌트 리스트로 이동
         new FriendList();
         setVisible(false);
      }
      
   }

}