
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server {
	
	private ServerSocket serverSocket; //���� ����
	private Socket socket; // �޾ƿ� ���� ����
	private ServerGUI gui;
	private String msg;
	
	//Map�� HashMap�� ���� �˾ƺ��� //����ڵ��� ������ �����ϴ� ���̶�� ��
	private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
	
	public final void setGui(ServerGUI gui) {
		this.gui = gui;
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.setting();
	}
	
	public void setting() {
		
			
			try {
				Collections.synchronizedMap(clientsMap); // ��Ű���ʰ� ��������
				serverSocket = new ServerSocket(2222);
				
				while(true) {
					System.out.println("���� ���� �����...");
					socket = serverSocket.accept(); // ��� ����ڸ� �޴°�
					System.out.println(socket.getInetAddress() + "���� �����߽��ϴ�.");
						
					Receiver receiver = new Receiver(socket);
					receiver.start();
				
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
	}
		
	

	// ���� ����(Ŭ���̾�Ʈ) ����� ����
	private void addClient(String nick, DataOutputStream out) {
		String message = nick + "�� ����.\n";
		sendMessage(message);
		gui.appendMsg(message);
		clientsMap.put(nick, out);	// out�� �ǹ̴�??	
	}
	
	public void removeClient(String nick) {
		String message = nick + "�� ��ȭ���� �������ϴ�.\n";
		sendMessage(message);
		gui.appendMsg(message);
		clientsMap.remove(nick);
	}
	
	//�޼��� ���� ����
	public void sendMessage(String msg) {
		Iterator<String> it = clientsMap.keySet().iterator();//Iterator �˾ƺ���
		String key="";
		
		while(it.hasNext()) { // �߸𸣰���
			key=it.next();
			try {
				clientsMap.get(key).writeUTF(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	class Receiver extends Thread{ //���ù��� ��� �޾Ƽ� �Ѱ���
		private DataInputStream in;
		private DataOutputStream out;
		private String nick;
		
		public Receiver(Socket socket) {
			
			try {
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				
				nick = in.readUTF();//readUTF ��???
				addClient(nick,out);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public void run() {
			try { // ��� ��⸸!!!
				while(in != null) {
					msg = in.readUTF();
					sendMessage(msg);
					gui.appendMsg(msg);
				}
				
			} catch (IOException e) {
				removeClient(nick);
			}
			
		}
		
	}
}