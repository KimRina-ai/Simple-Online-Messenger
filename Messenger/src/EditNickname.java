import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EditNickname extends JFrame {

   String newNickname = "";
   private PreparedStatement pstmt1;
    private Connection conn;
    private int rs;
    
   EditNickname(String userNick){
      
      JTextField newNicknameTf = new JTextField("new nickname here");
      newNicknameTf.setBackground(new Color(204,239,252));
        JButton editbtn = new JButton("Edit");
        editbtn.setBackground(new Color(12,125,175));
        editbtn.setForeground(Color.white);
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(newNicknameTf);
        panel.add(editbtn);
        add(panel);
      setTitle("Edit Nickname");
      setBounds(500, 300, 400, 150);
      //setSize(400, 150);
      setVisible(true);
      
      editbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newNickname = newNicknameTf.getText();
                String SQL1 = "update member set nickname=? where nickname=?";

                try{
                    pstmt1 = conn.prepareStatement(SQL1);
                    pstmt1.setString(1, newNickname);
                    pstmt1.setString(2, userNick);

                    //Äõ¸® ½ÇÇà
                    rs = pstmt1.executeUpdate();


                } catch(Exception e1){
                    e1.printStackTrace();
                }

                new FriendList();
                dispose();
                setVisible(false);

            }
        });

        try{
            String dbURL = "jdbc:mysql://localhost/users";
            String dbID = "root";
            String dbPW = "sqjfwk8282";

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(dbURL, dbID, dbPW);

        } catch (Exception e) {
            e.printStackTrace();
        }
      
   }
   public static void main(String args[]) {
      String nick = "";
      new EditNickname(nick);
   }
}