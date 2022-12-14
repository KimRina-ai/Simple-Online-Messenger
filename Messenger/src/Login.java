import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.*;

public class Login extends JFrame implements WindowListener{
    public static String id = null;
    JoinDB jdb;
    LoginDB ljdb;
    FriendList fList;
    String current_ID;
    public Login() {
      
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
         
         //ID
         JLabel idLabel = new JLabel("      ID");
         idLabel.setBounds(100,250,300,50);
         idLabel.setFont(font2);
         idLabel.setForeground(Color.white);
         JTextField idText = new JTextField(15);
         idText.setBounds(200,250,300,40);
         idText.setBackground(new Color(204,239,252));
         
         //PW
         JLabel pwLabel = new JLabel("Password");
         pwLabel.setBounds(100,300,300,50);
         pwLabel.setFont(font2);
         pwLabel.setForeground(Color.white);
         JPasswordField pwText = new JPasswordField(20);
         pwText.setBounds(200,300,300,40);
         pwText.setBackground(new Color(204,239,252));
         
         //Button
         JButton logBtn = new JButton("Log In");
         logBtn.setBounds(150,400,300, 30);
         logBtn.setBackground(new Color(12,125,175));
         logBtn.setForeground(Color.white);
         JButton joinBtn = new JButton("Join");
         joinBtn.setBounds(150,450,300,30);
         joinBtn.setBackground(new Color(12,125,175));
         joinBtn.setForeground(Color.white);
         JButton findIdBtn = new JButton("Forgot ID");
         findIdBtn.setBounds(150, 500, 300, 30);
         findIdBtn.setBackground(new Color(12,125,175));
         findIdBtn.setForeground(Color.white);
         JButton updatePwBtn = new JButton("Forgot PW");
         updatePwBtn.setBounds(150, 550, 300, 30);
         updatePwBtn.setBackground(new Color(12,125,175));
         updatePwBtn.setForeground(Color.white);
         
         panel.add(title);
         panel.add(idLabel);
         panel.add(idText);
         panel.add(pwLabel);
         
         panel.add(pwText);
         panel.add(logBtn);
         panel.add(joinBtn);
         panel.add(findIdBtn);
         //panel.add(updatePwBtn);
         
         findIdBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new FindID();            
         }
            
         });

      
      
      logBtn.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            ljdb = new LoginDB();
            id = idText.getText();
            String pw = new String(pwText.getPassword());
            int result = ljdb.checkIDPW(id,pw);
            if(result == 0) {
               JOptionPane.showMessageDialog(null,"Login Success!");
               current_ID = idText.getText();
               fList = new FriendList();
               try {
                  fList.FriendListPanel(id);
               } catch (IOException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               } catch (ParseException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               } catch (org.json.simple.parser.ParseException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }
            }
            else if(result == 1)
               JOptionPane.showMessageDialog(null,"Fail");
            else if(result == 2)
               JOptionPane.showMessageDialog(null, "이미 로그인 중 입니다!");

         }
      });
      
      joinBtn.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            jdb = new JoinDB();
            jdb.JoinDBPanel();
         }
      });
      
       add(panel);
         setVisible(true);
         setSize(600,800);
         setLocationRelativeTo(null);
         setResizable(false);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         this.addWindowListener(this);

   }
   public static void main(String args[]) {
      new Login();
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