package HCQ;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.util.List;
import java.awt.Color;

public class Restart extends JFrame implements ActionListener{
	
	public JLabel suggest;
	public JButton sure;
	public JButton quit;
	
	Restart() {
		setLayout(null);
		suggest = new JLabel("�����䣬�����¿�ʼ��Ϸ");
		suggest.setBounds(10, 0, 260, 50);
		this.add(suggest);
		sure = new JButton("��");
		sure.setBounds(50,60,60,40);
		sure.addActionListener(this);
		this.add(sure);
		quit = new JButton("��");
		quit.setBounds(90,100,60,40);
		quit.addActionListener(this);
		//this.add(quit);
		this.setBounds(500, 200, 180, 140);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sure) {
			System.exit(0);
		}
		if (e.getSource() == quit) {
			System.exit(0);
		}
		
	}
	
	

}
