package HCQ;

/**
 *  <code>airplane.java</code>
 *  <p>����:
 *  
 *  <p>Copyright �������1101�� 2014 All right reserved.
 *  @author Ѧ�̹� aurora.xue@gmail.com ʱ�� 2014-1-1 ����10:10:52	
 *  @version 1.0 
 *  </br>����޸��� ��
 */
public class airplane {

	public int life;
	public int size;
	
	public float r_x;
	public float r_y;
	public float r_z;
	
	public float r_angle;
	public float radii;
	

	public float getr_x(){
		return  (float) ((float) radii * Math.cos(r_angle));
	}
	public float getr_z(){
		return  (float) ((float) radii * Math.sin(r_angle));
	}
}
