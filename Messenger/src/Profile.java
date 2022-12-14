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
         Class.forName("com.mysql.cj.jdbc.Driver"); // db ����
         String user = "root", passwd = "sqjfwk8282";
         conn = DriverManager.getConnection(url, user,passwd);
         stmt = conn.createStatement();
         stmt2 = conn.createStatement();
         stmt3 = conn.createStatement();
         stmt4 = conn.createStatement();
         
         sql = "select * from member where id = '" + text +  "' or nickname = '" + text + "'";
         rs = stmt.executeQuery(sql); // �����ʿ� ��� ������ ������ �ҷ���
         
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
   friend = new JButton("ģ�� �߰�"); // ģ�� �߰� ���
   friend.setBackground(Color.YELLOW);
   friend.setOpaque(true);
   back = new JButton("�ڷ� ����"); // �ڷ� ���� ��ư
   
   try {
      sql = "select * from friend where id = '" + my_id + "' and friend_id = '" + id[0] + "'";
      rs = stmt2.executeQuery(sql); // ���� ģ������ �ƴ��� ���� ������ ���� Ȯ��
      
      if(rs.next() == true)
      {
         friend_or_not = 1; // ���� ģ����� ��ư ���� �޸��� �׸��� 0 or 1�� ���� ���� ���� ������ 1�� ��
         friend.setBackground(Color.LIGHT_GRAY);
      }
      
   }catch(Exception e) {
      e.printStackTrace();
   }
   if(my_id.trim().equals(text)) // ���� �˻��� ������ �ڱ� �ڽ��̶�� ģ�� �߰� ��ư �ؽ�Ʈ�� �ٲٰ� ��Ȱ��ȭ��
   {
      friend.setText("���߳�");
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
   public void actionPerformed(ActionEvent e) { // ģ�� �߰� ��ư�� ������ ��
      if(e.getSource() == friend ) {
         String sql;
         if(friend_or_not == 0) // ���� ģ���� �ƴ϶��
         {
            try
            {
               
               sql = "insert into friend value ('" + my_id.trim() + "', '" + id[0] + "')"; 
               stmt3.executeUpdate(sql); // ģ�� �߰� ����
               friend_or_not = 1;
               friend.setBackground(Color.LIGHT_GRAY);
            }catch(Exception e1) {
               e1.printStackTrace();
            }
         }
         else { // ���� �̹� ģ�����
 
            try
            {
               
               sql = "delete from friend where id = '" + my_id.trim() + "' and friend_id = '" + id[0].trim() + "'"; 
               stmt4.executeUpdate(sql); // ģ�� ���� ����
               friend_or_not = 0;
               friend.setBackground(Color.YELLOW);
            }catch(Exception e1) {
               e1.printStackTrace();
            }
         }
         
         
      }
      else if(e.getSource() == back) {// �ڷ� ���� ���� �� ����Ʈ ����Ʈ�� �̵�
         new FriendList();
         setVisible(false);
      }
      
   }

}