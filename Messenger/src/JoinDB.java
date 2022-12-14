import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.math.BigInteger;
public class JoinDB implements MouseListener {
   JFrame frame;
   JPanel logPanel;
   JPanel logPanel1;
   JPanel logPanel2;
   idcode_gen randVal;
   String_crypto crypt = new String_crypto();
   JPanel logPanel3;
   JTextField idText,nameText, birthText, nickText, emailText, pnumText, homeText = null;
   JPasswordField pwText, pwCheckText = null;
   JButton joinBtn, checkBtn, checkNickBtn;
   
   void JoinDBPanel() {
      frame = new JFrame("Join");
      logPanel = new JPanel();
      logPanel1 =  new JPanel(new GridLayout(9,1));
      logPanel2 = new JPanel(new GridLayout(9,1));
      logPanel3 = new JPanel(new GridLayout(7,1));
      
      
      //��� color
      	Color b = new Color(0,175,240);
         logPanel.setBackground(b);
         logPanel1 =  new JPanel(new GridLayout(9,1));
         logPanel1.setBackground(b);
         logPanel2 = new JPanel(new GridLayout(9,1));
         logPanel2.setBackground(b);
         logPanel3 = new JPanel(new GridLayout(7,1));
         logPanel3.setBackground(b);


         //Font
         Font font = new Font("Aharoni ����", Font.BOLD, 15);
         
         
         JLabel idLabel = new JLabel("ID", JLabel.CENTER);
         idLabel.setFont(font);
         idLabel.setForeground(Color.white);
         JLabel pwLabel = new JLabel("PW", JLabel.CENTER);
         pwLabel.setFont(font);
         pwLabel.setForeground(Color.white);
         JLabel pwCheckLabel = new JLabel("Check PW", JLabel.CENTER);
         pwCheckLabel.setFont(font);
         pwCheckLabel.setForeground(Color.white);
         JLabel nickLabel = new JLabel("Nickname", JLabel.CENTER);
         nickLabel.setFont(font);
         nickLabel.setForeground(Color.white);
         JLabel emailLabel = new JLabel("E-Mail", JLabel.CENTER);
         emailLabel.setFont(font);
         emailLabel.setForeground(Color.white);
         JLabel nameLabel = new JLabel("Name", JLabel.CENTER);
         nameLabel.setFont(font);
         nameLabel.setForeground(Color.white);
         JLabel birthLabel = new JLabel("Birth Date "+"\n"+"yy/mm/dd", JLabel.CENTER);
         birthLabel.setFont(font);
         birthLabel.setForeground(Color.white);
         JLabel pnumLabel = new JLabel("Phone number (option) ", JLabel.CENTER);
         pnumLabel.setFont(font);
         pnumLabel.setForeground(Color.white);
         JLabel homeLabel = new JLabel("Homepage address (option)", JLabel.CENTER);
         homeLabel.setFont(font);
         homeLabel.setForeground(Color.white);
         

      
      logPanel1.add(idLabel);
      logPanel1.add(pwLabel);
      logPanel1.add(pwCheckLabel);
      logPanel1.add(nickLabel);
      logPanel1.add(emailLabel);
      logPanel1.add(nameLabel);
      logPanel1.add(birthLabel);
      logPanel1.add(pnumLabel);
      logPanel1.add(homeLabel);
      
      idText = new JTextField(20);
      idText.addMouseListener(this);
      
      pwText = new JPasswordField(20);
      pwText.addMouseListener(this);
      
      pwCheckText = new JPasswordField(20);
      pwCheckText.addMouseListener(this);
      
      nickText = new JTextField(20);
      nickText.addMouseListener(this);
      
      emailText = new JTextField(50);
      emailText.addMouseListener(this);
      
      nameText = new JTextField(20);
      nameText.addMouseListener(this);
      
      birthText = new JTextField(6);
      birthText.addMouseListener(this);
      
      pnumText = new JTextField(11);
      pnumText.addMouseListener(this);
      
      homeText = new JTextField(100);
      homeText.addMouseListener(this);
      
      
      logPanel2.add(idText);
      logPanel2.add(pwText);
      logPanel2.add(pwCheckText);
      logPanel2.add(nickText);
      logPanel2.add(emailText);
      logPanel2.add(nameText);
      logPanel2.add(birthText);
      logPanel2.add(pnumText);
      logPanel2.add(homeText);
      
      checkBtn = new JButton("Check ID");
   

      logPanel3.add(checkBtn, BorderLayout.EAST);
   
      checkBtn.addMouseListener(this);
      //���̵� �ߺ� üũ�� ���� ��ư 
      checkNickBtn = new JButton("Check Nickname");
      logPanel3.add(checkNickBtn,BorderLayout.EAST);
      checkNickBtn.addMouseListener(this);

      //�г��� �ߺ� üũ�� ���� ��ư
      frame.add(logPanel,BorderLayout.NORTH);
      frame.add(logPanel1,BorderLayout.WEST);
      frame.add(logPanel2, BorderLayout.CENTER);
      frame.add(logPanel3, BorderLayout.EAST);
      logPanel3.setPreferredSize(new Dimension(130, 10));

      JPanel logPanel4 = new JPanel();
      logPanel4.setBackground(b);
      JLabel askLabel = new JLabel("Join?");
      joinBtn = new JButton("YES");
      JButton disBtn = new JButton("NO");
      
      joinBtn.addMouseListener(this);
      logPanel4.add(askLabel);
      logPanel4.add(joinBtn);
      logPanel4.add(disBtn);
      frame.add(logPanel4, BorderLayout.SOUTH);
      
      disBtn.addMouseListener(new MouseAdapter(){
         @Override
         public void mouseClicked(MouseEvent e) {
            frame.dispose();
            dbClose();
         }
      });
      //��� ��ư�� ������ ȸ�������� ��� �ǰ� , Frame �� �� ��
      frame.setBounds(450,250,500,500);
      frame.setResizable(false);
      frame.setVisible(true);
      
   }
   
