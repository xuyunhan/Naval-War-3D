package HCQ;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.util.List;
import java.awt.Color;

public class SetFrame extends JFrame implements ItemListener,ActionListener{
	
	public JComboBox choiceSpeed;
	public JComboBox choiceDifficulty;
	public JComboBox choiceNet;
	public JCheckBox isPlay;
	public JButton saveData;
	public JLabel labelC;
	public JLabel labelI;
	public JLabel labelR;
	public JLabel labelN;
	public int speed;
	public int enemyNumber;
	public int netChoice;
	public boolean playMusic;
	
	SetFrame() {
		setLayout(null);
		labelC = new JLabel("航速");
		labelC.setBounds(100, 30, 30, 50);
		add(labelC);
		labelI = new JLabel("是否开启背景音乐");
		labelI.setBounds(100, 90, 150, 50);
		add(labelI);
		labelR = new JLabel("游戏难度");
		labelR.setBounds(100,150,60,50);
		add(labelR);
		choiceSpeed = new JComboBox();
		choiceSpeed.addItem("10");
		choiceSpeed.addItem("20");
		choiceSpeed.addItem("30");
		choiceSpeed.addItem("40");
		choiceSpeed.addItemListener(this);
		choiceSpeed.addActionListener(this);
		choiceSpeed.setBounds(160, 40, 80, 30);
		add(choiceSpeed);
		isPlay= new JCheckBox();
		isPlay.setBounds(210,100,30,30);
		isPlay.addItemListener(this);
		add(isPlay);
		choiceDifficulty = new JComboBox();
		choiceDifficulty.addItem("简单");
		choiceDifficulty.addItem("一般");
		choiceDifficulty.addItem("难");
		choiceDifficulty.addItemListener(this);
		choiceDifficulty.addActionListener(this);
		choiceDifficulty.setBounds(160, 160, 80, 30);
		add(choiceDifficulty);
		labelN = new JLabel("网络");
		labelN.setBounds(100,200,60,50);
		add(labelN);
		choiceNet = new JComboBox();
		choiceNet.addItem("单机版");
		choiceNet.addItem("局域网(主机)");
		choiceNet.addItem("局域网(客户机)");
		choiceNet.addItemListener(this);
		choiceNet.addActionListener(this);
		choiceNet.setBounds(160, 210, 100, 30);
		add(choiceNet);
		saveData = new JButton("确定");
		saveData.setBounds(150,260,70,30);
		saveData.addActionListener(this);
		add(saveData);
		this.setBounds(500, 200, 380, 340);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
	}

	public void actionPerformed(ActionEvent e) {
        String str = (String)choiceSpeed.getSelectedItem();  
        speed = Integer.parseInt(str);
        str = (String)choiceDifficulty.getSelectedItem();
        if (str == "简单") {
        	enemyNumber = 2;
        }
        else if (str == "一般") {
        	enemyNumber = 3;
        }
        else if (str == "难") {
        	enemyNumber = 4;
        }
        str = (String)choiceNet.getSelectedItem();
        if (str == "单机版") {
        	netChoice = 0;
        }
        if (str == "局域网(主机)") {
        	netChoice = 1;
        }
        if (str == "局域网(客户机)") {
        	netChoice = 2;
        }
        if (e.getSource() == saveData) {
        	this.dispose();
        }
	}

	public void itemStateChanged(ItemEvent e) {
		if (isPlay.isSelected()) {
			playMusic = true;
		}
		else {
			playMusic = false;
		}
		
	}
	
	public int returnSpeed() {
		return speed;
	}
	
	public int returnEnemyNumber() {
		return enemyNumber;
	}
	
	

}
