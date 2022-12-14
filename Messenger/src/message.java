import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class message extends JFrame{
   
   
    String serverAddress;
    Scanner in;
    PrintWriter out;
    JPanel p;
    JPanel p1;
    JPanel p2;
    JFrame frame = new JFrame("Chatter");
//    JTextField textField ;//Declare Textfield
//    JTextArea messageArea ;//Declare message area
    JTextField tf = new JTextField(20);
   JTextArea ta = new JTextArea(7, 20); 
    JButton fileBtn= new JButton("ÆÄÀÏ");

   public void messagePanel() {
      // TODO Auto-generated method stub

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Container c = getContentPane();
      c.setLayout(new FlowLayout());
      
      c.add(new JScrollPane(ta));
      c.add(tf);
      c.add(fileBtn);
      tf.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JTextField t = (JTextField)e.getSource();
            ta.append(t.getText() + "\n"); 
            t.setText(""); 
         }
      });
      setSize(300,300);
      setVisible(true);
        frame.setLocation(300,100);
       frame.pack();
       frame.setVisible(true);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
   }



}