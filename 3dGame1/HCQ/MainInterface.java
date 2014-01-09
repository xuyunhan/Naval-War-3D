package HCQ;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.util.List;
import java.awt.Color;

public class MainInterface extends JFrame implements ActionListener,Runnable {

	public JButton start;
	public JButton exitGame;
	public JButton set;
	public JLabel label;
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	String filePath = System.getProperty("user.dir");
	public ImageIcon gameImage = new ImageIcon(filePath + "\\data\\游戏主界面.jpg");
	public ImageIcon startImage = new ImageIcon(filePath + "\\data\\开始游戏按钮.png");
	public ImageIcon exitGameImage = new ImageIcon(filePath + "\\data\\退出游戏按钮.png");
	public ImageIcon setImage = new ImageIcon(filePath + "\\data\\设置按钮.png");
	MainFrame test;
	SetFrame setup;
	boolean isPlay;
	public int enemyNumber;
	public int netChoice;
	
	MainInterface() {
		setLayout(null);
		label = new JLabel(gameImage);
		label.setIcon(gameImage);
		label.setBounds(0, 0, gameImage.getIconWidth(),gameImage.getIconHeight());
		add(label);
		start = new JButton(startImage);
		start.setBounds(20, 460, 60, 180);
		start.setBorderPainted(false);
		start.addActionListener(this);
		add(start);
		exitGame = new JButton(exitGameImage);
		exitGame.setBounds(660, 460, 60, 180);
		exitGame.setBorderPainted(false);
		exitGame.addActionListener(this);
		add(exitGame);
		set = new JButton(setImage);
		set.setBounds(740, 480, 60, 180);
		set.setBorderPainted(false);
		set.addActionListener(this);
		add(set);
		this.setBounds(300, 10, 800, 690);
		this.setVisible(true);
		new Thread(this).start();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			this.dispose();
		    test = new MainFrame(isPlay,enemyNumber,1,netChoice);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					test.setVisible(true);

				}
			});
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					test.animator.start();
				}
			});
		}
		if (e.getSource() == exitGame) {
			System.exit(0);
		}
		if (e.getSource() == set) {
			setup = new SetFrame();

		}
	}

	public void run() {
		while(true) {
			try {
				if (setup != null && test != null && test.listener != null && test.listener.myShip != null) {
					test.listener.myShip.speed = setup.returnSpeed() / 10;
					//System.out.println("响应 " + test.listener.myShip.speed);
				}
				
				if (setup != null) {
					isPlay = setup.playMusic;
					//System.out.println(isPlay);
				}
				if (setup != null) {
					enemyNumber = setup.enemyNumber;
				}
				if (setup != null) {
					netChoice = setup.netChoice;
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String args[]) {
		MainInterface test = new MainInterface();

	}
}
