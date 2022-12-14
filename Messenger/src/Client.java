
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
			System.out.println("������ ����ƽ��ϴ�.");
			
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			out.writeUTF(nickName); // �������ڸ��� �г��� �����ϸ� �̰��� �г������� �ν���
			System.out.println("Ŭ���̾�Ʈ : �޼��� ���ۿϷ�");
			
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