package OceanSimulation;

import javax.media.opengl.*;

/**
 * @author XuYunhan
 *
 */

public class LightSimulation {
	private float WAVEWIDE = 2f;
	private float[] mat_specular = {0.3f,0.3f,0.3f,1.0f};//�����棩������ɫ
	private float[] mat_shininess = {1.0f};//(����)����ָ��
	private float[] mat_diffuse = {0.4f,0.4f,0.4f,1.0f};//(����)��ɢ����ɫ
	private float[] mat_emission = {0.7f,0.7f,0.7f,1.0f};//(����)������ɫ
	private float[] light_position = {1.0f,-0.3f,0.1f,0.0f};//��Դλ��
	private float[] white_light = {0.9f,0.9f,0.9f,1.0f};//���ɢ��;���ǿ��(��������Ч��ʱ�������)
	private float[] lmodel_ambient = {0.5f,0.5f,0.5f,1.0f};//��Ļ���ǿ��
	
	private float[] light1_ambient = {0.9f,0.9f,0.9f,1.0f};
	private float[] light1_light = {1.0f,1.0f,1.0f,1.0f};
	private float[] light1_specular = {1.0f,1.0f,1.0f,1.0f};
	private float[] light1_position = {1.0f,0.3f,-0.1f,1.0f};
	private float[] spot_direction = {WAVEWIDE / 2f , 99.3f , 1.5f + WAVEWIDE , 0.0f};

	public void SetLight(GL2 gl) {
		
		//gl.glClearColor(0,0,0,0);
		//gl.glShadeModel(GL2.GL_SMOOTH);
	//	float mat_specular[] = {1.0f,2.0f,1.0f,1.0f};

	      float low_shininess [] = {5.0f};

	     // gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_SPECULAR,mat_specular,0);

	     // gl.glMaterialfv(GL2.GL_FRONT,GL2.GL_SHININESS,low_shininess,0);
	      float light_specular[] ={1.0f,1.0f,1.0f,1.0f};

	    //  gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_SPECULAR,light_specular,0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_SPECULAR,mat_specular,0);//(����)��������
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_SHININESS,mat_shininess,0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_DIFFUSE,mat_diffuse,0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_EMISSION,mat_emission,0);
		
		gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_POSITION,light_position,0);//��Դλ��
		gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_DIFFUSE,white_light,0);
		
		gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_SPECULAR,white_light,0);
		gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT,lmodel_ambient,0);//ȫ�ֻ�����
		
		gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_AMBIENT,light1_ambient,0);
		gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_POSITION,light1_position,0);//��Դλ��
		gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_DIFFUSE,light1_light,0);
		gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_SPECULAR,light1_light,0);
		
		gl.glLightf(GL2.GL_LIGHT1,GL2.GL_CONSTANT_ATTENUATION,1.5f);
		gl.glLightf(GL2.GL_LIGHT1,GL2.GL_LINEAR_ATTENUATION,0.5f);
		gl.glLightf(GL2.GL_LIGHT1,GL2.GL_QUADRATIC_ATTENUATION,1.2f);
		gl.glLightf(GL2.GL_LIGHT1,GL2.GL_SPOT_CUTOFF,45.0f);
		
		gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_SPOT_DIRECTION,spot_direction,0);
		
		gl.glLightf(GL2.GL_LIGHT1,GL2.GL_SPOT_EXPONENT,2.0f);
		
		//gl.glEnable(GL2.GL_LIGHTING);//���ù���
		//gl.glEnable(GL2.GL_LIGHT0);
		//gl.glEnable(GL2.GL_LIGHT1);
		//gl.glEnable(GL2.GL_DEPTH_TEST);
		
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_COLOR_CONTROL,GL2.GL_SEPARATE_SPECULAR_COLOR);
		
	}
}
