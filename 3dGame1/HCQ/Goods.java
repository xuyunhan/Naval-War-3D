package HCQ;

/**
 *  <code>Goods.java</code>
 *  <p>功能:
 *  
 *  <p>Copyright 软件工程1101班 2013 All right reserved.
 *  @author 薛继光 aurora.xue@gmail.com 时间 2013-12-31 下午08:13:40	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
public class Goods {

	private float Xsit;
	private float Ysit;
	private float Zsit;
	private boolean ifblood;
	public boolean ifeat;
	private float blood;
	private float power;
	
	public Goods(int num){
		Ysit = 0.5f;
		ifeat = false;
		if(num%2 != 0){
			Xsit = -num*10;
			Zsit = -num*10;
			ifblood = true;
			blood = 20;
			power = 0;
		}else{
			Xsit = num*15;
			Zsit = num*10;
			ifblood = false;
			blood = 0;
			power = 15;
		}
		
	}
	
	public float getX(){
		return Xsit;
	}
	public float getY(){
		return Ysit;
	}
	public float getZ(){
		return Zsit;
	}
	
	public boolean getifblood(){
		return ifblood;
	}
	
	public float getblood(){
		return blood;
	}
	public float getpower(){
		return power;
	}
	
}
