import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import game.GameUser;

class Client_f extends JFrame implements ActionListener {
	
	JLabel User1, User2, VS;
	 static JLabel Result;
	 JButton Roc, Sci, Pap;
	 
	 Socket ClientSocket;
	 BufferedReader inFromServer;
	 DataOutputStream outToServer;
	 
	 GameUser user;
	 Check check;
	 
	 String response;
	 String console;
	 JTextField textField;
	 JTextArea messageArea;
	 Border border = BorderFactory.createLineBorder(Color.black);
	 private static String host = "localhost";
	 private static Integer port = 1337;
	 ImageIcon Icon[] = { new ImageIcon("./src/roc_f.jpg"), new ImageIcon("./src/sci_f.jpg"),
	   new ImageIcon("./src/pap_f.jpg"),
	   new ImageIcon("./vs.png") };
	 public Client_f() throws UnknownHostException, IOException {
	  user = new GameUser();
	  check = new Check();
	  
	  ClientSocket = new Socket(Client_f.host, Client_f.port);
	  outToServer = new DataOutputStream(ClientSocket.getOutputStream());
	  inFromServer = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
	  setBounds(100, 100, 400, 520);
	  setTitle("데이터 통신 과제");
	  setLayout(null);
	  Container ct = getContentPane();
	  ct.setBackground(Color.white);   
	  User1 = new JLabel(new ImageIcon("./src/seoho.jpg"));
	  User1.setBounds(10, 10, 60, 60);
	  VS = new JLabel(new ImageIcon("./src/versus_f.jpg"));
	  VS.setBounds(65, 10, 60, 60);
	 
	  User2 = new JLabel(new ImageIcon("./src/simdae.jpg"));
	  User2.setBounds(120, 10, 60, 60);
	  Result = new JLabel();
	  Result.setFont(new Font("돋움", Font.BOLD, 10));
	  Result.setBounds(10, 50, 300, 80);
	  messageArea = new JTextArea(8,40);
	  messageArea.setBounds(10, 100, 365, 300);
	  messageArea.setBorder(border);
	  messageArea.append(console);
	  textField = new JTextField(40);
	  textField.setBounds(10,420,365,20 );
	  textField.setBorder(border);
	  Roc = new JButton(Icon[0]);
	  Roc.setBounds(205, 10, 50, 50);
	  Roc.setBorderPainted(false);
	  Roc.setFocusPainted(false);
	  Sci = new JButton(Icon[1]);
	  Sci.setBounds(267, 10, 50, 50);
	  Sci.setBorderPainted(false);
	  Sci.setFocusPainted(false);
	  Pap = new JButton(Icon[2]);
	  Pap.setBounds(331, 10, 50, 50);
	  Pap.setBorderPainted(false);
	  Pap.setFocusPainted(false);
	  Roc.addActionListener(this);
	  Sci.addActionListener(this);
	  Pap.addActionListener(this);
	  textField.addActionListener(this);
	  textField.setEnabled(false);
	  Roc.setEnabled(false);
	  Sci.setEnabled(false);
	  Pap.setEnabled(false);
	  ct.add(User1);
	  ct.add(User2);
	  ct.add(VS);
	  ct.add(Roc);
	  ct.add(Sci);
	  ct.add(Pap);
	  ct.add(Result);
	  ct.add(messageArea);
	  ct.add(textField);
	  setDefaultCloseOperation(EXIT_ON_CLOSE);
	  
	  messageArea.setEditable(false);
	  setVisible(true);
	 }
	 
	 public void runClient() {
	  
	  String result = "";
	  boolean isChange = true; 
	  boolean wait = false;
	  
	  String str = "";
	  
	  try {
	   
	   System.out.println("1");
	   str = inFromServer.readLine();
	   System.out.println(str);
	   messageArea.setText(str);
	   
	   wait = check.checkBool(inFromServer.readLine());
	   
	   if(wait) {
	    System.out.println("wait : " + wait);
	    str = inFromServer.readLine();
	    messageArea.setText(str);
	    System.out.println(str);
	    
	    Roc.setEnabled(true);
	    Sci.setEnabled(true);
	    Pap.setEnabled(true);
	    
	    System.out.println("3");
	    
	    result = inFromServer.readLine();
	    str = inFromServer.readLine();
	    messageArea.setText("result : " + str);
	    System.out.println("result : " + str); 
	   }
	   
	   Roc.setEnabled(false);
	   Sci.setEnabled(false);
	   Pap.setEnabled(false);
	   
	   System.out.println("4");
	   messageArea.setText(str + "\n");
	   System.out.println(str);
	   
	   while (isChange) {
	    
	    System.out.println("Start Game");
	    user.setRole(check.checkBool(result));
	    
	    outToServer.writeBytes(check.boolToString(user.getRole()));
	    outToServer.flush();
	    
	    if(user.getRole()) {
	     messageArea.append("Submit Problem (Format : ?x?) !\n\n");
	     textField.setEnabled(true);
	     
	     str = inFromServer.readLine();
	     
	     messageArea.append("Problem : " + inFromServer.readLine() + "\n");
	     
	     if(str.equals("Accept")) {
	      System.out.println("Accept");
	      textField.setEnabled(false);
	     }
	     messageArea.append("Problem : " + inFromServer.readLine() + "\n");
	     
	    }else {
	     messageArea.append("Submit Answer !\n\n");
	     
	     str = inFromServer.readLine();
	     
	     messageArea.append("Problem : " + inFromServer.readLine() + "\n");
	     
	     textField.setEnabled(true);
	     
	     messageArea.append("Problem : " + inFromServer.readLine() + "\n");
	    }
	    
	    isChange = check.checkBool(inFromServer.readLine());
	    System.out.println("isChange : " + isChange);
	    
	    str = inFromServer.readLine();
	    System.out.println(str);
	    messageArea.append(str + "\n");
	    
	    if (isChange) {
	     
	     result = inFromServer.readLine();
	     user.setRole(check.checkBool(result));
	     messageArea.append("Wait...");
	     try {
	      Thread.sleep(2000);
	      messageArea.setText("");
	     }catch(InterruptedException e) {
	      
	     }
	    } else {
	     textField.setEnabled(false);
	     break;
	    }
	   }
	   
	   ClientSocket.close();
	   
	  } catch(IOException ee) {
	   
	  }
	 
	 
	 }
	 public void chkResult(int User2) {
	 
	  try {
	   if (User2 == 1) {
	    outToServer.writeByte(4);
	    outToServer.flush();
	   } else if (User2 == 2) {
	    outToServer.writeByte(3);
	    outToServer.flush();
	    
	   } else if (User2 == 3) {
	    outToServer.writeByte(5);
	    outToServer.flush();
	   }
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }
	 @Override
	 public void actionPerformed(ActionEvent e) {
	  Object ob = e.getSource();
	  if (ob == Roc) {
	   chkResult(1);
	  } else if (ob == Sci) {
	   chkResult(2);
	  }
	  else if (ob == Pap) {
	   chkResult(3);
	  }
	  
	  else if (ob == textField) {
	   
	   String msg = textField.getText().trim();
	   if(msg == null || msg.length() == 0) {
	    textField.setText("");
	    textField.requestFocus();
	    return;
	   }
	   else {
	    try {
	     outToServer.write((msg + "\n").getBytes());
	    } catch (Exception ee) {
	     ee.printStackTrace();
	    }
	   }
	  }
	 }
	 public static void main(String args[]) throws Exception {
	  Client_f client  = new Client_f();
	  
	  client.runClient();
	 } 
}
