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
	public int attack;//��¼������
	public int life;//��¼����ֵ
	public int size;//��¼�н��Ĵ�С
	public float radii;//��¼�н�����ת�뾶
	public float depositAngle;//�洢�н���ʱ����ת�Ƕ�
	public float depositRadian;//�洢�н�
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
