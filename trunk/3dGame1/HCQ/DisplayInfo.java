package HCQ;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DisplayInfo extends JPanel {
	public int speed;
	public int attack;
	public int life;
	public Image ibuffer = null;
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
	};
	
	DisplayInfo(int tspeed,int tattack,int tlife) {
		speed = tspeed;
		attack = tattack;
		life = tlife;
//		this.setBounds(0, 600, 600, 100);
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}
	
	public void paint(Graphics g) {
//		System.out.println("���ڻ�ͼ");
		switch(speed) {
		case 0:
			g.drawImage(imgs[0], 0, 0, this);
			break;
		case 1:
			g.drawImage(imgs[1], 0, 0, this);
			break;
		case 2:
			g.drawImage(imgs[2], 0, 0, this);
			break;
		case 3:
			g.drawImage(imgs[3], 0, 0, this);
			break;
		case 4:
			g.drawImage(imgs[4], 0, 0, this);
			break;
		case 5:
			g.drawImage(imgs[5], 0, 0, this);
			break;
		case 6:
			g.drawImage(imgs[6], 0, 0, this);
			break;
		case 7:
			g.drawImage(imgs[7], 0, 0, this);
			break;
		case 8:
			g.drawImage(imgs[8], 0, 0, this);
			break;
		case 9:
			g.drawImage(imgs[9], 0, 0, this);
			break;
		case 10:
			g.drawImage(imgs[10], 0, 0, this);
			break;
		case 11:
			g.drawImage(imgs[11], 0, 0, this);
			break;
		case 12:
			g.drawImage(imgs[12], 0, 0, this);
			break;
		case 13:
			g.drawImage(imgs[13], 0, 0, this);
			break;
		case 14:
			g.drawImage(imgs[14], 0, 0, this);
			break;
		default :
			g.drawImage(imgs[14], 0, 0, this);
		}
		switch(attack) {
		case 0:
			g.drawImage(imgs[15], 100, 0, this);
			break;
		case 1:
			g.drawImage(imgs[16], 100, 0, this);
			break;
		case 2:
			g.drawImage(imgs[17], 100, 0, this);
			break;
		case 3:
			g.drawImage(imgs[18], 100, 0, this);
			break;
		case 4:
			g.drawImage(imgs[19], 100, 0, this);
			break;
		case 5:
			g.drawImage(imgs[20], 100, 0, this);
			break;
		case 6:
			g.drawImage(imgs[21], 100, 0, this);
			break;
		case 7:
			g.drawImage(imgs[22], 100, 0, this);
			break;
		case 8:
			g.drawImage(imgs[23], 100, 0, this);
			break;
		case 9:
			g.drawImage(imgs[24], 100, 0, this);
			break;
		case 10:
			g.drawImage(imgs[25], 100, 0, this);
			break;
		case 11:
			g.drawImage(imgs[26], 100, 0, this);
			break;
		case 12:
			g.drawImage(imgs[27], 100, 0, this);
			break;
		case 13:
			g.drawImage(imgs[28], 100, 0, this);
			break;
		case 14:
			g.drawImage(imgs[29], 100, 0, this);
			break;
		default:
			g.drawImage(imgs[29], 100, 0, this);
		
		}
		switch(life) {
		case 0:
			g.drawImage(imgs[30], 200, 0, this);
			break;
		case 1:
			g.drawImage(imgs[31], 200, 0, this);
			break;
		case 2:
			g.drawImage(imgs[32], 200, 0, this);
			break;
		case 3:
			g.drawImage(imgs[33], 200, 0, this);
			break;
		case 4:
			g.drawImage(imgs[34], 200, 0, this);
			break;
		case 5:
			g.drawImage(imgs[35], 200, 0, this);
			break;
		case 6:
			g.drawImage(imgs[36], 200, 0, this);
			break;
		case 7:
			g.drawImage(imgs[37], 200, 0, this);
			break;
		case 8:
			g.drawImage(imgs[38], 200, 0, this);
			break;
		case 9:
			g.drawImage(imgs[39], 200, 0, this);
			break;
		case 10:
			g.drawImage(imgs[40], 200, 0, this);
			break;
		case 11:
			g.drawImage(imgs[41], 200, 0, this);
			break;
		case 12:
			g.drawImage(imgs[42], 200, 0, this);
			break;
		case 13:
			g.drawImage(imgs[43], 200, 0, this);
			break;
		case 14:
			g.drawImage(imgs[44], 200, 0, this);
			break;
		default:
			g.drawImage(imgs[44], 200, 0, this);
		
		}
		
		
	}
	
	public void update(Graphics g) {// ˫���壬������Ļ��˸
		if (ibuffer == null) {
			ibuffer = this.createImage(this.getSize().width,
					this.getSize().height);
		}
		Graphics gbuffer = ibuffer.getGraphics();
		gbuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);
		paint(gbuffer);
		g.drawImage(ibuffer, 0, 0, null);
	}
	
	public class PaintThread implements Runnable {
		public void run() {
			while (true) {
				try {
					repaint();
		//			System.out.println("�����߳�");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
