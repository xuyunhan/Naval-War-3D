package HCQ;

import java.awt.event.*;
import java.io.IOException;

import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;



import OBJLoader.OBJModel;

import com.sun.jmx.snmp.SnmpPeer;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.gl2.GLUT;
import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.awt.GLCanvas;  

import common.TextureLoader;
import java.math.*;
import java.text.DecimalFormat;



import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
import javax.media.opengl.*;  
import com.sun.opengl.util.Animator;  
import com.sun.opengl.util.FPSAnimator;  
import javax.media.opengl.awt.GLCanvas; 
import javax.media.opengl.GLCapabilities;

public class JOGL06_Texture implements GLEventListener, KeyListener, MouseListener,MouseMotionListener
{
  private GL2 gl;
  private GLU glu;
 // private GLCapabilities caps;
 // private GLCanvas canvas;
  private MouseEvent mouse;
  private Animator animator;
  //贴图
  private Texture  pngtexture1,pngtexture2;
  private Texture bmptexture[] = new Texture[5];
  
  private float xrot;//绕X轴
  private float yrot;//绕Y轴
  private float zrot = 0f;//绕Z轴
  
  private String modelName;
  private float maxSize;
  float forwardZ;
  
  //摄像机参数
  float	 g_eye[]= new float[3];
  float	 g_look[]= new float[3];
  float	 rad_xz;	
  float	 g_Angle;
  float	 g_elev;	
  
  //鼠标始末点
  private int firstMoveX;
  private int lastMoveX;
  private int firstMoveY;
  private int lastMoveY;
  private int isMove;
  private int isPicture;
  
  //
  float MAP = 40;//	MAP_W*MAP_SCALE/2
  
  //摄像机步长
  float speed=1f;
  
  //准星
  private Counter Crosshair;
  
  private Counter FPS;
  private OBJModel model;
  private OBJModel model2;
  
  //子弹
  float shoot[]  = new float[3];
  boolean ifshoot;
  
  float te  = 5;
  float text[]  = new float[3];

  public JOGL06_Texture()
  {
	  
 //   super("Naval War 3D");

    modelName = "formula";

    maxSize = 5f;
 //   caps = new GLCapabilities(null);
 //   canvas = new GLCanvas(caps);
 //   canvas.addGLEventListener(this);
  //  canvas.addKeyListener(this);
  //  canvas.addMouseListener(this);
  //  canvas.addMouseMotionListener(this);
  //  getContentPane().add(canvas);
  }

  //程序的调用的主入口
 /* public void run()
  {
    setSize(800, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    canvas.requestFocusInWindow();
    canvas.swapBuffers();
    animator.start();
  }*/


  public void init(GLAutoDrawable drawable)
  {
	    gl = drawable.getGL().getGL2();
	    glu = new GLU();  
	    gl.glShadeModel(GL2.GL_SMOOTH);
	    gl.glClearColor(0.0f, 0.0f, 0.0f, 0f);
	    gl.glClearDepth(1.0f);							// 设置深度缓存
		gl.glEnable(GL.GL_DEPTH_TEST);					// 启用深度测试
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);			// 告诉系统对透视进行修正
//	    animator=new Animator(canvas);//实例化对canvas，刷新

		//摄像机参数初始化
		g_eye[0]= MAP;
		g_eye[2]= -MAP;
		
		//子弹
		shoot[0] = g_eye[0];
		shoot[2] = g_eye[2];
		shoot[1] = 1.8f;
		ifshoot = false;
	
		g_Angle = 0;
		g_elev = 0;	
		
		//初始化鼠标始末点
		firstMoveX = 0;
		lastMoveX = 0;
		firstMoveY = 0;
		lastMoveY = 0;
		isMove = 0;
		isPicture = 0;
		//forwardZ = -18;
		forwardZ = -45;
		
		//
		Crosshair = new Counter(drawable, 35);
		FPS = new Counter(drawable, 18);
		
		model = new OBJModel(modelName,maxSize,gl,true);
		model2 = new OBJModel(modelName,maxSize,gl,true);
		
	    
 //--------------新添加代码-------
    gl.glEnable(GL.GL_TEXTURE_2D);//启动纹理映射
    pngtexture1 = TextureLoader.load("data/jogl.png");
    pngtexture2 = TextureLoader.load("data/Crate.bmp");
    
