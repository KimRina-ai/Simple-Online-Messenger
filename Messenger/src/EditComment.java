import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EditComment extends JFrame {
   String newComment = "";
   private PreparedStatement pstmt1;

    private Connection conn;

    private int rs;
    
   EditComment(String userNick){
      JTextField newCommentTf = new JTextField("new comment here");
      newCommentTf.setBackground(new Color(204,239,252));
        //newComment.setBounds(300,700,200,30);
        JButton editbtn = new JButton("Edit");
        editbtn.setBackground(new Color(12,125,175));
        editbtn.setForeground(Color.white);
        //edit.setBounds(270, 800, 150, 30);
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(newCommentTf);
        panel.add(editbtn);
        add(panel);
      setTitle("Edit Comment");
      setBounds(500, 300, 400, 150);
      //setSize(400, 150);
      setVisible(true);
      
      editbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newComment = newCommentTf.getText();
                String SQL1 = "update member set comments=? where nickname=?";

                try{
                    pstmt1 = conn.prepareStatement(SQL1);
                    pstmt1.setString(1, newComment);
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
      new EditComment(nick);
   }
}