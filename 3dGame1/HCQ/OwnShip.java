package HCQ;

public class OwnShip {
	public int attack;
	public int life;
	public int goSpeed;
	
	// 摄像机参数
	public float g_eye[] = new float[3];
	public float g_look[] = new float[3];
	public float rad_xz;
	public float g_Angle;
	public float g_elev;
	
	// 摄像机步长
	float speed;
	
	public void seteye(float xx,float yy,float zz){
		this.g_eye[0] = xx;
		this.g_eye[1] = yy;
		this.g_eye[2] = zz;
	}

}