   Statement stmt = null;
   ResultSet rs = null;
   String sql = null;
   String url = "jdbc:mysql://localhost/users?serverTimezone=UTC&useSSL=false&&allowPublicKeyRetrieval=true&useSSL=false";
   Properties info = null;
   Connection conn = null;
   //db�� ���� 
   @Override
   public void mouseClicked(MouseEvent e)  {
      if(e.getSource().equals(idText))
         idText.setText("");
      else if(e.getSource().equals(pwText))
         pwText.setText("");
      else if(e.getSource().equals(pwCheckText))
         pwCheckText.setText("");
      
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         info = new Properties();
         info.setProperty("user", "root");
         info.setProperty("password", "sqjfwk8282");
         conn = DriverManager.getConnection(url, info);
         stmt = conn.createStatement();
         
//      
         if (e.getSource().equals(checkBtn)) {
                sql = "select * from member where id='" + idText.getText() + "'";
                rs = stmt.executeQuery(sql); // �о���� �Ŷ� �ٸ��� ���� // ����Ÿ�о� ResultSet
 
                if (rs.next() == true || (idText.getText().isEmpty()) == true) { // �̹� id�� �����Ѵٸ�
                   JOptionPane.showMessageDialog(logPanel3, "Unavailable ID");   
                   // �ߺ��Ǵ� ���̵��� �˾��� ���
                   } 
                else {
                   JOptionPane.showMessageDialog(logPanel3, "Available ID"); 
                   // ��밡���� ���̵��� �˾��� ��� 
                   }
            }
 
         if (e.getSource().equals(checkNickBtn)) {
                sql = "select * from member where nickname='" + nickText.getText() + "'";
                rs = stmt.executeQuery(sql); // �о���� �Ŷ� �ٸ��� ���� // ����Ÿ�о� ResultSet
 
                if (rs.next() == true || (nickText.getText().isEmpty()) == true) { // �̹� �г����� �����Ѵٸ�
                   JOptionPane.showMessageDialog(logPanel3, "Unavailable Nickname");                } 
                else {
                   JOptionPane.showMessageDialog(logPanel3, "Available Nickname");                }
            }
            // ���� ��ư
         randVal = new idcode_gen();
         long result = randVal.make_member_code(idText.getText(), nickText.getText());
            if (e.getSource().equals(joinBtn)) {
               
               String a = getPass(pwText.getPassword());
               
                sql = "select * from member where id='" + idText.getText() + "'";
 
                rs = stmt.executeQuery(sql); // �о���°Ŷ� �ٸ��� ����//����Ÿ���� ResultSet
 
                if (rs.next() == true) { // �̹� id�� �����Ѵٸ�
                   JOptionPane.showMessageDialog(logPanel3, "Please check ID");    
 
                } else if (idText.getText().isEmpty() || (nameText.getText().isEmpty()) ||(nickText.getText().isEmpty())||(emailText.getText().isEmpty())|| (birthText.getText().isEmpty())) {        //빈칸 ��  �� ��경우
                   JOptionPane.showMessageDialog(logPanel3, "Empty value");
                } else if ((birthText.getText().length()) != 6) {
                   JOptionPane.showMessageDialog(logPanel3, "Invalid birth date");
                } else {
                   if(!pnumText.getText().isEmpty() && !homeText.getText().isEmpty()) {
                   sql = "insert into member values ('" + idText.getText() + "','" + a + "','"+ nickText.getText() + "','"
                         + emailText.getText() + "','"+ nameText.getText() + "','" + birthText.getText() + "', '" + pnumText.getText() + "','"+ homeText.getText()+ "',"+ result + ",'off', null)";
                   }
                   else if(!pnumText.getText().isEmpty() && homeText.getText().isEmpty()) {
                      sql = "insert into member values ('" + idText.getText() + "','" + a  + "','"+ nickText.getText() + "','"
                             + emailText.getText() + "','"+ nameText.getText() + "','" + birthText.getText() + "','" + pnumText.getText() + "', null"+ ","+ result+ ", 'off', null)";
                   }
                   else if(pnumText.getText().isEmpty() && !homeText.getText().isEmpty()) {
                      sql = "insert into member values ('" + idText.getText() + "','" + a + "','"+ nickText.getText() + "','"
                             + emailText.getText() + "','"+ nameText.getText() + "','" + birthText.getText() + "',null'"+ homeText.getText() + "',"+ result+ ",'off', null)";
                   }else {
                      sql = "insert into member values ('" + idText.getText() + "','" + a + "','"+ nickText.getText() + "','"
                             + emailText.getText() + "','"+ nameText.getText() + "','" + birthText.getText()+"' ,null,null" + ","+ result+",'off', null )"
                                   + "";
                   }
                   stmt.executeUpdate(sql);
               JOptionPane.showMessageDialog(logPanel3, "Joined!");
               frame.dispose();
               dbClose();
                }
            }


      }catch(Exception ee) {
         ee.printStackTrace();
      }
   }
   public String getPass(char[] a)
   {
      String pw="";
      for(char cha : a) {
         Character.toString(cha);
         pw+= (pw.equals(""))?""+ cha + "" : "" +  cha + "";
      }
      return pw;
   }
   @Override
   public void mousePressed(MouseEvent e) {
      
   }
   @Override
   public void mouseReleased(MouseEvent e) {
      
   }
   @Override
   public void mouseEntered(MouseEvent e) {
      
   }
   @Override
   public void mouseExited(MouseEvent e) {
      
   }

   public void dbClose() {
      try {
         if(rs != null)
            rs.close();
         if(stmt != null)
            stmt.close();
         if(conn!= null)
            conn.close();
      }catch(SQLException e1) {
         e1.printStackTrace();
      }
   }
}