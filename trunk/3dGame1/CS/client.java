package CS;
/**
 *  <code>client.java</code>
 *  <p>����:
 *  
 *  <p>Copyright �������1101�� 2014 All right reserved.
 *  @author Ѧ�̹� aurora.xue@gmail.com ʱ�� 2014-1-3 ����04:11:32	
 *  @version 1.0 
 *  </br>����޸��� ��
 */
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class client{

 Socket client;
 InputStream in;
 OutputStream out;

 public client() {

  try {
   //client = new Socket(InetAddress.getLocalHost(), 4000);
	  client = new Socket("192.168.137.128", 4000);
   //ta.append("�������ǣ�" + client.getInetAddress().getHostName() + "\n\n");
   in = client.getInputStream();
   out = client.getOutputStream();
  } catch (IOException ioe) {
  }
  
 }

 public String clientin(){
	 String Str = "";
		   try {
		    byte[] buf = new byte[256];
		    in.read(buf);
		    String str = new String(buf);
		    Str = str;
		    //ta.append("������˵��" + str + "\n");
		   } catch (IOException e) {
		   }
		   return Str;
 }
 public void clientout(int x){
	 try {
		   //String str = "";
		 String Str = "������"+x+"\n"+"������"+x;
		   byte[] buf = Str.getBytes();
		   //tf.setText(null);
		   out.write(buf);
		   //ta.append("��˵��" + str + "\n");
		  } catch (IOException iOE) {
		  }
 }

 //public static void main(String args[]) {
 // new client();
 //}
}
