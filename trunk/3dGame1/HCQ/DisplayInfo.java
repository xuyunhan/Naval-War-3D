package HCQ;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DisplayInfo extends JPanel {
	
	public int speed;//显示此时我船的速度
	public int attack;//显示此时我船的攻击力
	public int life;//显示此时我船的生命值
	public Image ibuffer = null;//显示图片
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	public static Image imgs[] = {
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力0.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力1.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力2.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力3.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力4.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力5.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力6.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力7.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力8.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力9.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力10.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力11.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力12.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力13.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/攻击力14.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速0.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速1.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速2.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速3.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速4.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速5.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速6.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速7.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速8.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速9.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速10.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速11.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速12.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速13.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/航速14.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力0.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力1.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力2.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力3.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力4.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力5.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力6.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力7.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力8.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力9.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力10.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力11.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力12.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力13.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/生命力14.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("data/面板背景.jpg")),
	};
	
	DisplayInfo(int tspeed,int tattack,int tlife) {
		speed = tspeed;
		attack = tattack;
		life = tlife;
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}
	
	//下面的代码用于绘出我船的生命值，攻击力和速度
	public void paint(Graphics g) {
		g.drawImage(imgs[45],0,0,this);
		if (attack <= 0) {
			g.drawImage(imgs[0],0,0,this);
		}
		else if (attack >0 && attack <=10) {
			g.drawImage(imgs[1], 0, 0, this);
		}
		else if (attack >10 && attack <= 20) {
			g.drawImage(imgs[2], 0, 0, this);
		}
		else if (attack >20 && attack <= 30) {
			g.drawImage(imgs[3], 0, 0, this);
		}
		else if (attack > 30 && attack <= 40) {
			g.drawImage(imgs[4], 0, 0, this);
		}
		else if (attack > 40 && attack <= 50) {
			g.drawImage(imgs[5], 0, 0, this);
		}
		else if (attack > 50 && attack <= 60) {
			g.drawImage(imgs[6], 0, 0, this);
		}
		else if (attack > 60 && attack <= 70) {
			g.drawImage(imgs[7], 0, 0, this);
		}
		else if (attack > 70 && attack <= 80) {
			g.drawImage(imgs[8], 0, 0, this);
		}
		else if (attack > 80 && attack <= 90) {
			g.drawImage(imgs[9], 0, 0, this);
		}
		else if (attack > 90 && attack <= 100) {
			g.drawImage(imgs[10], 0, 0, this);
		}
		else if (attack > 100 && attack <= 110) {
			g.drawImage(imgs[11], 0, 0, this);
		}
		else if (attack > 110 && attack <= 120) {
			g.drawImage(imgs[12], 0, 0, this);
		}
		else if (attack > 120 && attack <= 130) {
			g.drawImage(imgs[13], 0, 0, this);
		}
		else if (attack > 130) {
			g.drawImage(imgs[14], 0, 0, this);
		}
		
		if (speed <= 0) {
			g.drawImage(imgs[15], 200, 0,this);
		}
		else if (speed >0 && speed <=10) {
			g.drawImage(imgs[16], 200, 0, this);
		}
		else if (speed >10 && speed <= 20) {
			g.drawImage(imgs[17], 200, 0, this);
		}
		else if (speed >20 && speed <= 30) {
			g.drawImage(imgs[18], 200, 0, this);
		}
		else if (speed > 30 && speed <= 40) {
			g.drawImage(imgs[19], 200, 0, this);
		}
		else if (speed > 40 && speed <= 50) {
			g.drawImage(imgs[20], 200, 0, this);
		}
		else if (speed > 50 && speed <= 60) {
			g.drawImage(imgs[21], 200, 0, this);
		}
		else if (speed > 60 && speed <= 70) {
			g.drawImage(imgs[22], 200, 0, this);
		}
		else if (speed > 70 && speed <= 80) {
			g.drawImage(imgs[23], 200, 0, this);
		}
		else if (speed > 80 && speed <= 90) {
			g.drawImage(imgs[24], 200, 0, this);
		}
		else if (speed > 90 && speed <= 100) {
			g.drawImage(imgs[25], 200, 0, this);
		}
		else if (speed > 100 && speed <= 110) {
			g.drawImage(imgs[26], 200, 0, this);
		}
		else if (speed > 110 && speed <= 120) {
			g.drawImage(imgs[27], 200, 0, this);
		}
		else if (speed > 120 && speed <= 130) {
			g.drawImage(imgs[28], 200, 0, this);
		}
		else if (speed > 130) {
			g.drawImage(imgs[29], 200, 0, this);
		}
		
		if (life <= 0) {
			g.drawImage(imgs[30], 400, 0,this);
		}
		else if (life >0 && life <=10) {
			g.drawImage(imgs[31], 400, 0, this);
		}
		else if (life >10 && life <= 20) {
			g.drawImage(imgs[32], 400, 0, this);
		}
		else if (life >20 && life <= 30) {
			g.drawImage(imgs[33], 400, 0, this);
		}
		else if (life > 30 && life <= 40) {
			g.drawImage(imgs[34], 400, 0, this);
		}
		else if (life > 40 && life <= 50) {
			g.drawImage(imgs[35], 400, 0, this);
		}
		else if (life > 50 && life <= 60) {
			g.drawImage(imgs[36], 400, 0, this);
		}
		else if (life > 60 && life <= 70) {
			g.drawImage(imgs[37], 400, 0, this);
		}
		else if (life > 70 && life <= 80) {
			g.drawImage(imgs[38], 400, 0, this);
		}
		else if (life > 80 && life <= 90) {
			g.drawImage(imgs[39], 400, 0, this);
		}
		else if (life > 90 && life <= 100) {
			g.drawImage(imgs[40], 400, 0, this);
		}
		else if (life > 100 && life <= 110) {
			g.drawImage(imgs[41], 400, 0, this);
		}
		else if (life > 110 && life <= 120) {
			g.drawImage(imgs[42], 400, 0, this);
		}
		else if (life > 120 && life <= 130) {
			g.drawImage(imgs[43], 400, 0, this);
		}
		else if (life > 130) {
			g.drawImage(imgs[44], 400, 0, this);
		}
		
	}
	
	// 双缓冲，避免屏幕闪烁
	public void update(Graphics g) {
		if (ibuffer == null) {
			ibuffer = this.createImage(this.getSize().width,
					this.getSize().height);
		}
		Graphics gbuffer = ibuffer.getGraphics();
		gbuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);
		paint(gbuffer);
		g.drawImage(ibuffer, 0, 0, null);
	}
	
	//用于重绘画图区域
	public class PaintThread implements Runnable {
		public void run() {
			while (true) {
				try {
					repaint();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
