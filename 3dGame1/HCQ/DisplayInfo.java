package HCQ;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DisplayInfo extends JPanel {
	
	public int speed;//��ʾ��ʱ�Ҵ����ٶ�
	public int attack;//��ʾ��ʱ�Ҵ��Ĺ�����
	public int life;//��ʾ��ʱ�Ҵ�������ֵ
	public Image ibuffer = null;//��ʾͼƬ
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	public static Image imgs[] = {
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������0.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������1.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������2.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������3.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������4.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������5.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������6.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������7.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������8.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������9.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������10.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������11.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������12.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������13.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������14.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����0.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����1.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����2.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����3.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����4.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����5.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����6.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����7.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����8.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����9.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����10.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����11.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����12.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����13.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/����14.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������0.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������1.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������2.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������3.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������4.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������5.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������6.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������7.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������8.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������9.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������10.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������11.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������12.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������13.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("DashBoard/������14.png")),
		tk.getImage(DisplayInfo.class.getClassLoader().getResource("data/��屳��.jpg")),
	};
	
	DisplayInfo(int tspeed,int tattack,int tlife) {
		speed = tspeed;
		attack = tattack;
		life = tlife;
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}
	
	//����Ĵ������ڻ���Ҵ�������ֵ�����������ٶ�
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
	
	// ˫���壬������Ļ��˸
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
	
	//�����ػ滭ͼ����
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
