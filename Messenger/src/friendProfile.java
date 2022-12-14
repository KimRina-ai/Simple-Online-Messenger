import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class friendProfile extends JFrame {
   JFrame frame;
   JPanel panel1;
   Statement stmt = null;
   String url = "jdbc:mysql://localhost/users?serverTimezone=UTC&useSSL=false&&allowPublicKeyRetrieval=true&useSSL=false";
   String sql = null;
   String sql2 = null;
   Connection conn = null;
   ResultSet rs = null;
   
   String[] friendId = {""};
   String[] friendNick = {""};
   String[] friendName = {""};
   String[] friendHome = {""};
   String[] friendComment ={""};
   String[] friendPhone = {""};
   String[] friendBirth = {""};
   String[] friendEmail ={""};
   
   public friendProfile(String friendid) {
      frame = new JFrame("friendProfile");
      panel1 = new JPanel(new GridLayout(12,1));
      Color b = new Color(0,175,240);
      panel1.setBackground(b);
      frame.add(panel1);
      
      //font
      Font font = new Font("Aharoni ±½°Ô", Font.BOLD, 25);
         
      
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         String user = "root", passwd = "sqjfwk8282";
         conn = DriverManager.getConnection(url, user, passwd);
           stmt = conn.createStatement();
           sql = "select * from member where id = \'" + friendid.trim() +  "\' ";
           rs = stmt.executeQuery(sql);
           
           int i = 0;
           
           while(rs.next()) {
              if(i==1)
                 break;
              friendId[i] = rs.getString("id");
              friendName[i] = rs.getString("name");
              //friendNick = rs.getString("nickname");
              friendEmail[i] = rs.getString("email");
              friendBirth[i] = rs.getString("birth");
              friendPhone[i] = rs.getString("pnum");
              friendHome[i] = rs.getString("home");
              friendComment[i] = rs.getString("comments");
              i++;
           }
      } catch(Exception e) {
         System.out.println("Error");
         e.printStackTrace();
      }
      JLabel friendIdPanel = new JLabel("ID : " + friendId[0], JLabel.CENTER);
      friendIdPanel.setFont(font);
      friendIdPanel.setForeground(Color.white);
      friendIdPanel.setBounds(300,200,600,50);
      panel1.add(friendIdPanel);
      JLabel friendNamePanel = new JLabel("NAME : " + friendName[0], JLabel.CENTER);
      friendNamePanel.setFont(font);
      friendNamePanel.setForeground(Color.white);
      friendNamePanel.setBounds(100,250,600,50);
      panel1.add(friendNamePanel);
      JLabel friendNickPanel = new JLabel("NICKNAME : " + friendid, JLabel.CENTER);
      friendNickPanel.setFont(font);
      friendNickPanel.setForeground(Color.white);
      friendNickPanel.setBounds(100,300,300,50);
      panel1.add(friendNickPanel);
      JLabel friendEmailPanel = new JLabel("EMAIL : " + friendEmail[0], JLabel.CENTER);
      friendEmailPanel.setFont(font);
      friendEmailPanel.setForeground(Color.white);
      friendEmailPanel.setBounds(100,350,300,50);
      panel1.add(friendEmailPanel);
      JLabel friendBirthPanel = new JLabel("BIRTH : " + friendBirth[0], JLabel.CENTER);
      friendBirthPanel.setFont(font);
      friendBirthPanel.setForeground(Color.white);
      friendBirthPanel.setBounds(100,400,300,50);
      panel1.add(friendBirthPanel);
      JLabel friendPhonePanel = new JLabel("PHONE NUM : " + friendPhone[0], JLabel.CENTER);
      friendPhonePanel.setFont(font);
      friendPhonePanel.setForeground(Color.white);
      friendPhonePanel.setBounds(100,450,300,50);
      panel1.add(friendPhonePanel);
      JLabel friendHomePanel = new JLabel("HOME PAGE : " + friendHome[0], JLabel.CENTER);
      friendHomePanel.setFont(font);
      friendHomePanel.setForeground(Color.white);
      friendHomePanel.setBounds(100,500,300,50);
      panel1.add(friendHomePanel);
      JLabel friendCommentPanel = new JLabel("TODAY COMMENT : " + friendComment[0], JLabel.CENTER);
      friendCommentPanel.setFont(font);
      friendCommentPanel.setForeground(Color.white);
      friendCommentPanel.setBounds(100,550,300,50);
      panel1.add(friendCommentPanel);
      
      /*JButton closeBtn = new JButton("CLOSE");
      closeBtn.setSize(300,100);
      panel1.add(closeBtn);
      
      //close ´©¸£¸é
      closeBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });*/
      
      frame.add(panel1);
      frame.setSize(600,800);
      frame.setVisible(true);
     
   }


}