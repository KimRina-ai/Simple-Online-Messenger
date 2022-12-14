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
    //������ ����Ʈ ��Ʈ ��� �� (��� ó��)
    final String FL_NODE_ROOT = "ģ�����";
    final String FL_NODE_ONLINE = "�������� ģ��";
    final String FL_NODE_OFFLINE = "���������� ģ��";
   
    //ģ����� �����
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(FL_NODE_ROOT);
    /*********** JM : END 
  * @throws org.json.simple.parser.ParseException ****************/
   

    public void FriendListPanel(String id) throws IOException, ParseException, org.json.simple.parser.ParseException {
       my_id = id;
      
       Color b = new Color(0, 175, 240);
       Color b2 = new Color(204,239,252);
       JLabel title = new JLabel("�� ������");
       
        Font font2 = new Font("Aharoni ����", Font.BOLD, 15);
        title.setFont(font2);
      

       editComment = new JButton("������ �� ���� ����"); // ������ �� ���� ���� ��ư
       editNickname = new JButton("���� ����"); // ���� ���� ��ư
       
       refresh = new JButton("���ΰ�ħ"); // ���ΰ�ħ ��ư
         submit = new JButton("�˻�"); // �˻� ��ư
         search = new JTextField("search"); // �˻� �ʵ�
         frame = new JFrame("SGYPE");
         openchat = new JButton("���� ä�� ����"); // ���� ä�� ��ư
       
         panel1 = new JPanel(new GridLayout(11,2));
         panel1.setBackground(b2);
         
         panel3 = new JPanel(); // ����������
         panel3.setPreferredSize(new Dimension(600, 50));
         panel3.setBackground(b2);

         logoutBtn = new JButton("�α׾ƿ�"); // �α׾ƿ� ��ư
         secessionBtn = new JButton("���� Ż��"); // Ż�� ��ư
    
     
         ArrayList<Friend> friendData = new ArrayList<Friend>(); // ģ�� ����Ʈ
      
         frame.add(new JScrollPane(tree), BorderLayout.WEST); // ģ�� ����Ʈ ������
         //����� DB���� ȸ�� ������ �̾ƿ� 
         try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // db ����
            String user = "root", passwd = "sqjfwk8282";
            conn = DriverManager.getConnection(url, user, passwd);
         
            stmt = conn.createStatement();
            stmt3 = conn.createStatement();
            stmt4 = conn.createStatement();
            stmt5 = conn.createStatement();
            stmt6 = conn.createStatement();
            
            sql = "select * from member where id = \'" + id +  "\' "; // ���� ���ӵ� ������ ���� ����
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
               //sql���� id, nickname, member_code�� Friend �������� friendData�� �����ϱ�
               Friend friend = new Friend(rs2.getString("id"),  rs2.getString("nickname"), Long.parseLong( rs2.getString("member_code")));
               friendData.add(friend);
            }
        
            refresh.addMouseListener(new MouseAdapter() { // ���ΰ�ħ ��ư�� ������ �� â�� ����
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
        

            submit.addMouseListener(new MouseAdapter() { // �˻� �ʵ忡 �Էµ� ��ǲ�� ���� �˻� ����
               public void mouseClicked(MouseEvent e) {
                  if(e.getSource() == submit) {
                     sql3 = "select * from member where id = '" + search.getText() + "' or nickname = '" + search.getText() + "'";
                    // ���̵� ���� �Ѵ� ���� ������ �̾���
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
                           new Profile(userID, id[0]); // �˻��� ���� �����ʷ� �̵�
                        }
                     } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                     }
                  }
               }
            });
      
            secessionBtn.addMouseListener(new MouseAdapter(){ //Ż�� ��ư
            @Override
               public void mouseClicked(MouseEvent e) {
                  if (e.getSource().equals(secessionBtn)) {
                     int var = JOptionPane.showConfirmDialog(null,  "really?", "secession", // ���� ���
                           JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
                     String secessSql1 = "delete from friend where id = '" + id + "' "; // id�� �ܷ� Ű�̹Ƿ� ģ�� ���� ��� ���� ��
                     String secessSql2 = "delete from member where id = '" + id + "' "; // Ż�� ����
                  
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
            logoutBtn.addMouseListener(new MouseAdapter(){ // �α׾ƿ� ��ư
               @Override
               public void mouseClicked(MouseEvent e) {
                  int var = JOptionPane.showConfirmDialog(null,  "�α׾ƿ� �Ͻðڽ��ϱ�?", "Logout", // ���� Ȯ��
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
         
            editComment.addActionListener(new AbstractAction() { // ������ �� ���� ���� ��ư
               @Override
               public void actionPerformed(ActionEvent e) {
                  new EditComment(userNick); // ����â���� �̵�
                  dispose();
                  setVisible(false);
               }
            });
         editNickname.addActionListener(new ActionListener() { // �г��� ���� ��ư

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            new EditNickname(userNick); // ����â���� �̵�
            dispose();
            setVisible(false);
         }
            
         });

      
      // ģ����� �ȿ� �ִ� �¶���, �������� ��� �����
      DefaultMutableTreeNode online = new DefaultMutableTreeNode(FL_NODE_ONLINE); // �¶��� ���
      DefaultMutableTreeNode offline = new DefaultMutableTreeNode(FL_NODE_OFFLINE); // �������� ���
      
      
      ArrayList<DefaultMutableTreeNode> f = new ArrayList<DefaultMutableTreeNode>();

 
      
      // �¶��� ��ϰ� �������� ����� ģ�� ��Ͽ� �߰��ϱ�
      root.add(online);
      root.add(offline);
      
      
         String on_off = "";
         String friend_id = "";
         stmt2 = conn.createStatement();
         sql = "select * from member where id IN (select friend_id from friend where id = '" + id + "')"; // ģ�� �˻�
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
            
            if(on_off.equals("on")) { // online offline�� ���� Ʈ�� ��� �ٸ��� ��
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
         // ���� ������ : ���� ������ �ҷ���
      StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/      
      
      urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=ZpvgJRSokac08vieuw8vBfW7xqRnC6zEOwSyg5E3zYYwZ3JlZ0ziZXPJ8ArfSWEt1cGWrC0FAJbq2zJ2ZzMzkQ%3D%3D"); /*Service Key*/
      urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*��������ȣ*/
      urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*�� ������ ��� ��*/
      urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*��û�ڷ�����(XML/JSON) Default: XML*/
      urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20221214", "UTF-8")); /*��21�� 6�� 28�� ��ǥ*/
      urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0600", "UTF-8")); /*06�� ��ǥ(���ô���) */
      urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*���������� X ��ǥ��*/
      urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*���������� Y ��ǥ��*/
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
      // Json parser�� ����� ������� ���ڿ� �����͸� ��üȭ �մϴ�.
      JSONParser parser = new JSONParser();
      JSONObject obj = (JSONObject)parser.parse(result);
      // Top���� �ܰ��� response Ű�� ������ �����͸� �Ľ��մϴ�.
      JSONObject parse_response = (JSONObject) obj.get("response");
      // response �� ���� body ã�ƿɴϴ�.
      JSONObject parse_body = (JSONObject) parse_response.get("body");
      // body �� ���� items �޾ƿɴϴ�.
      JSONObject parse_items = (JSONObject) parse_body.get("items");
      // items�� ���� itemlist �� �޾ƿ��� itemlist : �ڿ� [ �� �����ϹǷ� jsonarray�̴�
      JSONArray parse_item = (JSONArray) parse_items.get("item");

      JSONObject weather; // parse_item�� �迭�����̱� ������ �ϳ��� �����͸� �ϳ��� �����ö� ����մϴ�.
      String base_Date;
      String base_Time;
      String category;
      String value;
      
      String text = "", date_time = "", reh = "", rn1 = "", t1h ="";
      int count = 0;
      // �ʿ��� �����͸� �����������մϴ�.
      for(int i = 0 ; i < parse_item.size(); i++)
      {
          weather = (JSONObject) parse_item.get(i);
          base_Date = (String)weather.get("baseDate");
          base_Time = (String)weather.get("baseTime");
          category = (String)weather.get("category");
          value = (String)weather.get("obsrValue");

          // ����մϴ�.
          if(count == 0) {
              date_time = "Date(Time)  :  " + base_Date + "(" + base_Time + ")\n";
          }

          switch(i) {
          case 1:
             reh = "���� (" + category + ") : " + value + "%\n";               
             break;
          case 2:
             rn1 = "������ (" + category + ") : " + value + "mm\n";
             break;
          case 3:
             //System.out.print("��� (" + category + ")   : " + value + "��"); // T1H
             t1h = "��� (" + category + ") : " + value + "��";
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

      tree = new JTree(root); // Ʈ�� ����
      tree.expandRow(1);
      tree.expandRow(5);
     
      
      
      
      JScrollPane js = new JScrollPane(tree); // ��ũ�� �������� �����Ͽ� ģ���� ���Ƶ� Ȯ�� ����
      

      JPopupMenu popupMenu = new JPopupMenu(); // ���콺 ��Ŭ�� �̺�Ʈ
      
      profile = new JMenuItem("������ ����"); // ��Ŭ�� 1st option - ������ ����
      chat = new JMenuItem("ä���ϱ�"); // 2nd option - ä���ϱ�
      file = new JMenuItem("���� ������"); // 3rd option ���� ������
            
      popupMenu.add(profile);
      popupMenu.add(chat);
      popupMenu.add(file);
      
      chat.addActionListener(new ActionListener() { // ��Ŭ�� - ä���ϱ� �ɼ��� ������ ��
         public void actionPerformed(ActionEvent ev) {
            
        	 DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
             // ������ ģ���� ä�� 
        	 new Server();
        	 //new ServerGUI();
        	 new ClientGUI(userNick);
        	 new ClientGUI(selectedElement.getUserObject().toString());
         }
      });
      
      tree.setComponentPopupMenu(popupMenu);
      
      initPopupListener(tree, popupMenu); // �̺�Ʈ ������ ����
      
      profile.addActionListener(new ActionListener() { // ��Ŭ�� - ������ �ɼ��� ������ ��
         public void actionPerformed(ActionEvent ev) {
            
            DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
            // Ŭ���� ���� ����� id�� ���� ������ �湮
            new friendProfile(selectedElement.getUserObject().toString());
         }
      });
      file.addActionListener(new ActionListener() { // ��Ŭ�� - ���� ������ �ɼ��� ������ ��
         public void actionPerformed(ActionEvent ev) {
             File selectedFile;
             String savepathname;
             JFileChooser jfc = new JFileChooser("���"); 
             
             int sv = jfc.showSaveDialog(frame);  // ���� ���� ����
             
             if(sv == JFileChooser.APPROVE_OPTION) {
                
                selectedFile = jfc.getSelectedFile(); // ������ ���� ���
                savepathname = selectedFile.getAbsolutePath(); // ���� ���
                System.out.println(selectedFile);
                System.out.println(savepathname);
             }else {
                JOptionPane.showMessageDialog(null, "��θ� �������� �ʾҽ��ϴ�." // ĵ�� ��
                      , "���", JOptionPane.WARNING_MESSAGE);
             }
            }

         
      });
      
      /*********** JM : END ****************/
      JLabel userNickPanel = new JLabel("���� : "+ userNick,JLabel.CENTER);
      panel1.add(userNickPanel);
      JLabel userHomePanel = new JLabel("Ȩ������ : " + userHome, JLabel.CENTER);
      panel1.add(userHomePanel);
      JLabel todayComment = new JLabel("������ �� ���� : "+ userComment, JLabel.CENTER);
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
      private static void initPopupListener(JTree tree, JPopupMenu popupMenu) { // ��Ŭ�� ������ �޽�� ����
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
    public void windowClosing(WindowEvent e) { // �����츦 ������ �α׾ƿ� �� â �ݱ�
       int state = JOptionPane.showConfirmDialog(this, "�����Ͻðڽ��ϱ�?");
      
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