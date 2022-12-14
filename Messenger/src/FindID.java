import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class FindID extends JFrame implements WindowListener{
   String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost/users";
    String user = "root", pw = "sqjtwk8282";
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    public static String id = null;
    JoinDB jdb;
    LoginDB ljdb;
    FriendList fList;
    String current_ID;
    String member_name = "";
    @SuppressWarnings("deprecation")
   String member_email = "";
    String found_id = "";
    public FindID() {
      
        setLayout(new BorderLayout());
         JPanel panel = new JPanel();
         panel.setLayout(null);
         Color b = new Color(0, 175, 240);
         panel.setBackground(b);
         
         //Font
         Font font = new Font("Aharoni 굵게", Font.BOLD, 75);
         Font font2 = new Font("Aharoni 굵게", Font.BOLD, 15);
         
         //Title
         JLabel title = new JLabel("SGYPE",JLabel.CENTER);
         title.setBounds(50,100,500,80);
         title.setFont(font);
         title.setForeground(Color.white);
         
         //name
         JLabel namelb = new JLabel("    Name");
         namelb.setBounds(100,250,300,50);
         namelb.setFont(font2);
         namelb.setForeground(Color.white);
         JTextField nametf = new JTextField(15);
         nametf.setBounds(200,250,300,40);
         nametf.setBackground(new Color(204,239,252));
         
         //email
         JLabel emaillb = new JLabel("   Email");
         emaillb.setBounds(100,300,300,50);
         emaillb.setFont(font2);
         emaillb.setForeground(Color.white);
         JTextField emailtf = new JTextField(20);
         emailtf.setBounds(200,300,300,40);
         emailtf.setBackground(new Color(204,239,252));
         
       
         //Button
         JButton findBtn = new JButton("Find ID");
         findBtn.setBounds(150,450,300, 30);
         findBtn.setBackground(new Color(12,125,175));
         findBtn.setForeground(Color.white);
         
         findBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            member_name = new String(nametf.getText());
            member_email = new String(emailtf.getText());
            
            found_id = findId(member_name, member_email);
            String id_found = "Your ID is < " + found_id + " >";
            JOptionPane.showMessageDialog(null, id_found);
         }
         });
         
         panel.add(title);
         panel.add(namelb);
         panel.add(nametf);
         panel.add(emaillb);
         panel.add(emailtf);
         
         panel.add(findBtn);
         
      
         add(panel);
         setVisible(true);
         setSize(600,800);
         setLocationRelativeTo(null);
         setResizable(false);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         this.addWindowListener(this);

   }
    
   public String findId(String member_name, String member_email) {
      String id = null;
      
      try {
         Class.forName(driver);
         conn = DriverManager.getConnection(url, user, pw);
         String sql = "select id from member where name=? and email=? ";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, member_name);
         pstmt.setString(2, member_email);
         
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            id = rs.getString("id");
         }
            
      } catch (Exception e) {
         e.printStackTrace();
      }
      return id;
   }
   
   public static void main(String args[]) {
      new FindID();
   }
   @Override
   public void windowOpened(WindowEvent e) {
      // TODO Auto-generated method stub
      
   }
   @Override
   public void windowClosing(WindowEvent e) {
      LoginDB logout = new LoginDB();
      int state = JOptionPane.showConfirmDialog(this, "종료하시겠습니까?");
      
      if(state == JOptionPane.YES_NO_OPTION)
      {
         logout.logout(current_ID);
           System.exit(0);
      }
      
      
   }
   @Override
   public void windowClosed(WindowEvent e) {

      
   }
   @Override
   public void windowIconified(WindowEvent e) {
      // TODO Auto-generated method stub
      
   }
   @Override
   public void windowDeiconified(WindowEvent e) {
      // TODO Auto-generated method stub
      
   }
   @Override
   public void windowActivated(WindowEvent e) {
      // TODO Auto-generated method stub
      
   }
   @Override
   public void windowDeactivated(WindowEvent e) {
      // TODO Auto-generated method stub
      
   }
}