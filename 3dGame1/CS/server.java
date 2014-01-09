package CS;
/**
 *  <code>server.java</code>
 *  <p>功能:
 *  
 *  <p>Copyright 软件工程1101班 2014 All right reserved.
 *  @author 薛继光 aurora.xue@gmail.com 时间 2014-1-3 下午04:10:52	
 *  @version 1.0 
 *  </br>最后修改人 无
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
   
   //客户机是：
   client.getInetAddress().getHostName();
   
   in =client.getInputStream();
   out= client.getOutputStream();
  } catch (IOException ioe) {
  }
 }
 public String severin(){
	//ta.append("客户机说：" + str + "\n\n");
	 String Str = "";
	   try {
	    byte[] buf = new byte[256];
	    in.read(buf);
	    String str = new String(buf);
	    Str = str;
	    //ta.setText(str);
	    //ta.append("客户机说：" + str + "\n\n");
	   } catch (IOException e) {
	   }
	 
	   return Str;
 }
 
 public void serveout(int x){
	 try {
		   String Str = "生命："+x+"\n"+"体力："+x;
		   byte[] buf = Str.getBytes();
		   //tf.setText(null);
		   out.write(buf);
		   //ta.append("我说：" + str + "\n");
		  } catch (IOException ioe) {
		  }
 }

 //public static void main(String[] args) {
 // new server();
 //}
}
