package HCQ;
import java.awt.event.*;
import java.io.IOException;

import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import OBJLoader.OBJModel;

import com.sun.jmx.snmp.SnmpPeer;
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

public class Enemy {
	public int attack;//记录攻击力
	public int life;//记录生命值
	public int size;//记录敌舰的大小
	public float radii;//记录敌舰的旋转半径
	public float depositAngle;//存储敌舰此时的旋转角度
	public float depositRadian;//存储敌舰
	public String shipName;
	public float descendNumber;;
	public float descendAngle;
	
	public OBJModel model;
	
	float shipshoot[] = new float[3];
	
	
	public void setModel(String shipname,int size,GL2 gl){
		this.size = size;
		this.shipName = shipname;
		model = new OBJModel(shipName, size, gl, true);
		
	}



}