    bmptexture[0]= TextureLoader.load("data/天空/左.bmp");
    bmptexture[1]= TextureLoader.load("data/天空/右.bmp");
    bmptexture[2]= TextureLoader.load("data/天空/上.bmp");
    bmptexture[3]= TextureLoader.load("data/天空/前.bmp");
    bmptexture[4]= TextureLoader.load("data/天空/后.bmp");
               
  //-----------------------
  }
  
//绘图函数
  public void display(GLAutoDrawable drawable)
  {
	GL2 gl = drawable.getGL().getGL2();
	gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT);    //清除颜色缓冲
	gl.glLoadIdentity();    //重置矩阵
	
//	gl.glTranslatef(0.0f, 0.0f, -6.0f);    //向内(Z轴负方向)移动6
//   gl.glTranslatef(0.0f, 0.0f, -6.0f);    //向内(Z轴负方向)移动6
    
    //----------------------新添加部分-------------------
    gl.glEnable(GL.GL_BLEND); //允许混合
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    gl.glAlphaFunc(GL.GL_GREATER, 0);
    gl.glEnable(GL.GL_ALPHA);
    //--------------------------------------------
    
    
 /*
	gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
    

    gl.glFinish();
	
    xrot += 0.3f;
    yrot += 0.2f;
    zrot += 0.4f;
    
 */

    //摄像机
    camera();
    //天空盒   
    CreateSkyBox(3,6,3,6,gl);
    //地面
    DrawGround(gl);
      
    gl.glPushMatrix();  
    //gl.glTranslated(100.0, 2.0, forwardZ);
    gl.glTranslated(60.0, 1.8, forwardZ);
    model.draw(gl);
	gl.glPopMatrix();
	
	//正方形窗口
	Drawcube(drawable);
    
	/*
    FPS.setColor(0.2f, 0.2f, 0.2f, te); 
    if(te > 1){
    	 te-=0.001;
    }
   */
	//显示字
    Crosshair.draw("﹛﹜",365,250);
    FPS.draw();
    
    gl.glFlush();
    
    //
    if(ifshoot){
    	shoot(gl);
    }
    
    
  }
  
  //碰撞检测
  public boolean iftf(){
	  text[0] = Math.abs(Math.abs(g_look[0])-60);
	  text[2] = Math.abs(Math.abs(g_look[2])-45);
	  text[1] = Math.abs(Math.abs(g_look[1])-1.8f);
	  if(text[0] <= 10+maxSize/2 && text[2] <= maxSize/2+7 && text[1] <= 5 ){
		  return true;
	  }else{
		  return false;
	  }
	  
  }
  
  public void shoot(GL2 gl){

	  if(shoot[0] > 400 || shoot[0] < -400 || shoot[2] > 400 || shoot[2] < -400 || shoot[1] > 400 || shoot[1] < -400 ){
		  shoot[0] = g_eye[0];
			shoot[2] = g_eye[2];
			shoot[1] = 1.8f;
			System.out.print("完成");
			ifshoot = false;
	  }else{
		  shoot[0] += Math.cos(rad_xz)*speed;
		  shoot[2] += Math.sin(rad_xz)*speed;
		  shoot[1] += Math.tan(3.13149f * g_elev/180.0f)*speed;
		  
		  text[0] = Math.abs(Math.abs(shoot[0])-60);
		  text[2] = Math.abs(Math.abs(shoot[2])-45);
		  text[1] = Math.abs(Math.abs(shoot[1])-1.8f);
		  
/*		  gl.glBegin(GL2.GL_POINTS);  //mode为GL_POINT
	        gl.glColor3f(1.0f,1.0f,1.0f); //设置点颜色
	        gl.glVertex3f(shoot[0],shoot[1],shoot[2]);
	    gl.glEnd();
	    gl.glFlush();*/
		  
		  System.out.print(shoot[0]+","+shoot[1]+","+shoot[2]+"\n");
		  if(text[0] <= maxSize/2 && text[2] <= maxSize/2 && text[1] <= maxSize/2){
			 System.out.print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			 
			 shoot[0] = g_eye[0];
				shoot[2] = g_eye[2];
				shoot[1] = 1.8f;
			 ifshoot = false;
		  }else{
			  ifshoot = true;
		  }
	  }
	  
	  
  }

  //这部分代码暂时不用改
  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
  {
	    GL2 gl = drawable.getGL().getGL2();
	    
	    gl.glViewport(0, 0, w, h);   //视见区域
	    gl.glMatrixMode(GL2.GL_PROJECTION);//哪一个矩阵堆栈
	    //gl.glLoadIdentity(); //重置矩阵
	    glu.gluPerspective(45.0, (float) w / (float) h, 1.0, 1000.0);//创建透视矩阵
	    gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();
  }


  //摄像机的搭建
  public void camera()
  {

	  if (g_elev<-20)	g_elev  =-20;
	  if (g_elev> 40)	g_elev  = 40;

	  if (g_eye[0]<-(MAP*2-20))	g_eye[0]= -(MAP*2-20);
	  if (g_eye[0]> (MAP*2-20))	g_eye[0]=  (MAP*2-20);
	  if (g_eye[2]<-(MAP*2-20))	g_eye[2]= -(MAP*2-20);
	  if (g_eye[2]> (MAP*2-20))	g_eye[2]=  (MAP*2-20);
	  
	  
	  //摄像机高度
	  g_eye[1] =1.8f;
	  //响应鼠标移动
	  if (isMove == 1) {
		  if (isPicture == 1) {
			  //g_Angle += (firstMoveX - lastMoveX) * speed*2;
			  
			  if((firstMoveX - lastMoveX)>0){
				  g_Angle -= speed*2;
				  yrot += 2f;
			  }else{
				  g_Angle += speed*2;
				  yrot -= 2f;
			  }
			
			  			 
			  //g_elev += (lastMoveY - firstMoveY) * 0.4f;
			  firstMoveX = 0;
			  lastMoveX = 0;
			  firstMoveY = 0;
			  lastMoveY = 0;
			  isMove = 0;
		  }
	  }
	  //g_look[0] = (float) ((float)g_eye[0] + 100*Math.cos(rad_xz));
	  //g_look[2] = (float) ((float)g_eye[2] + 100*Math.sin(rad_xz));
	  rad_xz = (float) 3.13149* g_Angle/180.0f;
	  g_look[0] = (float) ((float)g_eye[0] + Math.cos(rad_xz));
	  g_look[2] = (float) ((float)g_eye[2] + Math.sin(rad_xz));

	  g_look[1] = g_eye[1]+g_elev/100;
	    
	  //glu.gluLookAt(g_eye[0],g_eye[1],g_eye[2],g_look[0],g_look[1]+g_elev,g_look[2],0.0,1.0,0.0);
	  glu.gluLookAt(g_eye[0],g_eye[1],g_eye[2],g_look[0],g_look[1],g_look[2],0.0,1.0,0.0);
	  
	  //
	  //System.out.print(g_look[0]+" , "+g_look[1]+" , "+g_look[2]+"///");

  }
  
  //画出地面
  public void DrawGround(GL2 gl)
  {

	 // gl.glPushAttrib(GL.GL_CURRENT_BIT);
	 // gl.glEnable(GL.GL_BLEND);
	 // gl.glPushMatrix();
	 // gl.glColor3f(0.5f, 0.7f, 1.0f);
	 // gl.glTranslatef(0,0.0f,0);
	  int size0=(int)(MAP*2);
	  gl.glBegin(GL.GL_LINES);
		for (int x = -size0; x < size0;x+=4)
			{gl.glVertex3i(x, 0, -size0); 
			gl.glVertex3i(x, 0,  size0);}
		for (int z = -size0; z < size0;z+=4)
			{
			gl.glVertex3i(-size0, 0, z); 
			gl.glVertex3i( size0, 0, z);
			}
	  gl.glEnd();
	 // gl.glPopMatrix();
	//  gl.glDisable(GL.GL_BLEND);
	//  gl.glPopAttrib();
  }
  
  //画出贴图的正方形
  public void Drawcube(GLAutoDrawable drawable)
  {
	  GL2 gl = drawable.getGL().getGL2();
//	  gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
//	    gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
//	    gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
	    //gl.glBindTexture(GL.GL_TEXTURE_2D, );//指定和绑定纹理(这里是指定纹理)
	    //gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE);	  
	 //锁定图片，图片跟随摄像机   
	  gl.glTranslated(g_look[0],g_look[1],g_look[2]);
	  
	  //绕Y，Z轴旋转
	  gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
	  gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

	  gl.glTranslated(-g_look[0],-g_look[1],-g_look[2]);


	    pngtexture1.bind();
	    gl.glTexEnvf(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
	    gl.glBegin(GL2.GL_QUADS);

/*    
	    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(g_look[0]+8, g_look[1]-2, g_look[2]-4);
	    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(g_look[0]+8, g_look[1]-2, g_look[2]+4);
	    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(g_look[0]+8, g_look[1]+4, g_look[2]+4);
	    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(g_look[0]+8, g_look[1]+4, g_look[2]-4);
*/	    
	    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(g_look[0]+10, (float) (g_look[1]-5), g_look[2]-7);
	    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(g_look[0]+10, (float) (g_look[1]+5), g_look[2]-7);
	    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(g_look[0]+10, (float) (g_look[1]+5), g_look[2]+7);
	    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(g_look[0]+10, (float) (g_look[1]-5), g_look[2]+7);
		    
/*	    // 左面
	    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(5.0f, -5f, -7.0f);
	    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(5.0f, -5f, 7.0f);
	    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(5.0f, 9f, 7.0f);
	    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(5.0f, 9f, -7.0f);
/*	    // 右面
	    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(1.0f, 1.0f, -1.0f);
	    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(1.0f, 3.0f, -1.0f);
	    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(1.0f, 3.0f, 1.0f);
	    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(1.0f, 1.0f, 1.0f);
	    */    	
	    gl.glEnd();

	   // xrot += 0.3f;
	    //yrot += 0.02f;
	   // zrot += 0.4f;
	    
	 
  }
  
  //天空盒的加载
  public void CreateSkyBox(int a,int wi,int he,int le,GL2 gl){
	  
	  
	  float width =MAP*wi;
		float height=MAP*he;
		float length=MAP*le;
		float x = MAP  -width /2;
		float y = MAP/a-height/2+43;
		float z = -MAP -length/2;
	///////////////////////////////////////////////////////////////////////////////
		bmptexture[0].bind();
		gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2f(1.0f,0.0f); gl.glVertex3f(x+width,y,		 z);
			gl.glTexCoord2f(1.0f,1.0f); gl.glVertex3f(x+width,y+height,z); 
			gl.glTexCoord2f(0.0f,1.0f); gl.glVertex3f(x,		y+height,z);
			gl.glTexCoord2f(0.0f,0.0f); gl.glVertex3f(x,		y,		 z);
		gl.glEnd();
		
		
		bmptexture[1].bind();
		gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2f(1.0f,0.0f); gl.glVertex3f(x,		y,		 z+length);
			gl.glTexCoord2f(1.0f,1.0f); gl.glVertex3f(x,		y+height,z+length);
			gl.glTexCoord2f(0.0f,1.0f); gl.glVertex3f(x+width,y+height,z+length); 
			gl.glTexCoord2f(0.0f,0.0f); gl.glVertex3f(x+width,y,		 z+length);
		gl.glEnd();

		bmptexture[2].bind();
		gl.glBegin(GL2.GL_QUADS);	
			gl.glTexCoord2f(0.0f,1.0f); gl.glVertex3f(x+width,y+height,z);
			gl.glTexCoord2f(0.0f,0.0f); gl.glVertex3f(x+width,y+height,z+length); 
			gl.glTexCoord2f(1.0f,0.0f); gl.glVertex3f(x,		y+height,z+length);
			gl.glTexCoord2f(1.0f,1.0f); gl.glVertex3f(x,		y+height,z);
		gl.glEnd();
		
		
		bmptexture[3].bind();
		gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2f(1.0f,1.0f); gl.glVertex3f(x,		y+height,z);	
			gl.glTexCoord2f(0.0f,1.0f); gl.glVertex3f(x,		y+height,z+length); 
			gl.glTexCoord2f(0.0f,0.0f); gl.glVertex3f(x,		y,		 z+length);
			gl.glTexCoord2f(1.0f,0.0f); gl.glVertex3f(x,		y,		 z);		
		gl.glEnd();
		
		bmptexture[4].bind();
		gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2f(0.0f,0.0f); gl.glVertex3f(x+width,y,		 z);
			gl.glTexCoord2f(1.0f,0.0f); gl.glVertex3f(x+width,y,		 z+length);
			gl.glTexCoord2f(1.0f,1.0f); gl.glVertex3f(x+width,y+height,z+length); 
			gl.glTexCoord2f(0.0f,1.0f); gl.glVertex3f(x+width,y+height,z);
		gl.glEnd();
	    
  }
  
  //负责响应
  public void keyPressed(KeyEvent e)
  {

	  //弧度转换	
	  rad_xz = 3.13149f * g_Angle/180.0f;

	//处理键盘事件  
      System.out.println(e.getKeyCode());  
      //前进
      if (e.getKeyCode() == 87) { 
   	   
    	  if(iftf()){
  	    	//forwardZ += 0.001f;
  	    	//forwardZ += 90f;
  	    }else{
  	    	
  	    	g_eye[2]+=Math.sin(rad_xz)*speed;
  	  	    g_eye[0]+=Math.cos(rad_xz)*speed;
  	    }
    	  
    	  

   	   }
      //后退
      if (e.getKeyCode() == 83) { 
   	   
    	  g_eye[2]-=Math.sin(rad_xz)*speed;
    	  g_eye[0]-=Math.cos(rad_xz)*speed;
    	  
    	  if(iftf()){
    	    	//forwardZ += 0.001f;
    	    	forwardZ += 0.009f;
    	    }


      }  
    //左旋转
      if (e.getKeyCode() == 65) { 

    	  g_Angle-=speed*2;

    	  yrot += 2f;
    	  

      }  
      //右旋转
      if (e.getKeyCode() == 68) {  

    	  g_Angle+=speed*2;
    	  
    	  yrot -= 2f;

      } 
       
      //抬头
      if (e.getKeyCode() == 69) { 

    	  g_elev +=0.2f;
    	  
    	  if(g_elev <= 40){
    		  zrot += 0.12f;
    	  }else{
    		  zrot = 23.5f;
    	  }
    	    
    	  System.out.print(g_elev+"  "); 
    	  System.out.print(zrot);

   	   }
      //低头
      if (e.getKeyCode() == 81) { 
    	  
    	  g_elev -=0.2f;
    	  //g_elev -=speed;
    	  if(g_elev >= -20){
    		  zrot -= 0.11f;
    	  }else{
    		  zrot = -11f;
    	  }
    	  
    	  System.out.print(zrot);

   	   }
      
      if (e.getKeyCode() == 32) { 
    	  ifshoot = true;

   	   }
      

  }

  //鼠标移动
  public void mouseMoved(MouseEvent e) {
  	if (isMove == 0) {
  		firstMoveX = e.getX();
  		firstMoveY = e.getY();
  		isPicture = 0;
  		isMove = 1;
  	}
  	else {
  		lastMoveX = e.getX();
  		lastMoveY = e.getY();
  		isPicture = 1;
  	}
  	
  }
  
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
	      boolean deviceChanged)
	  {
		  
	  }

  public void keyTyped(KeyEvent key)
	  {
		
	  }
  public void keyReleased(KeyEvent key)
  {
  }

  public void mouseClicked(MouseEvent e)
  {
  }

  public void mousePressed(MouseEvent e)
  {
  }

  public void mouseReleased(MouseEvent e)
  {
  }

  public void mouseEntered(MouseEvent e)
  {
  }

  public void mouseExited(MouseEvent e)
  {
  }

public void dispose(GLAutoDrawable arg0) {
	// TODO Auto-generated method stub
	
}

public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

} 