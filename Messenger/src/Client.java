
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private ClientGUI gui;
	private String msg;
	private String nickName;
	

	public void setGui(ClientGUI gui) {
		this.gui = gui;
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.connet();
	}
	
	public void connet() {
		try {
			socket = new Socket("127.0.0.1",2222);
			System.out.println("서버에 연결됐습니다.");
			
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			out.writeUTF(nickName); // 접속하자마자 닉네임 전송하면 이것을 닉네임으로 인식함
			System.out.println("클라이언트 : 메세지 전송완료");
			
			while(in != null) {
				msg=in.readUTF();
				gui.appendMsg(msg);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void sendMessage(String msg) {
		try {
			out.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void setNickname(String nickName) {
		this.nickName = nickName;
		
	}

}