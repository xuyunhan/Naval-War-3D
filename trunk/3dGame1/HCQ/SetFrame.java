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
		labelC = new JLabel("����");
		labelC.setBounds(100, 30, 30, 50);
		add(labelC);
		labelI = new JLabel("�Ƿ�����������");
		labelI.setBounds(100, 90, 150, 50);
		add(labelI);
		labelR = new JLabel("��Ϸ�Ѷ�");
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
		choiceDifficulty.addItem("��");
		choiceDifficulty.addItem("һ��");
		choiceDifficulty.addItem("��");
		choiceDifficulty.addItemListener(this);
		choiceDifficulty.addActionListener(this);
		choiceDifficulty.setBounds(160, 160, 80, 30);
		add(choiceDifficulty);
		labelN = new JLabel("����");
		labelN.setBounds(100,200,60,50);
		add(labelN);
		choiceNet = new JComboBox();
		choiceNet.addItem("������");
		choiceNet.addItem("������(����)");
		choiceNet.addItem("������(�ͻ���)");
		choiceNet.addItemListener(this);
		choiceNet.addActionListener(this);
		choiceNet.setBounds(160, 210, 100, 30);
		add(choiceNet);
		saveData = new JButton("ȷ��");
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
        if (str == "��") {
        	enemyNumber = 2;
        }
        else if (str == "һ��") {
        	enemyNumber = 3;
        }
        else if (str == "��") {
        	enemyNumber = 4;
        }
        str = (String)choiceNet.getSelectedItem();
        if (str == "������") {
        	netChoice = 0;
        }
        if (str == "������(����)") {
        	netChoice = 1;
        }
        if (str == "������(�ͻ���)") {
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
