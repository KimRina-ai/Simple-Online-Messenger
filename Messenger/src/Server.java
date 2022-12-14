
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
	
	private ServerSocket serverSocket; //서버 소켓
	private Socket socket; // 받아올 소켓 저장
	private ServerGUI gui;
	private String msg;
	
	//Map과 HashMap에 대해 알아보자 //사용자들의 정보를 저장하는 맵이라고 함
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
				Collections.synchronizedMap(clientsMap); // 엉키지않게 정리해줌
				serverSocket = new ServerSocket(2222);
				
				while(true) {
					System.out.println("서버 연결 대기중...");
					socket = serverSocket.accept(); // 계속 사용자를 받는것
					System.out.println(socket.getInetAddress() + "에서 접속했습니다.");
						
					Receiver receiver = new Receiver(socket);
					receiver.start();
				
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
	}
		
	

	// 맵의 내용(클라이언트) 저장과 삭제
	private void addClient(String nick, DataOutputStream out) {
		String message = nick + "님 입장.\n";
		sendMessage(message);
		gui.appendMsg(message);
		clientsMap.put(nick, out);	// out의 의미는??	
	}
	
	public void removeClient(String nick) {
		String message = nick + "님 대화방을 나갔습니다.\n";
		sendMessage(message);
		gui.appendMsg(message);
		clientsMap.remove(nick);
	}
	
	//메세지 내용 전파
	public void sendMessage(String msg) {
		Iterator<String> it = clientsMap.keySet().iterator();//Iterator 알아보기
		String key="";
		
		while(it.hasNext()) { // 잘모르겠음
			key=it.next();
			try {
				clientsMap.get(key).writeUTF(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	class Receiver extends Thread{ //리시버는 계속 받아서 넘겨줌
		private DataInputStream in;
		private DataOutputStream out;
		private String nick;
		
		public Receiver(Socket socket) {
			
			try {
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				
				nick = in.readUTF();//readUTF 는???
				addClient(nick,out);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public void run() {
			try { // 계속 듣기만!!!
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