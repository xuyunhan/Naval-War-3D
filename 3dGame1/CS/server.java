package CS;
/**
 *  <code>server.java</code>
 *  <p>����:
 *  
 *  <p>Copyright �������1101�� 2014 All right reserved.
 *  @author Ѧ�̹� aurora.xue@gmail.com ʱ�� 2014-1-3 ����04:10:52	
 *  @version 1.0 
 *  </br>����޸��� ��
 */
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class server {

 ServerSocket server;
 Socket client;
 InputStream in;
 OutputStream out;

 public server() {
  
  try {
   server = new ServerSocket(4000);
   client = server.accept();
   
   //�ͻ����ǣ�
   client.getInetAddress().getHostName();
   
   in =client.getInputStream();
   out= client.getOutputStream();
  } catch (IOException ioe) {
  }
 }
 public String severin(){
	//ta.append("�ͻ���˵��" + str + "\n\n");
	 String Str = "";
	   try {
	    byte[] buf = new byte[256];
	    in.read(buf);
	    String str = new String(buf);
	    Str = str;
	    //ta.setText(str);
	    //ta.append("�ͻ���˵��" + str + "\n\n");
	   } catch (IOException e) {
	   }
	 
	   return Str;
 }
 
 public void serveout(int x){
	 try {
		   String Str = "������"+x+"\n"+"������"+x;
		   byte[] buf = Str.getBytes();
		   //tf.setText(null);
		   out.write(buf);
		   //ta.append("��˵��" + str + "\n");
		  } catch (IOException ioe) {
		  }
 }

 //public static void main(String[] args) {
 // new server();
 //}
}
