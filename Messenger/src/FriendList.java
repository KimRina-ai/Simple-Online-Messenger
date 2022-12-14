import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FriendList extends JFrame implements WindowListener{
    JFrame frame;
    //JPanel panel;
    JPanel panel1;
    //JPanel panel2;
    JPanel panel3;
    JPanel panel1_inside;
    JTextField search;
    JTree tree;
    JTree tree2;
    JButton editComment;
    JButton editNickname;   
    JButton refresh;
    JButton logoutBtn;
    JButton secessionBtn;
    JButton chatBtn;
    JButton submit;
    JButton openchat;
    JMenuItem profile;
    JMenuItem chat;
    JMenuItem file;
    Statement stmt = null;
    Statement stmt2 = null;
    Statement stmt3 = null;
    Statement stmt4 = null;
    Statement stmt5 = null;
    Statement stmt6 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    ResultSet rs3 = null;
    ResultSet rs4 = null;
    ResultSet rs5 = null;
    message chattingRoom;
    String url = "jdbc:mysql://localhost/users?serverTimezone=UTC&useSSL=false&&allowPublicKeyRetrieval=true&useSSL=false";
    String sql = null;
    String sql2 = null;
    String sql3 = null;
    String my_id = null;
    Connection conn = null;
    String userID = null;
    String userName = null;
    String userNick = null;
    String userHome = null;
    String userComment = null;
    String friend_comment = null;
   
    /*********** JM : START ****************/
    //프렌드 리스트 루트 노드 명 (상수 처리)
    final String FL_NODE_ROOT = "친구목록";
    final String FL_NODE_ONLINE = "접속중인 친구";
    final String FL_NODE_OFFLINE = "비접속중인 친구";
   
    //친구목록 만들기
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(FL_NODE_ROOT);
    /*********** JM : END 
  * @throws org.json.simple.parser.ParseException ****************/
   

    public void FriendListPanel(String id) throws IOException, ParseException, org.json.simple.parser.ParseException {
       my_id = id;
      
       Color b = new Color(0, 175, 240);
       Color b2 = new Color(204,239,252);
       JLabel title = new JLabel("내 프로필");
       
        Font font2 = new Font("Aharoni 굵게", Font.BOLD, 15);
        title.setFont(font2);
      

       editComment = new JButton("오늘의 한 마디 변경"); // 오늘의 한 마디 변경 버튼
       editNickname = new JButton("별명 변경"); // 별명 변경 버튼
       
       refresh = new JButton("새로고침"); // 새로고침 버튼
         submit = new JButton("검색"); // 검색 버튼
         search = new JTextField("search"); // 검색 필드
         frame = new JFrame("SGYPE");
         openchat = new JButton("오픈 채팅 들어가기"); // 오픈 채팅 버튼
       
         panel1 = new JPanel(new GridLayout(11,2));
         panel1.setBackground(b2);
         
         panel3 = new JPanel(); // 공공데이터
         panel3.setPreferredSize(new Dimension(600, 50));
         panel3.setBackground(b2);

         logoutBtn = new JButton("로그아웃"); // 로그아웃 버튼
         secessionBtn = new JButton("계정 탈퇴"); // 탈퇴 버튼
    
     
         ArrayList<Friend> friendData = new ArrayList<Friend>(); // 친구 리스트
      
         frame.add(new JScrollPane(tree), BorderLayout.WEST); // 친구 리스트 보여줌
         //연결된 DB에서 회원 정보를 뽑아옴 
         try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // db 연결
            String user = "root", passwd = "sqjfwk8282";
            conn = DriverManager.getConnection(url, user, passwd);
         
            stmt = conn.createStatement();
            stmt3 = conn.createStatement();
            stmt4 = conn.createStatement();
            stmt5 = conn.createStatement();
            stmt6 = conn.createStatement();
            
            sql = "select * from member where id = \'" + id +  "\' "; // 현재 접속된 유저의 정보 저장
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
               userID = rs.getString("id");
               userName = rs.getString("name");
               userNick = rs.getString("nickname");
               userHome = rs.getString("home");
               userComment = rs.getString("comments");
            }
         
            /*********** JM : START ****************/

            sql2 = "select * from member where id in (select friend_id from friend where id = '" + userID + "')";
            rs2 = stmt5.executeQuery(sql2);
            while(rs2.next()) {
               //sql에서 id, nickname, member_code를 Friend 형식으로 friendData에 저장하기
               Friend friend = new Friend(rs2.getString("id"),  rs2.getString("nickname"), Long.parseLong( rs2.getString("member_code")));
               friendData.add(friend);
            }
        
            refresh.addMouseListener(new MouseAdapter() { // 새로고침 버튼을 누르면 새 창이 나옴
               public void mouseClicked(MouseEvent e) {
                  if(e.getSource() == refresh) {
                     try {
                        frame.setVisible(false);
                        FriendList show = new FriendList();
                        show.FriendListPanel(userID);
                     }catch(Exception e1) {
                        e1.printStackTrace();
                     }
                  }
               }
            });
        

            submit.addMouseListener(new MouseAdapter() { // 검색 필드에 입력된 인풋을 토대로 검색 시작
               public void mouseClicked(MouseEvent e) {
                  if(e.getSource() == submit) {
                     sql3 = "select * from member where id = '" + search.getText() + "' or nickname = '" + search.getText() + "'";
                    // 아이디나 별명 둘다 유저 정보로 이어줌
                     try {
                        rs4 = stmt4.executeQuery(sql3);
                        String[] id = { "null" };
                        int i = 0;
                        while(rs4.next()) {
                           id[i] = "";
                           id[i] = rs4.getString("id");
                           if(rs4.wasNull() == true) 
                              id[i] = "null";
                           i++;
                               System.out.println("im here");
                        }
                        if(id[0].equals("null")) {
                           JOptionPane.showMessageDialog(null, 
                                 "no such user!", 
                                 "title", 
                                 JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                           new Profile(userID, id[0]); // 검색된 유저 프로필로 이동
                        }
                     } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                     }
                  }
               }
            });
      
            secessionBtn.addMouseListener(new MouseAdapter(){ //탈퇴 버튼
            @Override
               public void mouseClicked(MouseEvent e) {
                  if (e.getSource().equals(secessionBtn)) {
                     int var = JOptionPane.showConfirmDialog(null,  "really?", "secession", // 재차 물어봄
                           JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
                     String secessSql1 = "delete from friend where id = '" + id + "' "; // id가 외래 키이므로 친구 정보 모두 삭제 후
                     String secessSql2 = "delete from member where id = '" + id + "' "; // 탈퇴 진행
                  
                     if(var == 0) { 
                     
               
                        try {
                        	stmt6.executeUpdate(secessSql1);
                            stmt.executeUpdate(secessSql2);
              
                  
                        } catch (SQLException e1) {
                           // TODO Auto-generated catch block
                           e1.printStackTrace();
                        }
                        frame.dispose();
                     }
                  }
                  else {
                  
                  }
               }
            
            });
            logoutBtn.addMouseListener(new MouseAdapter(){ // 로그아웃 버튼
               @Override
               public void mouseClicked(MouseEvent e) {
                  int var = JOptionPane.showConfirmDialog(null,  "로그아웃 하시겠습니까?", "Logout", // 재차 확인
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
                  if(var == 0) { 
                     if (e.getSource().equals(logoutBtn)) {
                        sql = "update member set on_off = 'off' where id = '" + userID + "'"; 
                        try {
                           stmt5.executeUpdate(sql);
                        } catch (SQLException e1) {
                           // TODO Auto-generated catch block
                           e1.printStackTrace();
                        }
                        frame.dispose();
                     }}
                  else {
                  
                  }
               }
            });
         
            editComment.addActionListener(new AbstractAction() { // 오늘의 한 마디 변경 버튼
               @Override
               public void actionPerformed(ActionEvent e) {
                  new EditComment(userNick); // 변경창으로 이동
                  dispose();
                  setVisible(false);
               }
            });
         editNickname.addActionListener(new ActionListener() { // 닉네임 변경 버튼

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            new EditNickname(userNick); // 변경창으로 이동
            dispose();
            setVisible(false);
         }
            
         });

      
      // 친구목록 안에 있는 온라인, 오프라인 목록 만들기
      DefaultMutableTreeNode online = new DefaultMutableTreeNode(FL_NODE_ONLINE); // 온라인 노드
      DefaultMutableTreeNode offline = new DefaultMutableTreeNode(FL_NODE_OFFLINE); // 오프라인 노드
      
      
      ArrayList<DefaultMutableTreeNode> f = new ArrayList<DefaultMutableTreeNode>();

 
      
      // 온라인 목록과 오프라인 목록을 친구 목록에 추가하기
      root.add(online);
      root.add(offline);
      
      
         String on_off = "";
         String friend_id = "";
         stmt2 = conn.createStatement();
         sql = "select * from member where id IN (select friend_id from friend where id = '" + id + "')"; // 친구 검색
         rs3 = stmt2.executeQuery(sql);
         
         String index_on[] = new String[100];
         String index_off[] = new String[100];
         int k = 0;
         int j = 0;
         
         while(rs3.next()) {
            friend_id = rs3.getString("id");
            friend_comment = rs3.getString("comments");
            on_off = rs3.getString("on_off");
            
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(friend_id);
            
            if(on_off.equals("on")) { // online offline에 따라 트리 경로 다르게 함
               online.add(node);
               index_on[k] = friend_id;
               k++;
            }
            else {
               offline.add(node);
               index_off[j] = friend_id;
               j++;
            }   
         }
         // 공공 데이터 : 날씨 정보를 불러옴
      StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/      
      
      urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=ZpvgJRSokac08vieuw8vBfW7xqRnC6zEOwSyg5E3zYYwZ3JlZ0ziZXPJ8ArfSWEt1cGWrC0FAJbq2zJ2ZzMzkQ%3D%3D"); /*Service Key*/
      urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
      urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
      urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
      urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20221214", "UTF-8")); /*‘21년 6월 28일 발표*/
      urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0600", "UTF-8")); /*06시 발표(정시단위) */
      urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
      urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
      URL url = new URL(urlBuilder.toString());
      
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-type", "application/json");
      //System.out.println("Response code: " + conn.getResponseCode());
      BufferedReader rd;
      if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
          rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      } else {
          rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
      }
      
      StringBuilder sb = new StringBuilder();
      String line;
      String result = ""; /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
      while ((line = rd.readLine()) != null) {
          result = result.concat(line); /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
          sb.append(line);
      }
      // Json parser를 만들어 만들어진 문자열 데이터를 객체화 합니다.
      JSONParser parser = new JSONParser();
      JSONObject obj = (JSONObject)parser.parse(result);
      // Top레벨 단계인 response 키를 가지고 데이터를 파싱합니다.
      JSONObject parse_response = (JSONObject) obj.get("response");
      // response 로 부터 body 찾아옵니다.
      JSONObject parse_body = (JSONObject) parse_response.get("body");
      // body 로 부터 items 받아옵니다.
      JSONObject parse_items = (JSONObject) parse_body.get("items");
      // items로 부터 itemlist 를 받아오기 itemlist : 뒤에 [ 로 시작하므로 jsonarray이다
      JSONArray parse_item = (JSONArray) parse_items.get("item");

      JSONObject weather; // parse_item은 배열형태이기 때문에 하나씩 데이터를 하나씩 가져올때 사용합니다.
      String base_Date;
      String base_Time;
      String category;
      String value;
      
      String text = "", date_time = "", reh = "", rn1 = "", t1h ="";
      int count = 0;
      // 필요한 데이터만 가져오려고합니다.
      for(int i = 0 ; i < parse_item.size(); i++)
      {
          weather = (JSONObject) parse_item.get(i);
          base_Date = (String)weather.get("baseDate");
          base_Time = (String)weather.get("baseTime");
          category = (String)weather.get("category");
          value = (String)weather.get("obsrValue");

          // 출력합니다.
          if(count == 0) {
              date_time = "Date(Time)  :  " + base_Date + "(" + base_Time + ")\n";
          }

          switch(i) {
          case 1:
             reh = "습도 (" + category + ") : " + value + "%\n";               
             break;
          case 2:
             rn1 = "강수량 (" + category + ") : " + value + "mm\n";
             break;
          case 3:
             //System.out.print("기온 (" + category + ")   : " + value + "℃"); // T1H
             t1h = "기온 (" + category + ") : " + value + "℃";
             break;
          default:
             break;
          }
          count++;
      }
      text = date_time + " >>   " + reh + "   " + rn1 + "   " + t1h;
      System.out.println(text);
      JLabel data = new JLabel(text);
      panel3.add(data);
      
      rd.close();
      conn.disconnect();

      tree = new JTree(root); // 트리 생성
      tree.expandRow(1);
      tree.expandRow(5);
     
      
      
      
      JScrollPane js = new JScrollPane(tree); // 스크롤 페인으로 생성하여 친구가 많아도 확인 가능
      

      JPopupMenu popupMenu = new JPopupMenu(); // 마우스 우클릭 이벤트
      
      profile = new JMenuItem("프로필 보기"); // 우클릭 1st option - 프로필 보기
      chat = new JMenuItem("채팅하기"); // 2nd option - 채팅하기
      file = new JMenuItem("파일 보내기"); // 3rd option 파일 보내기
            
      popupMenu.add(profile);
      popupMenu.add(chat);
      popupMenu.add(file);
      
      chat.addActionListener(new ActionListener() { // 우클릭 - 채팅하기 옵션을 선택할 시
         public void actionPerformed(ActionEvent ev) {
            
        	 DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
             // 선택한 친구와 채팅 
        	 new Server();
        	 //new ServerGUI();
        	 new ClientGUI(userNick);
        	 new ClientGUI(selectedElement.getUserObject().toString());
         }
      });
      
      tree.setComponentPopupMenu(popupMenu);
      
      initPopupListener(tree, popupMenu); // 이벤트 리스너 적용
      
      profile.addActionListener(new ActionListener() { // 우클릭 - 프로필 옵션을 선택할 시
         public void actionPerformed(ActionEvent ev) {
            
            DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
            // 클릭한 리프 노드의 id를 통해 프로필 방문
            new friendProfile(selectedElement.getUserObject().toString());
         }
      });
      file.addActionListener(new ActionListener() { // 우클릭 - 파일 보내기 옵션을 선택할 시
         public void actionPerformed(ActionEvent ev) {
             File selectedFile;
             String savepathname;
             JFileChooser jfc = new JFileChooser("경로"); 
             
             int sv = jfc.showSaveDialog(frame);  // 파일 선택 가능
             
             if(sv == JFileChooser.APPROVE_OPTION) {
                
                selectedFile = jfc.getSelectedFile(); // 선택한 파일 경로
                savepathname = selectedFile.getAbsolutePath(); // 파일 경로
                System.out.println(selectedFile);
                System.out.println(savepathname);
             }else {
                JOptionPane.showMessageDialog(null, "경로를 선택하지 않았습니다." // 캔슬 시
                      , "경고", JOptionPane.WARNING_MESSAGE);
             }
            }

         
      });
      
      /*********** JM : END ****************/
      JLabel userNickPanel = new JLabel("별명 : "+ userNick,JLabel.CENTER);
      panel1.add(userNickPanel);
      JLabel userHomePanel = new JLabel("홈페이지 : " + userHome, JLabel.CENTER);
      panel1.add(userHomePanel);
      JLabel todayComment = new JLabel("오늘의 한 마디 : "+ userComment, JLabel.CENTER);
      panel1.add(todayComment);
      panel1.add(editNickname);
      panel1.add(editComment);
      panel1.add(refresh);
      panel1.add(logoutBtn);
      panel1.add(secessionBtn);
      panel1.add(search);          
      panel1.add(submit);
      panel1.add(openchat);
      
      frame.add(panel1,BorderLayout.NORTH);
      frame.add(js,BorderLayout.CENTER);
      //frame.add(chatBtn, BorderLayout.EAST);
      frame.add(panel3,BorderLayout.SOUTH);
      frame.setBounds(450, 100, 600, 800);
      frame.setResizable(false);
      frame.setVisible(true);
      frame.addWindowListener(this);
      
      } catch(Exception e) {
         e.printStackTrace();
         }
      }
      private MutableTreeNode f(int i) {
      // TODO Auto-generated method stub
      return null;
      }


      private DefaultMutableTreeNode DefaultMutableTreeNode(Object object) { 
      return null;
      }
      private static void initPopupListener(JTree tree, JPopupMenu popupMenu) { // 우클릭 리스너 메쏘드 구현
          // TODO Auto-generated method stub
        popupMenu.addPopupMenuListener((PopupMenuListener) new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                //get selected node's rectangle
                Rectangle rect = tree.getPathBounds(tree.getSelectionPath());
                Arrays.stream(popupMenu.getComponents()).forEach(c -> c.setEnabled(rect != null));
                if (rect == null) {
                    return;
                }

                Point p = new Point(rect.x + rect.width / 2, rect.y + rect.height);
                SwingUtilities.convertPointToScreen(p, tree);
                popupMenu.setLocation(p.x, p.y);
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });
    }
    private static JFrame createFrame() {
        JFrame frame = new JFrame("Popup On Shift + F10 Press");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 400));
        return frame;
    }

    @Override
    public void windowOpened(WindowEvent e) {
      // TODO Auto-generated method stub
       
    }

    @Override
    public void windowClosing(WindowEvent e) { // 윈도우를 닫으면 로그아웃 및 창 닫기
       int state = JOptionPane.showConfirmDialog(this, "종료하시겠습니까?");
      
        if(state == JOptionPane.YES_NO_OPTION)
        {
           sql = "update member set on_off = 'off' where id = '" + userID + "'"; 
           try {
              stmt.executeUpdate(sql);
           } catch (SQLException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
           }
           frame.setVisible(false);
        }
    }


    @Override
    public void windowClosed(WindowEvent e) {
       // TODO Auto-generated method stub
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