

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.SystemColor;

public class ServerGUI extends JFrame implements ActionListener {

	
	private JTextArea ta = new JTextArea();
	private JTextField tf = new JTextField(18);
	private Server server = new Server(); // Server Class와 연결시킴
	private JPanel pl = new JPanel();
	private JScrollPane sp; 
	private JButton btn = new JButton("Enter");
	private JButton btn2 = new JButton("Exit");
	
	public ServerGUI() {
		
		setTitle("Direct");
		setBounds(0, 0, 389, 435);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		getContentPane().setLayout(new BorderLayout());
		pl.setBackground(Color.WHITE);
		
		pl.add(tf);
		pl.add(btn);
		pl.add(btn2);
		getContentPane().add(pl,"South");
		ta.setBackground(SystemColor.activeCaption);
		
		
		ta.setLineWrap(true); // 꽉차면 다음줄로 가게 해줌
		ta.setEditable(false); // 수정불가능
		
		pl.add(ta);
		sp =  new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(sp, BorderLayout.CENTER);
		btn.setPreferredSize(new Dimension(80, 22));
		btn2.setPreferredSize(new Dimension(80, 22));
		
		tf.addActionListener(this);
		btn.addActionListener(this);
		btn2.addActionListener(this);
		
		server.setGui(this);
		server.setting(); // GUI가 실행될때 서버도 같이 동작함
		
	}
	
	public static void main(String[] args) {
		new ServerGUI();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = "서버 : " + tf.getText()+"\n";
		if(e.getSource()==tf || e.getSource()==btn) {
			if(tf.getText().equals("")) {
				return;
			}
			ta.append(msg);
			tf.setText("");
			server.sendMessage(msg);
			sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());//맨밑으로
		}else if(e.getSource()==btn2){
			System.exit(0);
		}
	}
	public void appendMsg(String msg) {
		ta.append(msg);
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());//맨밑으로
	}
}