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

	JOGL06_Texture listener = new JOGL06_Texture();
	DisplayInfo display;
	public JPanel draw;
	GLCapabilities caps;
	GLCanvas canvas;
	public static Animator animator;

	public MainFrame() {
		setLayout(null);
		draw = new JPanel();
		draw.setBounds(0, 600, 600, 100);
    	display = new DisplayInfo(0,0,0);
    	display.setVisible(true);
    	draw = display;
    	display.setBounds(0, 600, 600, 60);
		this.add(display);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
//		JPanel jp = new JPanel();
//		JButton jb = new JButton("重画");
//		jb.setFocusable(false);
//		jp.setBounds(0, 600, 800, 120);
//		jp.add(jb);
		// jp.setSize(800,200);
		//this.add("South", jp);
//		this.add(jp);
		caps = new GLCapabilities(null);
		canvas = new GLCanvas(caps);
		// canvas.addGLEventListener(this);
		canvas.setFocusable(true);
		canvas.addGLEventListener(listener);
		canvas.addKeyListener(listener);
		canvas.addMouseListener(listener);
		canvas.addMouseMotionListener(listener);
		// canvas.setSize(800, 400);
		canvas.setSize(800,600);
//		this.add(canvas, BorderLayout.CENTER);
		animator = new Animator(canvas);
		// animator.start();
		new Thread(this).start();
		this.add(canvas);
//		setSize(800, 600);
		this.setBounds(300,10, 800, 660);
		//centerWindow(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]) {
		final MainFrame test = new MainFrame();
		
		SwingUtilities.invokeLater(

		new Runnable() {

			public void run() {

				test.setVisible(true);

			}

		}

		);
		SwingUtilities.invokeLater(

				new Runnable() {

					public void run() {

						test.animator.start();

					}

				}

				);

	}

	public void run() {
		while(true) {
			try {
				display.speed ++;
				display.life++;
				display.attack++;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}

