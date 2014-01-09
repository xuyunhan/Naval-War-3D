package HCQ;

/**
 *  <code>MainFrame.java</code>
 *  <p>功能:
 *  
 *  <p>Copyright 软件工程1101班 2013 All right reserved.
 *  @author 薛继光 aurora.xue@gmail.com 时间 2013-12-28 下午03:31:08	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import OBJLoader.OBJModel;

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
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.List;
import java.awt.Color;

public class MainFrame extends Frame implements ActionListener,Runnable {

	JOGL06_Texture listener;
	DisplayInfo display;
	public JPanel draw;
	public JButton exitButton;
	public JButton mainButton;
	GLCapabilities caps;
	GLCanvas canvas;
	public static Animator animator;
	String musicFile;
	String filePath = System.getProperty("user.dir");
	public ImageIcon exitImage = new ImageIcon(filePath + "\\data\\退出按钮.png");
	public ImageIcon mainImage = new ImageIcon(filePath + "\\data\\主菜单按钮.png");
	public boolean goOn;
	public boolean select;
	
	public MainFrame(boolean isPlay,int enemyNumber,int goodsNumber,int netChoice) {
		goOn = false;
		select = true;
		listener = new JOGL06_Texture(enemyNumber,goodsNumber,netChoice);
		exitButton = new JButton(exitImage);
		exitButton.setBounds(700,600,100,60);
		exitButton.setBorderPainted(false);
		exitButton.addActionListener(this);
		this.add(exitButton);
		mainButton = new JButton(mainImage);
		mainButton.setBounds(600, 600, 100, 60);
		mainButton.setBorderPainted(false);
		mainButton.addActionListener(this);
		this.add(mainButton);
		if (isPlay) {
			musicPlay();
		}
		setLayout(null);
    	display = new DisplayInfo(0,0,0);
    	display.setVisible(true);
    	display.setBounds(0, 600, 600, 60);
		this.add(display);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		caps = new GLCapabilities(null);
		canvas = new GLCanvas(caps);
		canvas.setFocusable(true);
		canvas.addGLEventListener(listener);
		canvas.addKeyListener(listener);
		canvas.addMouseListener(listener);
		canvas.addMouseMotionListener(listener);
		canvas.setSize(800,600);
		animator = new Animator(canvas);
		new Thread(this).start();
		this.add(canvas);
		this.setBounds(300,10, 800, 660);
	}
	
	//下面一段待遇用于播放背景音乐
	public void musicPlay() {
		MusicPlay play = null;
		musicFile = System.getProperty("user.dir") + "\\music\\背景音乐.mp3";
		Vector<String> playlist;
		playlist = new Vector<String>();
		String[] list = new String[1];
		list[0] = musicFile;
		for (int i = 0; i < list.length; i++) {
			playlist.add(list[i]);
		}
		play = new MusicPlay(playlist);
		play.star();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitButton) {
			System.exit(0);
		}
		if (e.getSource() == mainButton) {
			MainInterface test = new MainInterface();
			this.dispose();
		}

	}

	public void run() {
		while(true) {
			try {
				display.speed = listener.returnSpeed();
				display.attack = listener.returnAttack();
				display.life = listener.returnLife();
				if ( listener.returnLife() < 0 && select && listener != null) {
					System.out.println("dsdsadad"+listener.returnLife());
					goOn = true;
					if (goOn) {
						Restart re = new Restart();
						//this.dispose();
						select = false;
					}
					goOn = false;
				}
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}

