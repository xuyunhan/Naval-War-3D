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

public class MainFrame extends Frame implements ActionListener, KeyListener,
		MouseListener, MouseMotionListener {

	JOGL06_Texture listener = new JOGL06_Texture();
	GLCapabilities caps;
	GLCanvas canvas;
	public static Animator animator;

	public MainFrame() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		JPanel jp = new JPanel();
		JButton jb = new JButton("重画");
		jb.setFocusable(false);
		jp.add(jb);
		// jp.setSize(800,200);
		this.add("South", jp);
		caps = new GLCapabilities(null);
		canvas = new GLCanvas(caps);
		// canvas.addGLEventListener(this);
		canvas.setFocusable(true);
		canvas.addGLEventListener(listener);
		canvas.addKeyListener(listener);
		canvas.addMouseListener(listener);
		canvas.addMouseMotionListener(listener);
		// canvas.setSize(800, 400);
		this.add(canvas, BorderLayout.CENTER);
		animator = new Animator(canvas);
		// animator.start();
		setSize(800, 600);
		centerWindow(this);
		
		
		
		
		//设置空光标
		java.net.URL classUrl = this.getClass().getResource("");  
        Image imageCursor = Toolkit.getDefaultToolkit().getImage(classUrl);  
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(imageCursor,  
                new Point(0, 0), "cursor"));  
	}

	private void centerWindow(Component frame) { // 居中窗体

		Dimension screenSize =

		Toolkit.getDefaultToolkit().getScreenSize();

		Dimension frameSize = frame.getSize();

		if (frameSize.width > screenSize.width)

			frameSize.width = screenSize.width;

		if (frameSize.height > screenSize.height)

			frameSize.height = screenSize.height;

		frame.setLocation(

		(screenSize.width - frameSize.width) >> 1,

		(screenSize.height - frameSize.height) >> 1

		);

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent e) {
		/*
		 * if (listener.isMove == 0) { listener.firstMoveX = e.getX();
		 * listener.firstMoveY = e.getY(); listener.isPicture = 0;
		 * listener.isMove = 1; } else { listener.lastMoveX = e.getX();
		 * listener.lastMoveY = e.getY(); listener.isPicture = 1; }
		 */

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {
		/*
		 * float speed = 1f; listener.rad_xz = (float) 3.13149 *
		 * listener.g_Angle / 180.0f; if (e.getKeyCode() == 38) {
		 * listener.g_eye[2] += Math.sin(listener.rad_xz) * speed;
		 * listener.g_eye[0] += Math.cos(listener.rad_xz) * speed; } if
		 * (e.getKeyCode() == 40) { listener.g_eye[2] -=
		 * Math.sin(listener.rad_xz) * speed; listener.g_eye[0] -=
		 * Math.cos(listener.rad_xz) * speed; } if (e.getKeyCode() == 37) {
		 * listener.g_Angle -= speed * 2; listener.yrot += 2f; } if
		 * (e.getKeyCode() == 39) { listener.g_Angle += speed * 2; listener.yrot
		 * -= 2f; } if (e.getKeyCode() == 33) { listener.g_elev += 0.2f; if
		 * (listener.g_elev <= 50) { listener.zrot += 0.12f; } else {
		 * listener.zrot = 30f; }
		 * 
		 * if (listener.g_elev == 0) { listener.zrot = 0; }
		 * 
		 * System.out.print(listener.g_elev);
		 * 
		 * }
		 * 
		 * if (e.getKeyCode() == 34) {
		 * 
		 * listener.g_elev -= 0.2f; // g_elev -=speed; if (listener.g_elev >=
		 * -20) { listener.zrot -= 0.11f; } else { listener.zrot = -11f; }
		 * 
		 * System.out.print(listener.zrot);
		 * 
		 * }
		 */
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void dispose(GLAutoDrawable arg0) {

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
}

