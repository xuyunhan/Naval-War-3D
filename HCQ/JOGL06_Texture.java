package HCQ;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;



import OBJLoader.OBJModel;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
//import com.sun.opengl.util.GLUT;
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

public class JOGL06_Texture extends JFrame implements GLEventListener, KeyListener, MouseListener
{
  private GL2 gl;
  private GLU glu;
  private GLCapabilities caps;
  private GLCanvas canvas;
  private MouseEvent mouse;
  private Animator animator;
  private Texture  pngtexture1,pngtexture2;
  private Texture bmptexture[] = new Texture[5];
  private float xrot;//��X��
  private float yrot;//��Y��
  private float zrot;//��Z��
  private DecimalFormat df = new DecimalFormat("0.##");
  private String modelName;
//  private OBJModel model;
  private float maxSize;
  float focus[] = new float[3];//���������λ��
  float camera[] = new float[3];//�����λ��
  int viewangle;
  private OBJModel model;
  float	 g_eye[]= new float[3];
  float	 g_look[]= new float[3];
  float	 rad_xz;	
  float	 g_Angle;
  float	 g_elev;	
  
  float MAP = 40;//	MAP_W*MAP_SCALE/2


  public JOGL06_Texture()
  {
	  
    super("JOGL06_Texture");
    modelName = "formula";
    maxSize = 5f;
    caps = new GLCapabilities(null);
    canvas = new GLCanvas(caps);
    canvas.addGLEventListener(this);
    canvas.addKeyListener(this);
    canvas.addMouseListener(this);
    getContentPane().add(canvas);
   // model = new OBJModel("formula", 30, gl, true);
  }

  //����ĵ��õ������
  public void run()
  {
    setSize(800, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    canvas.requestFocusInWindow();
    canvas.swapBuffers();
    animator.start();
  }

  public static void main(String[] args)
  {
    new JOGL06_Texture().run();
    
  }

  public void init(GLAutoDrawable drawable)
  {
	    gl = drawable.getGL().getGL2();
	    glu = new GLU();  
	    gl.glShadeModel(GL2.GL_SMOOTH);
	    gl.glClearColor(0.0f, 0.0f, 0.0f, 0f);
	    gl.glClearDepth(1.0f);							// ������Ȼ���
		gl.glEnable(GL.GL_DEPTH_TEST);					// ������Ȳ���
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);			// ����ϵͳ��͸�ӽ�������
	    animator=new Animator(canvas);//ʵ������canvas��ˢ��
	    model = new OBJModel(modelName, maxSize, gl, true);
	    
	    focus[0]=7.0f;
		focus[1]=0.0f;
		focus[2]=3.0f;

		camera[2]=3.0f;//���Z�����2.0�ĸ߶�
		camera[0]=0.0f;
		camera[1]=0.0f;
		viewangle=0;//��ͷ�ƶ��ĽǶ�,����Ϊ��
	    
 //--------------����Ӵ���-------
    gl.glEnable(GL.GL_TEXTURE_2D);//��������ӳ��
    pngtexture1 = TextureLoader.load("data/jogl.png");
    pngtexture2 = TextureLoader.load("data/Crate.bmp");
    
    bmptexture[0]= TextureLoader.load("data/���/��.bmp");
    bmptexture[1]= TextureLoader.load("data/���/��.bmp");
    bmptexture[2]= TextureLoader.load("data/���/��.bmp");
    bmptexture[3]= TextureLoader.load("data/���/ǰ.bmp");
    bmptexture[4]= TextureLoader.load("data/���/��.bmp");
               
  //-----------------------
  }
  
  //��ͼ����
  public void display(GLAutoDrawable drawable)
  {
	GL2 gl = drawable.getGL().getGL2();
	gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT);    //�����ɫ����
	gl.glLoadIdentity();    //���þ���
	gl.glTranslatef(0.0f, 0.0f, -6.0f);    //����(Z�Ḻ����)�ƶ�6
    gl.glTranslatef(0.0f, 0.0f, -6.0f);    //����(Z�Ḻ����)�ƶ�6
    
    //----------------------����Ӳ���-------------------
    gl.glEnable(GL.GL_BLEND); //������
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    gl.glAlphaFunc(GL.GL_GREATER, 0);
    gl.glEnable(GL.GL_ALPHA);
    //--------------------------------------------
    
    
 /*   glu.gluLookAt(camera[0],camera[1],camera[2],focus[0],focus[1],focus[2],0.0,0.0,1.0);
  //���������ƽ����XYƽ��

    gl.glTexEnvf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
    gl.glPopMatrix();
    
    gl.glBegin(GL.GL_LINES);
	for(float x=-500;x<500;x+=4)
	{
		gl.glVertex3f(x,-500f,0f);
		gl.glVertex3f(x,500f,0f);
	}
	for(float y=-500;y<500;y+=4)
	{
		gl.glVertex3f(-500,y,0);
		gl.glVertex3f(500,y,0);
	}
	gl.glEnd();
	gl.glPopMatrix();
    
 
	gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
    

    gl.glFinish();
	
    xrot += 0.3f;
    yrot += 0.2f;
    zrot += 0.4f;
    
 */
//    model.draw(gl);
    
    
    
