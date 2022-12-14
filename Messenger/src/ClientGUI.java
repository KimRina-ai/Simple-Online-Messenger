
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.SystemColor;

public class ClientGUI extends JFrame implements ActionListener{

	
	private JTextArea ta = new JTextArea();
	private JTextField tf = new JTextField(18);
	private Client client = new Client();
	static String nickName;
	private JScrollPane sp =  new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private JButton btn = new JButton("Enter");
	private JButton btn2 = new JButton("Exit");
	private JPanel pl = new JPanel();

	public ClientGUI(String userNick) {
		
		setTitle(userNick+" 님의 채팅창");
		setBounds(400, 0, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(sp, BorderLayout.CENTER);
		ta.setBackground(SystemColor.activeCaption);
		ta.setLineWrap(true);
		ta.setEditable(false);
		
		pl.add(tf);
		pl.add(btn);
		pl.add(btn2);
		
		getContentPane().add(pl,"South");
		
		btn.setPreferredSize(new Dimension(80, 22));
		btn2.setPreferredSize(new Dimension(80, 22));
		
		tf.addActionListener(this);
		btn.addActionListener(this);
		btn2.addActionListener(this);
		
		client.setGui(this);
		client.setNickname(nickName);
		client.connet();
	}
	
	public static void main(String[] args) {
		/*Scanner scanner = new Scanner(System.in);
		
		System.out.println("닉네임 입력 : ");
		nickName = scanner.nextLine();
		scanner.close();*/
		
		
		new ClientGUI(nickName);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { // 말하면 보내면 부분
		String msg = nickName + " : " + tf.getText()+"\n";
		if(e.getSource()==tf || e.getSource()==btn) {
			if(tf.getText().equals("")) {
				return;
			}
			client.sendMessage(msg);
			sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());//맨밑으로
			tf.setText("");//택스트 필드 입력후 초기화 시켜줌
		}else if(e.getSource()==btn2){
			System.exit(0);
		}
	}

	public void appendMsg(String msg) {
		ta.append(msg);
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());//맨밑으로
		
	}
}