package HCQ;

/**
 *  <code>airplane.java</code>
 *  <p>功能:
 *  
 *  <p>Copyright 软件工程1101班 2014 All right reserved.
 *  @author 薛继光 aurora.xue@gmail.com 时间 2014-1-1 下午10:10:52	
 *  @version 1.0 
 *  </br>最后修改人 无
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