 //   gl.glEnable(GL.GL_TEXTURE_2D);
    camera();
    //   gl.glDisable(GL.GL_TEXTURE_2D);
    gl.glPushMatrix();
    DrawGround(gl);
    Drawcube(drawable);
    CreateSkyBox(3,6,3,6,gl);
    gl.glTranslated(0.0,1.0,0.0);
    model.draw(gl);
    gl.glPopMatrix();
    gl.glFlush();
   
    

  }

  //�ⲿ�ִ�����ʱ���ø�
  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h)
  {
	    GL2 gl = drawable.getGL().getGL2();
	    
	    gl.glViewport(0, 0, w, h);   //�Ӽ�����
	    gl.glMatrixMode(GL2.GL_PROJECTION);//��һ�������ջ
	    //gl.glLoadIdentity(); //���þ���
	    glu.gluPerspective(45.0, (float) w / (float) h, 1.0, 1000.0);//����͸�Ӿ���
	    gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();
  }

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
      boolean deviceChanged)
  {
	  
  }

  public void keyTyped(KeyEvent key)
  {
	
  }

  //������Ĵ
  public void camera()
  {

	  if (g_elev<-100)	g_elev  =-100;
	  if (g_elev> 100)	g_elev  = 100;


	  if (g_eye[0]<-(MAP*2-20))	g_eye[0]= -(MAP*2-20);
	  if (g_eye[0]> (MAP*2-20))	g_eye[0]=  (MAP*2-20);
	  if (g_eye[2]<-(MAP*2-20))	g_eye[2]= -(MAP*2-20);
	  if (g_eye[2]> (MAP*2-20))	g_eye[2]=  (MAP*2-20);
	  
	  g_eye[1] =1.8f;
	  g_look[0] = (float) ((float)g_eye[0] + 100*Math.cos(rad_xz));
	  g_look[2] = (float) ((float)g_eye[2] + 100*Math.sin(rad_xz));
	  g_look[1] = g_eye[1];

	  glu.gluLookAt(g_eye[0],g_eye[1],g_eye[2],g_look[0],g_look[1]+g_elev,g_look[2],0.0,1.0,0.0);

  }
  
  //��������
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
  
  //������ͼ��������
  public void Drawcube(GLAutoDrawable drawable)
  {

	  GL2 gl = drawable.getGL().getGL2();
//	  gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
//	    gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
//	    gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

	    //gl.glBindTexture(GL.GL_TEXTURE_2D, );//ָ���Ͱ�����(������ָ������)

	    //gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE);
	    gl.glColor4d(1,1,1,1);
	    pngtexture1.bind();
	    gl.glTexEnvf(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
	    gl.glBegin(GL2.GL_QUADS);


	    // ����
	    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-1.0f, 1f, -1.0f);
	    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-1.0f, 1f, 1.0f);
	    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-1.0f, 3f, 1.0f);
	    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-1.0f, 3f, -1.0f);
	    // ����
	    gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(1.0f, 1.0f, -1.0f);
	    gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(1.0f, 3.0f, -1.0f);
	    gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(1.0f, 3.0f, 1.0f);
	    gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(1.0f, 1.0f, 1.0f);
	    gl.glEnd();
	    
	   // xrot += 0.3f;
	   // yrot += 0.2f;
	   // zrot += 0.4f;
	    

	 
  }
  
  //��պеļ���
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
  
  //������Ӧ
  public void keyPressed(KeyEvent e)
  {

	  float speed=1f;	
	  rad_xz = (float) 3.13149* g_Angle/180.0f;

	//��������¼�  
      System.out.println(e.getKeyCode());  
      if (e.getKeyCode() == 38) { 
   	   
    	  g_eye[2]+=Math.sin(rad_xz)*speed;
    	  g_eye[0]+=Math.cos(rad_xz)*speed;


   	   }
      if (e.getKeyCode() == 40) { 
   	   
    	  g_eye[2]-=Math.sin(rad_xz)*speed;
    	  g_eye[0]-=Math.cos(rad_xz)*speed;


      }  

      if (e.getKeyCode() == 37) { 

    	  g_Angle-=speed*2;


      }  

      if (e.getKeyCode() == 39) {  

    	  g_Angle+=speed*2;


      } 
       
      if (e.getKeyCode() == 33) { 

    	  g_elev +=0.2f;

   	   }
      
      if (e.getKeyCode() == 34) { 

    	  g_elev -=0.2f;

   	   }
      focus[0] =(float) ((float)camera[0] + 100* (Math.cos(rad_xz)));
  	focus[1] =(float) ((float)camera[1] + 100* (Math.sin(rad_xz)));

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
} 