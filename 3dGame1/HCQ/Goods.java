package HCQ;

/**
 *  <code>Goods.java</code>
 *  <p>����:
 *  
 *  <p>Copyright �������1101�� 2013 All right reserved.
 *  @author Ѧ�̹� aurora.xue@gmail.com ʱ�� 2013-12-31 ����08:13:40	
 *  @version 1.0 
 *  </br>����޸��� ��
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
