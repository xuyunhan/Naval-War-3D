package CS;
/**
 *  <code>client.java</code>
 *  <p>功能:
 *  
 *  <p>Copyright 软件工程1101班 2014 All right reserved.
 *  @author 薛继光 aurora.xue@gmail.com 时间 2014-1-3 下午04:11:32	
 *  @version 1.0 
 *  </br>最后修改人 无
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
   //ta.append("服务器是：" + client.getInetAddress().getHostName() + "\n\n");
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
		    //ta.append("服务器说：" + str + "\n");
		   } catch (IOException e) {
		   }
		   return Str;
 }
 public void clientout(int x){
	 try {
		   //String str = "";
		 String Str = "生命："+x+"\n"+"体力："+x;
		   byte[] buf = Str.getBytes();
		   //tf.setText(null);
		   out.write(buf);
		   //ta.append("我说：" + str + "\n");
		  } catch (IOException iOE) {
		  }
 }

 //public static void main(String args[]) {
 // new client();
 //}
}
