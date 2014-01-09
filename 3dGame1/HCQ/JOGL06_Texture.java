package HCQ;
import CS.*;
import OceanSimulation.*;

import java.awt.event.*;
import java.io.IOException;

import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

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
import java.util.Vector;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.media.opengl.*;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.GLCapabilities;

public class JOGL06_Texture implements GLEventListener, KeyListener,
		MouseListener, MouseMotionListener, Runnable {

	//
	private client cli;
	private server ser;
	
	private GL2 gl;
	private GLU glu;
	// private GLCapabilities caps;
	// private GLCanvas canvas;
	private MouseEvent mouse;
	private Animator animator;
	// 贴图
	private Texture pngtexture1[] = new Texture[5];
	int pngtexture1num = 0;
	private Texture bmptexture[] = new Texture[5];
	private Texture warshiptexture[] = new Texture[6];
	private Texture shoottexture[] = new Texture[2];
	private Texture bloodtexture;
	private Texture ammotexture;

	private float xrot;// 绕X轴
	private float yrot;// 绕Y轴
	private float zrot = 0f;// 绕Z轴

	// 鼠标始末点
	private int firstMoveX;
	private int lastMoveX;
	private int firstMoveY;
	private int lastMoveY;
	private int isMove;
	private int isPicture;
	private int goSpeed;

	// 地面半径
	float MAP = 40;// MAP_W*MAP_SCALE/2

	//显示信息
	private Counter clean;
	private Counter information;
	private Counter infor;

	//
	GLUquadric g_text;

	// 计算战斗
	public GameFight fightResult;

	// 我方战舰
	public OwnShip myShip;

	// 敌军舰
	public Enemy enemyShip[];
	
	//击中敌方战舰
		 int Hit[] = new int[4];

	GLUT glut = new GLUT();  

	// 敌飞机
	public airplane[] enemyairplane = new airplane[3];
	
	//海面
	private GLFFTOceanSimulation _glFFTOceanSimulation = new GLFFTOceanSimulation();
	
	public int tSpeed;
	public int tAttack;
	public int tLife;

	// 子弹
	float shoot[] = new float[3];
	boolean ifshoot;

	float te = 5;
	int count;

	float text[][] = new float[7][3];

	int goodsnum = 4;
	Goods goods[] = new Goods[goodsnum];
	String shootFile;
	String hitFile;
	String glassFile;
	GunSound shootPlay = null;
	GunSound hitPlay = null;
	GunSound glassPlay = null;
	Vector<String> shootList = new Vector<String>();
	Vector<String> hitList = new Vector<String>();
	Vector<String> glassList = new Vector<String>();
	String[] list = new String[3];

	float depositAnglespeed[] = { 0.3f, 0.14f, 0.2f, 0.09f };
	boolean shiporair = true;
	boolean playF;
	boolean playS;
	boolean playT;
	boolean playR;
	boolean isCrash;
	boolean shootAction[];
	int shootActionCount[];
	int YN = 0;
	public int enemyNumber;//设定敌人的数量
	public int goodsNumber;//设定补给物品的数量
	public int netChoice;

	public JOGL06_Texture(int enemyNumber,int goodsNumber,int netChoice) {

		this.enemyNumber = enemyNumber;
		this.goodsNumber = goodsNumber;
		this.netChoice = netChoice;
		goSpeed = 1;
		enemyShip = new Enemy[enemyNumber];
	    shootAction = new boolean[enemyNumber];
		shootActionCount = new int[enemyNumber];
		
	}

	// 程序的调用的主入口
	/*
	 * public void run() { setSize(800, 600); setLocationRelativeTo(null);
	 * setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setVisible(true);
	 * canvas.requestFocusInWindow(); canvas.swapBuffers(); animator.start(); }
	 */

	public void init(GLAutoDrawable drawable) {
		
		if (netChoice == 2) {
			cli = new client();
			System.out.println("此时是客户机");
		}
		if (netChoice == 1) {
		    ser = new server();
		    System.out.println("此时是主机");
		}
		
		// 下面的代码用于音乐播放
		shootFile = System.getProperty("user.dir") + "\\music\\射击音乐.mp3";
		hitFile = System.getProperty("user.dir") + "\\music\\舰艇碰撞音乐.mp3";
		glassFile = System.getProperty("user.dir") + "\\music\\射击玻璃音乐.mp3";
		list[0] = shootFile;
		list[1] = hitFile;
		list[2] = glassFile;
		shootList.add(list[0]);
		hitList.add(list[1]);
		glassList.add(list[2]);

		gl = drawable.getGL().getGL2();
		glu = new GLU();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0f);
		gl.glClearDepth(1.0f); // 设置深度缓存
		gl.glEnable(GL.GL_DEPTH_TEST); // 启用深度测试
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // 告诉系统对透视进行修正

		fightResult = new GameFight();

		myShip = new OwnShip();

		// 我方军舰摄像机定位
		myShip.seteye(-80f, 1.8f, 0.0f);
		myShip.g_Angle = 0;
		myShip.g_elev = 0;

		myShip.speed = 1.0f;

		myShip.goSpeed = 10;
		myShip.attack = 80;
		myShip.life = 70;

		// 军舰加速
		goSpeed = 1;

		// 子弹初始位置
		shoot[0] = myShip.g_eye[0];
		shoot[2] = myShip.g_eye[2];
		shoot[1] = myShip.g_eye[1];
		ifshoot = false;

		// 初始化鼠标始末点
		firstMoveX = 0;
		lastMoveX = 0;
		firstMoveY = 0;
		lastMoveY = 0;
		isMove = 0;
		isPicture = 0;

		// 敌方军舰初始化
		for (int i = 0; i < enemyNumber; i++) {

			enemyShip[i] = new Enemy();

			enemyShip[i].radii = 10f + i * 10f;

			enemyShip[i].depositAngle = 0f;
			enemyShip[i].depositRadian = enemyShip[i].depositAngle / 180f * 3.13149f;

			enemyShip[i].life = 40;
			enemyShip[i].attack = 100;

			enemyShip[i].descendNumber = 0f;

			enemyShip[i].descendAngle = 0f;

			enemyShip[i].setModel("mod" + i, 20, gl);
			
			Hit[i] = 0;

		}
		// 敌方飞机初始化
		for (int i = 0; i < 3; i++) {
			enemyairplane[i] = new airplane();

			enemyairplane[i].r_x = 0;
			enemyairplane[i].r_y = 25 + 2 * i;

			enemyairplane[i].r_z = 0;

			enemyairplane[i].r_angle = 0f;

			enemyairplane[i].life = 30;

			enemyairplane[i].radii = 50.0f;

			enemyairplane[i].size = 25;

		}

		tSpeed = myShip.goSpeed;
		tAttack = myShip.attack;
		tLife = myShip.life;
		playF = true;
		playS = true;
		playT = true;
		playR = true;
		isCrash = false;

		//信息显示初始化
				clean = new Counter(drawable, 0);	
				information = new Counter(drawable, 18);
				infor = new Counter(drawable, 38);

		g_text = glu.gluNewQuadric();

		// 初始化物品
		for (int i = 0; i < goodsnum; i++) {
			goods[i] = new Goods(i);

		}

		// 设置空光标
		// java.net.URL classUrl = this.getClass().getResource("");
		// Image imageCursor = Toolkit.getDefaultToolkit().getImage(classUrl);
		// setCursor(Toolkit.getDefaultToolkit().createCustomCursor(imageCursor,
		// new Point(0, 0), "cursor"));

		// --------------新添加代码-------
		gl.glEnable(GL.GL_TEXTURE_2D);// 启动纹理映射

		for (int i = 0; i < 5; i++) {
			pngtexture1[i] = TextureLoader.load("data/窗口/窗口" + i + ".png");
		}

		// pngtexture2 = TextureLoader.load("data/Crate.bmp");
		shoottexture[0] = TextureLoader.load("data/爆炸右.png");
		shoottexture[1] = TextureLoader.load("data/爆炸左.png");
		bloodtexture = TextureLoader.load("data/急救包.png");
		ammotexture = TextureLoader.load("data/弹药包.png");

		bmptexture[0] = TextureLoader.load("data/天空/左.bmp");
		bmptexture[1] = TextureLoader.load("data/天空/右.bmp");
		bmptexture[2] = TextureLoader.load("data/天空/上.bmp");
		bmptexture[3] = TextureLoader.load("data/天空/前.bmp");
		bmptexture[4] = TextureLoader.load("data/天空/后.bmp");

		for (int i = 0; i < 6; i++) {
			warshiptexture[i] = TextureLoader.load("data/军舰" + i + ".png");
		}
		
		_glFFTOceanSimulation.Init(gl);
		new Thread(this).start();
		// -----------------------
	}

	// 绘图函数
	public void display(GLAutoDrawable drawable) {

		tSpeed = (int) (myShip.speed * 10);
		tAttack = myShip.attack;
		tLife = myShip.life;

		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // 清除颜色缓冲
		gl.glLoadIdentity(); // 重置矩阵

		// ----------------------新添加部分-------------------
		gl.glEnable(GL.GL_BLEND); // 允许混合
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glAlphaFunc(GL.GL_GREATER, 0);
		gl.glEnable(GL.GL_ALPHA);
		// --------------------------------------------
		

		// 摄像机
		camera();
		// 天空盒
		CreateSkyBox(3, 6, 3, 6, gl);
		// 地面
		//DrawGround(gl);
		 //gl.glDisable(GL.GL_BLEND);

		// 画飞机
		for (int i = 0; i < 3; i++) {
			airplane(enemyairplane[i].r_x, enemyairplane[i].r_y,
					enemyairplane[i].r_z, enemyairplane[i].r_angle,
					enemyairplane[i].radii, gl);
		}

		// 画物品
		drawgoods(gl);
		
		gl.glPushMatrix();
		
		//gl.glTranslated(myShip.g_look[0], myShip.g_look[1]-2, myShip.g_look[2]);
		//gl.glTranslated(0, -2, -5);
		// 绕Y，Z轴旋转
		//gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
		//gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
		//gl.glTranslated(-myShip.g_look[0], -myShip.g_look[1], -myShip.g_look[2]);

		//_glFFTOceanSimulation.Display(gl);
		gl.glPopMatrix();
		
		//画海洋
		gl.glPushMatrix();
//		gl.glRotatef(-myShip.g_Angle,0,1,0);
    	_glFFTOceanSimulation.Display(gl);
		gl.glPopMatrix();
		// gl.glEnable(GL.GL_BLEND);

		// 下面的代码导入模型，并画舰艇
		loadmod(gl);

		
		if (netChoice == 2) {
			cli.clientout(myShip.life);
			information.XJG = cli.clientin();
		}
		if (netChoice == 1){
			ser.serveout(myShip.life);
			information.XJG = ser.severin();
		}
		
		
		
		// 显示字
		gl.glPushMatrix();
		// Crosshair.draw("",370,285);
		// information.Intextup = "sss";
		// information.Intextdown = "sssss";
		information.draw();
		infor.drawclean();
		clean.drawclean();

		gl.glPopMatrix();

		// 正方形窗口
		gl.glEnable(GL.GL_BLEND);
		Drawcube(drawable);
		
		// 画子弹
		if (YN > 0) {

			drawSphere(myShip.g_look[0] + 9f, myShip.g_look[1],
					myShip.g_look[2], gl);
			YN--;

		}
		gl.glDisable(GL.GL_BLEND);

		// 射击判断
		if (ifshoot) {
			shoot(gl, enemyNumber);
		}

		enemyshoot(gl, enemyNumber);
		iftf(enemyNumber, goodsnum);

		gl.glFlush();
		
		
		

	}

	// 画物品
	public void drawgoods(GL2 gl) {

		for (int i = 0; i < goodsnum; i++) {

			gl.glPushMatrix();

			gl.glTranslatef(goods[i].getX(), goods[i].getY(), goods[i].getZ());// 定位
			gl.glRotatef(0f, 0.0f, 0.0f, 3.0f);// 倾斜
			if (goods[i].getifblood()) {
				bloodtexture.bind();
			} else {
				ammotexture.bind();

			}
			if (goods[i].ifeat) {
				;
			} else {
				box(1.0f, 1f, 1f, gl);
				gl.glColor3f(0f,0f,1f);
				  gl.glRasterPos3f(-0.5f,1.5f,0f);//显示文字的地方
				  glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18,//字的大小
				   "eat me!");//显示的内容	
				  gl.glColor3f(1f,1f,1f);
			}

			gl.glPopMatrix();
		}

	}

	// 下面的代码导入模型，并画舰艇
	public void loadmod(GL2 gl) {

		gl.glPushMatrix();

		for (int i = 0; i < enemyNumber; i++) {
			warshiptexture[i].bind();
			if (enemyShip[i].life <= 0) {
				enemyShip[i].descendNumber += 0.03f;
				if (enemyShip[i].descendAngle < 40.f) {
					enemyShip[i].descendAngle += 0.15;
				}
				gl.glPushMatrix();
				gl.glTranslatef(0f, 1.0f, 0f);
				gl.glRotatef(-enemyShip[i].depositRadian * 180f / 3.14159f,
						0.0f, 1.0f, 0.0f);
				gl.glTranslatef(enemyShip[i].radii, 0.0f, 0.0f);
				gl.glRotatef(-enemyShip[i].descendAngle, 1.0f, 0.0f, 0.0f);
				gl.glTranslatef(0.0f, -enemyShip[i].descendNumber, 0.0f);
				enemyShip[i].model.draw(gl);
				gl.glPopMatrix();

			}

			else if (enemyShip[i].life > 0 /*&& shootAction[i] == false*/) {
				gl.glPushMatrix();
				gl.glTranslatef(0f, 1.0f, 0f);
				gl.glRotatef(-enemyShip[i].depositAngle, 0.0f, 1.0f, 0.0f);
				gl.glTranslatef(enemyShip[i].radii, 0.0f, 0.0f);
				//新加
				if(Hit[i]>0){
					  gl.glColor3f(1f,0f,0f);
					  gl.glRasterPos3f(0f,1f, 0);//显示文字的地方
					  glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24,//字的大小
					   "-"+fightResult.harm());//显示的内容	
	
					  Hit[i]--;
				}
				enemyShip[i].model.draw(gl);
				gl.glColor3f(1f,1f,1f);
				gl.glPopMatrix();
				enemyShip[i].depositAngle += depositAnglespeed[i];

				if (enemyShip[i].depositAngle > 360.0f) {
					enemyShip[i].depositAngle = 0.0f;
				}
			}

		}

		gl.glPopMatrix();

	}

	// 碰撞检测
	public void iftf(int number, int goodsnum) {

		// 碰撞船只
		for (int i = 0; i < number; i++) {

			if (enemyShip[i].life > 0) {
				text[i][0] = (float) Math
						.abs(myShip.g_look[0]
								- enemyShip[i].radii
								* Math.cos(enemyShip[i].depositAngle / 180f * 3.13149f));
				text[i][1] = Math.abs(myShip.g_look[1] - 1.8f);
				text[i][2] = (float) Math
						.abs(myShip.g_look[2]
								- enemyShip[i].radii
								* Math.sin(enemyShip[i].depositAngle / 180f * 3.13149f));

				if (text[i][0] <= 10 + enemyShip[i].size / 2 /*
															 * && text[i][1] <=
															 * enemyShip
															 * [i].size/2+7
															 */
						&& text[i][2] <= enemyShip[i].size / 2) {
					System.out.println("");
					information.Intextdown = "信息:警告！撞船！";
					enemyShip[i].life = 0;

					myShip.life -= 20;
					isCrash = true;
					// return true;
				} else {
					// return false;
				}
			}

		}

		// 碰撞物体
		for (int i = 0; i < goodsnum; i++) {

			if (!goods[i].ifeat) {
				text[i][0] = (float) Math.abs(myShip.g_look[0]
						- goods[i].getX());
				text[i][1] = Math.abs(myShip.g_look[1] - goods[i].getY());
				text[i][2] = (float) Math.abs(myShip.g_look[2]
						- goods[i].getZ());

				if (text[i][0] <= 10 && text[i][1] <= 10 && text[i][2] <= 10) {

					goods[i].ifeat = true;

					if (goods[i].getifblood()) {
						myShip.life += goods[i].getblood();
						information.Intextdown = "信息:得到能量包！";
						infor.inforYN = 20;
						infor.Strinfor = "+血20";

					}else{
						myShip.attack+=goods[i].getpower();
						information.Intextdown = "信息:得到武器包！";
						infor.inforYN = 20;
						infor.Strinfor = "+攻击15";
					}

				} else {
					;
				}
			}

		}

	}

	// 我方子弹射击检测
	public void shoot(GL2 gl, int number) {

		if (shoot[0] > 100 || shoot[0] < -100 || shoot[2] > 100
				|| shoot[2] < -100 || shoot[1] > 100 || shoot[1] < -100) {

			// 子弹回归原点
			shoot[0] = myShip.g_eye[0];
			shoot[2] = myShip.g_eye[2];
			shoot[1] = myShip.g_eye[1];
			ifshoot = false;
			System.out.print("没打中！！！");
			information.Intextup = "舰载炮:没打中！！！";
		} else {
			// 子弹飞行
			shoot[0] += Math.cos(myShip.rad_xz)*2;
			shoot[2] += Math.sin(myShip.rad_xz)*2;
			shoot[1] += Math.tan(3.13149f * myShip.g_elev / 180.0f)*2;
			information.bulletX = shoot[0];
			information.bulletY = shoot[1];
			information.bulletZ = shoot[2];
			information.Intextup = "舰载炮:射击中...";
			// System.out.println("此时的shoot " + text[i][0] + " " + text[i][1] +
			// " " + text[i][2]);

			for (int i = 0; i < number; i++) {
				if (enemyShip[i].life > 0) {
					text[i][0] = (float) Math.abs(shoot[0] - enemyShip[i].radii
							* Math.cos(enemyShip[i].depositRadian));
					text[i][1] = Math.abs(shoot[1] - 1.8f);
					text[i][2] = (float) Math.abs(shoot[2] - enemyShip[i].radii
							* Math.sin(enemyShip[i].depositRadian));

					// System.out.println("此时的坐标 " + radii[i]*
					// Math.cos(depositAngle[i]) + " " + radii[i]*
					// Math.sin(depositAngle[i]));

					if (text[i][0] <= enemyShip[i].size / 3
							&& text[i][1] <= enemyShip[i].size / 3
							&& text[i][2] <= enemyShip[i].size / 3) {
						// System.out.println("打中"+i+"船");
						information.Intextup = "舰载炮:打中" + i + "船!";
						Hit[i] = 40;
						shoot[0] = myShip.g_eye[0];
						shoot[2] = myShip.g_eye[2];
						shoot[1] = myShip.g_eye[1];
						ifshoot = false;
						fightResult.setFirstAttack(myShip.attack);
						fightResult.setSecondAttack(enemyShip[i].attack);
						enemyShip[i].life -= fightResult.harm();
						shootAction[i] = true;
						shiporair = false;

						break;
					} else {
						shiporair = true;

						ifshoot = true;
					}

				}
			}

			if (shiporair) {
				// 打中飞机
				for (int i = 0; i < 3; i++) {
					if (enemyairplane[i].life > 0) {
						text[i][0] = (float) Math.abs(shoot[0]
								- enemyairplane[i].getr_x());
						text[i][1] = Math.abs(shoot[1] - enemyairplane[i].r_y);
						text[i][2] = (float) Math.abs(shoot[2]
								- enemyairplane[i].getr_z());

						if (text[i][0] <= enemyairplane[i].size / 2
								&& text[i][2] <= enemyairplane[i].size / 2
								&& text[i][1] <= enemyairplane[i].size / 2) {

							System.out.println("打中" + i + "飞机坐标："
									+ enemyairplane[i].getr_x());
							System.out.println("打中" + i + "飞机坐标："
									+ enemyairplane[i].r_y);
							System.out.println("打中" + i + "飞机坐标："
									+ enemyairplane[i].getr_z());
							information.Intextup = "舰载炮:打中" + i + "飞机!";
							shoot[0] = myShip.g_eye[0];
							shoot[2] = myShip.g_eye[2];
							shoot[1] = myShip.g_eye[1];
							ifshoot = false;

							// fightResult.setFirstAttack(myShip.attack);
							// fightResult.setSecondAttack(enemyShip[i].attack);
							// enemyShip[i].life -= fightResult.harm();
							// System.out.println("此时的血量是" + enemyShip[i].life);
							break;
						} else {
							ifshoot = true;
						}
					}
				}

			}

			shiporair = true;

		}

	}

	// 敌船子弹检测
	public void enemyshoot(GL2 gl, int number) {

		for (int i = 0; i < number; i++) {
			if (enemyShip[i].life > 0) {

				if (enemyShip[i].shipshoot[0] > 200
						|| enemyShip[i].shipshoot[0] < -200
						|| enemyShip[i].shipshoot[2] > 200
						|| enemyShip[i].shipshoot[2] < -200
						|| enemyShip[i].shipshoot[1] > 200
						|| enemyShip[i].shipshoot[1] < -200) {

					// 敌船子弹回归原点
					enemyShip[i].depositRadian = enemyShip[i].depositAngle / 180f * 3.13149f;

					enemyShip[i].shipshoot[0] = (float) (enemyShip[i].radii * Math
							.cos(enemyShip[i].depositRadian));
					enemyShip[i].shipshoot[2] = (float) (enemyShip[i].radii * Math
							.sin(enemyShip[i].depositRadian));
					enemyShip[i].shipshoot[1] = 1.8f;

					// System.out.print("没打中！");
					information.Intextdown = "信息:安全区域...";

				} else {
					// 子弹飞行
					enemyShip[i].shipshoot[0] -= Math
							.cos(enemyShip[i].depositRadian);
					enemyShip[i].shipshoot[2] -= Math
							.sin(enemyShip[i].depositRadian);
					// enemyShip[i].shipshoot[1] += 0.0f;

					text[i][0] = (float) Math.abs(enemyShip[i].shipshoot[0]
							- myShip.g_eye[0]);
					text[i][1] = Math.abs(enemyShip[i].shipshoot[1] - 1.8f);
					text[i][2] = (float) Math.abs(enemyShip[i].shipshoot[2]
							- myShip.g_eye[2]);

					if (text[i][0] <= 10 / 2 && text[i][1] <= 10 / 2
							&& text[i][2] <= 10 / 2) {

						// System.out.println("你被"+i+"船打中！！！");
						information.Intextdown = "信息:你被" + i + "船打中！！！";
						myShip.life -= 4;

						enemyShip[i].depositRadian = enemyShip[i].depositAngle / 180f * 3.13149f;

						enemyShip[i].shipshoot[0] = (float) (enemyShip[i].radii * Math
								.cos(enemyShip[i].depositRadian));
						enemyShip[i].shipshoot[2] = (float) (enemyShip[i].radii * Math
								.sin(enemyShip[i].depositRadian));
						enemyShip[i].shipshoot[1] = 1.8f;

					} else {

					}
				}
			}
		}
	}

	// 画子弹
	public void drawSphere(float xx, float yy, float zz, GL2 gl) {
		gl.glPushMatrix();

		gl.glTranslated(myShip.g_look[0], myShip.g_look[1], myShip.g_look[2]);
		// 绕Y，Z轴旋转
		gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
		gl.glTranslated(-myShip.g_look[0], -myShip.g_look[1], -myShip.g_look[2]);

		shoottexture[0].bind();

		gl.glTexEnvf(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
		gl.glBegin(GL2.GL_QUADS);

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xx, yy - 3f, zz + 2f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xx, yy - 0f, zz + 2f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xx, yy - 0f, zz + 5f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xx, yy - 3f, zz + 5f);

		gl.glEnd();

		shoottexture[1].bind();

		gl.glTexEnvf(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
		gl.glBegin(GL2.GL_QUADS);

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xx, yy - 3.5f, zz - 4.6f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xx, yy + 0.5f, zz - 4.6f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xx, yy + 0.5f, zz - 1.6f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xx, yy - 3.5f, zz - 1.6f);

		gl.glEnd();

		gl.glPopMatrix();

	}

	// 画立方体
	private void box(float x, float y, float z, GL2 gl) {
		gl.glPushMatrix();// 压入堆栈
		gl.glScalef(x, y, z);
		// gl.glEnable(GL.GL_TEXTURE_2D); //使用纹理
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);// 前
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);// 后
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);// 上
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);// 下
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);// 左
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);// 右
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glEnd();
		// gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();

	}

	// 画飞机
	void airplane(float x, float y, float z, float r2, float radii, GL2 gl)// 组合飞机
	{
		glu = new GLU();
		gl.glPushMatrix();// 压入堆栈

		gl.glTranslatef(x, y, z);// 飞机的定位

		gl.glRotatef(-r2 * 180f / 3.14159f, 0.0f, 1.0f, 0.0f);// 飞机的旋转
		gl.glTranslatef(radii, 0.0f, 0.0f); // 飞机的旋转半径
		gl.glRotatef(30.0f, 0.0f, 0.0f, 1.0f);// 飞机的倾斜
		// =============================================

		gl.glPushMatrix();//
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		box(1.0f, 0.1f, 0.02f, gl); // 方，螺旋浆
		gl.glPopMatrix();

		gl.glColor3f(1.0f, 1.0f, 1.0f); // 白色
		// gl.glEnable(GL.GL_TEXTURE_2D); //使用纹理
		// gl.glBindTexture(GL.GL_TEXTURE_2D, 4);//
		ammotexture.bind();
		gl.glTranslatef(0.0f, 0.0f, -0.5f); // 后移
		glu.gluSphere(g_text, 0.4f, 8, 8); // 机头园(半径)
		// =============================================
		gl.glTranslatef(0.0f, -0.0f, -2); // 位置调整,与机头对接
		glu.gluCylinder(g_text, 0.4, 0.4, 2.0, 8, 4);// 机身,园柱(半径、高)
		// =====================================================
		gl.glRotatef(-180.0f, 1.0f, 0.0f, 0.0f); // 角度调整
		gl.glTranslatef(0.0f, -0.0f, 0.0f); // 位置调整,缩进一点
		glu.gluCylinder(g_text, 0.4, 0.1, 1.5, 8, 4);// 机尾,园锥(底半径、高)
		// ======================================================
		// gl.glBindTexture(GL.GL_TEXTURE_2D, textureArr[0]);//
		ammotexture.bind();

		gl.glTranslatef(0.0f, -0.8f, 1.2f); // 位置调整

		box(1.0f, 0.05f, 0.3f, gl); // 后翼

		gl.glTranslatef(0.0f, 0.1f, 0.0f); // 位置调整

		box(0.05f, 0.6f, 0.30f, gl); // 后垂翼

		// ======================================================
		gl.glTranslatef(0.0f, 0.7f, -1.9f); // 位置调整

		box(3.0f, 0.05f, 0.5f, gl); // 前翼

		// ======================================================

		gl.glPopMatrix();
		// gl.glDisable(GL.GL_TEXTURE_2D);
		/*
		 * r2 += 0.2f;
		 * 
		 * if (r2 > 360) { r2 = 0; }
		 */

		for (int i = 0; i < 3; i++) {
			enemyairplane[i].r_angle += 0.005f * (i + 1);
			if (enemyairplane[i].r_angle > 360) {
				enemyairplane[i].r_angle = 0;
			}
		}

	}

	// 这部分代码暂时不用改
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glViewport(0, 0, w, h); // 视见区域
		gl.glMatrixMode(GL2.GL_PROJECTION);// 哪一个矩阵堆栈
		// gl.glLoadIdentity(); //重置矩阵
		glu.gluPerspective(45.0, (float) w / (float) h, 1.0, 1000.0);// 创建透视矩阵
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {

	}

	// 返回我船的航速
	public int returnSpeed() {
		return tSpeed;
	}

	// 返回我船的生命值
	public int returnLife() {
		return tLife;
	}

	// 返回我船的攻击力
	public int returnAttack() {
		return tAttack;
	}

	public void keyTyped(KeyEvent key) {

	}

	// 摄像机的搭建
	public void camera() {

		if (myShip.g_elev < -20)
			myShip.g_elev = -20;
		if (myShip.g_elev > 40)
			myShip.g_elev = 40;

		if (myShip.g_eye[0] < -(MAP * 2 - 20))
			myShip.g_eye[0] = -(MAP * 2 - 20);
		if (myShip.g_eye[0] > (MAP * 2 - 20))
			myShip.g_eye[0] = (MAP * 2 - 20);
		if (myShip.g_eye[2] < -(MAP * 2 ))
			myShip.g_eye[2] = -(MAP * 2 );
		if (myShip.g_eye[2] > (MAP * 2 - 40))
			myShip.g_eye[2] = (MAP * 2 - 40);

		// 摄像机高度
		myShip.g_eye[1] = 3f;
		// 响应鼠标移动
		if (isMove == 1) {
			if (isPicture == 1) {
				// g_Angle += (firstMoveX - lastMoveX) * speed*2;

				if ((firstMoveX - lastMoveX) > 0) {
					// 旋转限制
					if (myShip.g_Angle >= -360f) {
						myShip.g_Angle -= 2;
						yrot += 2f;
					}

				} else {
					// 旋转限制
					if (myShip.g_Angle <= 360f) {
						myShip.g_Angle += 2;
						yrot -= 2f;
					}

				}

				// g_elev += (lastMoveY - firstMoveY) * 0.4f;
				firstMoveX = 0;
				lastMoveX = 0;
				firstMoveY = 0;
				lastMoveY = 0;
				isMove = 0;
			}
		}
		// g_look[0] = (float) ((float)g_eye[0] + 100*Math.cos(rad_xz));
		// g_look[2] = (float) ((float)g_eye[2] + 100*Math.sin(rad_xz));
		myShip.rad_xz = (float) 3.13149 * myShip.g_Angle / 180.0f;
		myShip.g_look[0] = (float) ((float) myShip.g_eye[0] + Math
				.cos(myShip.rad_xz));
		myShip.g_look[2] = (float) ((float) myShip.g_eye[2] + Math
				.sin(myShip.rad_xz));

		myShip.g_look[1] = myShip.g_eye[1] + myShip.g_elev / 100;

		// glu.gluLookAt(g_eye[0],g_eye[1],g_eye[2],g_look[0],g_look[1]+g_elev,g_look[2],0.0,1.0,0.0);
		glu.gluLookAt(myShip.g_eye[0], myShip.g_eye[1], myShip.g_eye[2],
				myShip.g_look[0], myShip.g_look[1], myShip.g_look[2], 0.0, 1.0,
				0.0);

		//
		// System.out.print(g_look[0]+" , "+g_look[1]+" , "+g_look[2]+"///");

	}

	// 画出地面
	public void DrawGround(GL2 gl) {

		// gl.glPushAttrib(GL.GL_CURRENT_BIT);
		// gl.glEnable(GL.GL_BLEND);
		gl.glPushMatrix();
		// gl.glColor3f(0.5f, 0.7f, 1.0f);
		// gl.glTranslatef(0,0.0f,0);
		int size0 = (int) (MAP * 2);
		gl.glBegin(GL.GL_LINES);
		for (int x = -size0; x < size0; x += 4) {
			gl.glVertex3i(x, 0, -size0);
			gl.glVertex3i(x, 0, size0);
		}
		for (int z = -size0; z < size0; z += 4) {
			gl.glVertex3i(-size0, 0, z);
			gl.glVertex3i(size0, 0, z);
		}
		gl.glEnd();

		gl.glPopMatrix();
		// gl.glDisable(GL.GL_BLEND);
		// gl.glPopAttrib();
	}

	// 画出窗口的正方形
	public void Drawcube(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glPushMatrix();
		// gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
		// gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
		// gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
		// gl.glBindTexture(GL.GL_TEXTURE_2D, );//指定和绑定纹理(这里是指定纹理)
		// gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE);
		// 锁定图片，图片跟随摄像机
		gl.glTranslated(myShip.g_look[0], myShip.g_look[1], myShip.g_look[2]);

		// 绕Y，Z轴旋转
		gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

		gl.glTranslated(-myShip.g_look[0], -myShip.g_look[1], -myShip.g_look[2]);
		
		if (myShip.life > 60) {
			pngtexture1num = 0;
		} else if (myShip.life > 50 && myShip.life <= 60) {
			pngtexture1num = 1;
		} else if (myShip.life > 40 && myShip.life <= 50) {
			pngtexture1num = 2;
		} else if (myShip.life > 30 && myShip.life <= 40) {
			pngtexture1num = 3;
		} else if (myShip.life > 20 && myShip.life <= 30) {
			pngtexture1num = 4;
		} else {
			pngtexture1num = 4;
		}
		pngtexture1[pngtexture1num].bind();

		gl.glTexEnvf(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);

		gl.glBegin(GL2.GL_QUADS);

		/*
		 * gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(g_look[0]+8, g_look[1]-2,
		 * g_look[2]-4); gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(g_look[0]+8,
		 * g_look[1]-2, g_look[2]+4); gl.glTexCoord2f(1.0f,
		 * 1.0f);gl.glVertex3f(g_look[0]+8, g_look[1]+4, g_look[2]+4);
		 * gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(g_look[0]+8, g_look[1]+4,
		 * g_look[2]-4);
		 */
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(myShip.g_look[0] + 10, (float) (myShip.g_look[1] - 5),
				myShip.g_look[2] - 7);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(myShip.g_look[0] + 10, (float) (myShip.g_look[1] + 5),
				myShip.g_look[2] - 7);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(myShip.g_look[0] + 10, (float) (myShip.g_look[1] + 5),
				myShip.g_look[2] + 7);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(myShip.g_look[0] + 10, (float) (myShip.g_look[1] - 5),
				myShip.g_look[2] + 7);

		gl.glEnd();
		
		gl.glPopMatrix();

	}

	// 天空盒的加载
	public void CreateSkyBox(int a, int wi, int he, int le, GL2 gl) {
		float width = MAP * wi;
		float height = MAP * he;
		float length = MAP * le;
		float x = MAP - width / 2;
		float y = MAP / a - height / 2 + 12;
		float z = -MAP - length / 2;
		// /////////////////////////////////////////////////////////////////////////////
		bmptexture[0].bind();
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x + width, y, z);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x + width, y + height, z);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x, y + height, z);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x, y, z);
		gl.glEnd();

		bmptexture[1].bind();
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x, y, z + length);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x, y + height, z + length);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x + width, y + height, z + length);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x + width, y, z + length);
		gl.glEnd();

		bmptexture[2].bind();
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x + width, y + height, z);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x + width, y + height, z + length);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x, y + height, z + length);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x, y + height, z);
		gl.glEnd();

		bmptexture[3].bind();
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x, y + height, z);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x, y + height, z + length);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x, y, z + length);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x, y, z);
		gl.glEnd();

		bmptexture[4].bind();
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(x + width, y, z);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(x + width, y, z + length);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(x + width, y + height, z + length);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(x + width, y + height, z);
		gl.glEnd();

	}

	// 负责响应
	public void keyPressed(KeyEvent e) {

		// 弧度转换
		myShip.rad_xz = 3.13149f * myShip.g_Angle / 180.0f;

		// 处理键盘事件
		// 前进
		if (e.getKeyCode() == 87) {

			/*
			 * if(iftf()){ //forwardZ += 0.001f; //forwardZ += 90f; }else{
			 */

			myShip.g_eye[2] += Math.sin(myShip.rad_xz) * myShip.speed * goSpeed;
			myShip.g_eye[0] += Math.cos(myShip.rad_xz) * myShip.speed * goSpeed;

		}
		// 后退
		if (e.getKeyCode() == 83) {

			myShip.g_eye[2] -= Math.sin(myShip.rad_xz) * myShip.speed * goSpeed;
			myShip.g_eye[0] -= Math.cos(myShip.rad_xz) * myShip.speed * goSpeed;

			/* if(iftf()){ */
			// forwardZ += 0.001f;
			// forwardZ += 0.009f;

		}
		// 左旋转
		if (e.getKeyCode() == 65) {

			// 旋转限制
			if (myShip.g_Angle >= -360f) {
				myShip.g_Angle -= 2;

				System.out.print(myShip.speed * 2);
				System.out.print(myShip.g_Angle);
				yrot += 2f;
			}

		}
		// 右旋转
		if (e.getKeyCode() == 68) {

			// 旋转限制
			if (myShip.g_Angle <= 360f) {
				myShip.g_Angle += 2;
				System.out.print(myShip.g_Angle);
				yrot -= 2f;
			}

		}

		// 抬头
		if (e.getKeyCode() == 69) {

			myShip.g_elev += 0.2f;

			if (myShip.g_elev <= 40) {
				zrot += 0.12f;
			} else {
				zrot = 23.5f;
			}

			System.out.print(myShip.g_elev + "  ");
			System.out.print(zrot);

		}
		// 低头
		if (e.getKeyCode() == 81) {

			myShip.g_elev -= 0.2f;
			// g_elev -=speed;
			if (myShip.g_elev >= -20) {
				zrot -= 0.11f;
			} else {
				zrot = -11f;
			}

			System.out.print(zrot);

		}

		// 空格
		if (e.getKeyCode() == 32) {
			ifshoot = true;

		}

	}

	// 鼠标移动
	public void mouseMoved(MouseEvent e) {
		if (isMove == 0) {
			firstMoveX = e.getX();
			firstMoveY = e.getY();
			isPicture = 0;
			isMove = 1;
		} else {
			lastMoveX = e.getX();
			lastMoveY = e.getY();
			isPicture = 1;
		}

	}

	public void keyReleased(KeyEvent key) {
	}

	public void mouseClicked(MouseEvent e) {
		// System.out.println("此时已经点击鼠标");

	}

	public void mousePressed(MouseEvent e) {
		// 鼠标左键按下
		if (e.getButton() == e.BUTTON1) {
			// 射击音效
			shootPlay = new GunSound(shootList);
			shootPlay.star();
			// 射击开
			ifshoot = true;
			YN = 10;
			for (int j = 0; j < enemyNumber; j++) {
				enemyShip[j].depositRadian = enemyShip[j].depositAngle / 180f * 3.13149f;
			}
		}
		if (e.getButton() == e.BUTTON3) {
			goSpeed = 3;
			myShip.speed *= 3;
		}

	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == e.BUTTON3) {
			goSpeed = 1;
			myShip.speed /= 3;
		}

	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	// 用于音乐播放
	public void run() {
		while (true) {
			try {
				System.out.println(tLife);
				if (tLife / 10 == 6 && tLife >= 0 && playF) {
					glassPlay = new GunSound(glassList);
					glassPlay.star();
					playF = false;
				}
				if (tLife / 10 == 5 && tLife >= 0 && playS) {
					glassPlay = new GunSound(glassList);
					glassPlay.star();
					playS = false;
				}
				if (tLife / 10 == 4 && tLife >= 0 && playT) {
					glassPlay = new GunSound(glassList);
					glassPlay.star();
					playT = false;
				}
				if (tLife / 10 == 3 && tLife >= 0 && playR) {
					glassPlay = new GunSound(glassList);
					glassPlay.star();
					playR = false;
				}
				if (isCrash) {
					hitPlay = new GunSound(hitList);
					hitPlay.star();
					isCrash = false;
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}