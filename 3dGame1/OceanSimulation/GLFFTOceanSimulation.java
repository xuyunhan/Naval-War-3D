package OceanSimulation;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.*;

import com.sun.opengl.util.BufferUtil;

import com.sun.opengl.util.texture.Texture;


/**
 * @author XuYunhan
 *
 */

final public class GLFFTOceanSimulation {
	
	public class Simulation
	{
		private float[] _vertexsCoord;//�Ŷ�������
		private float[] _vertexsNormalCoord;//�Ŷ��㷨������
		private float[] _textureCoord;//����ͼ����
		
		//JOGL��glDrawElementsҪ����DirectBuffer
		private FloatBuffer _dirCoordBuffer;
		private FloatBuffer _dirNormalCoordBuffer;
		private FloatBuffer _dirTextureCoordBuffer;
		
		private float[] _upSpeed;
		private float[] _newY;

		private Simulation(int NumVertexs) {
			super();
			
			this._vertexsCoord = new float[NumVertexs*3];
			this._vertexsNormalCoord = new float[NumVertexs*3];
			this._textureCoord = new float[NumVertexs*4];

			_dirCoordBuffer = BufferUtil.newFloatBuffer(this._vertexsCoord.length);
			
	        _dirNormalCoordBuffer = BufferUtil.newFloatBuffer(this._vertexsNormalCoord.length);
	        
	        _dirTextureCoordBuffer = BufferUtil.newFloatBuffer(this._textureCoord.length);
	        
			this._upSpeed = new float[NumVertexs];
			this._newY = new float[NumVertexs];

		}
		
	};

	public class Height{
		public double real,imag;
	} ;

	private Texture _oceanTexture;
	private LightSimulation _lightSimulation;
	

	//����:
	final private int NUM_X_VERTEXS = 128;//���泤������
	final private int NUM_Z_VERTEXS = 128;
	final private int NUM_TOTAL_VERTEXS = NUM_X_VERTEXS*NUM_Z_VERTEXS;//�ܶ�����
	final private double VERTEXS_DISTANCE = 0.14;//��������
	final private double GRAVITY = 9.8 ; //��������
	final private double A_PHILLIPS = 0.0004;//Phillips�����׹�ʽ�ĵ�һ������
	final private double[] WIND_PHILLIPS = {30.0, 10.0} ; //��������


	//����
	private Simulation _simulationObj;
	private IntBuffer _indicesBuffer;//��������Buffer
	private double[][][] _K_WaveVectorArray;//�Ų�����������,Phillips�����׹�ʽҪ��
	
	private Height[][] _heightVar;//�Ÿ�ʱ�̵ĸ߶�
	private Height[][] _heightH0;//��h0ʱ�̵ĸ߶�
	
	public GLFFTOceanSimulation() {
		super();

		_heightVar = new Height[NUM_X_VERTEXS][NUM_Z_VERTEXS];
		_heightH0 = new Height[NUM_X_VERTEXS][NUM_Z_VERTEXS];
		for (int i = 0; i < NUM_X_VERTEXS; i++) {
			for (int j = 0; j < NUM_Z_VERTEXS; j++) {
				_heightVar[i][j] = new Height();
				_heightH0[i][j] = new Height();
			}
		}
		
		_timer = new Timer();
		_timerTask = new MyTimerTask();
		
		_K_WaveVectorArray = new double[NUM_X_VERTEXS][NUM_Z_VERTEXS][4];
		
		_lightSimulation = new LightSimulation();
	}
	
	private float _timePassed = 0.0f;
	private Timer _timer;
	private MyTimerTask _timerTask;
	public class MyTimerTask extends TimerTask{
	    public void run(){
	    	final float deltaTime = 0.02f;
	        _timePassed += deltaTime;
	        
	        UpdateHeight(deltaTime, _timePassed);
	        UpdateTextureCoord();
	        //_textrueSimulation.CalculateTextureCoord(NUM_X_OSCILLATORS, NUM_Z_OSCILLATORS, Oscillators._vertexsCoord, Oscillators._vertexsNormalCoord, Oscillators._textureCoord);
			UpdateScene(deltaTime, _timePassed);
	    }
	}
	
	public class C3dVector
	{
		private float x,y,z;
	};
	

	float Get3dVectorMod( C3dVector v)
	{//�����ά����ģ��
		return (float)(Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z));	
	}
	
	private C3dVector CrossProduct (C3dVector u, C3dVector v)
	{
		C3dVector resVector = new C3dVector();
		resVector.x = u.y*v.z - u.z*v.y;
		resVector.y = u.z*v.x - u.x*v.z;
		resVector.z = u.x*v.y - u.y*v.x;
		return resVector;
	}
	
	private C3dVector Create3dVector ( float x, float y, float z )
	{
		C3dVector tmp = new C3dVector();
		tmp.x = x;
		tmp.y = y;
		tmp.z = z;
		return tmp;
	}
		
	void Add3dVectorToVector ( C3dVector Dst, C3dVector V2)
	{
		Dst.x += V2.x;
		Dst.y += V2.y;
		Dst.z += V2.z;
	}
	
	C3dVector Normalize3dVector( C3dVector v)
	{
		C3dVector res = new C3dVector();
		float l = Get3dVectorMod(v);
		if (l == 0.0f) return Create3dVector(0.0f,0.0f,0.0f);
		res.x = v.x / l;
		res.y = v.y / l;
		res.z = v.z / l;
		return res;
	}
	
	C3dVector C3dVectorAdd (C3dVector v, C3dVector u)//����v+u
	{
		C3dVector res = new C3dVector();
		res.x = v.x+u.x;
		res.y = v.y+u.y;
		res.z = v.z+u.z;
		return res;
	}
	
	C3dVector C3dVectorMinus (C3dVector v, C3dVector u)//����v-u
	{
		C3dVector res = new C3dVector();
		res.x = v.x-u.x;
		res.y = v.y-u.y;
		res.z = v.z-u.z;
		return res;
	}
	///////////////////////////////////////////////////

	void Gauss(double[] dst)//������˹������ĺ���
	{	
		double x1 = 0, x2 = 0, w = 0;

		do {
			x1 = 2.0 * Math.random() - 1.0;
			x2 = 2.0 * Math.random() - 1.0;
			w = x1 * x1 + x2 * x2; 
		} while ( w >= 1.0 );

		w = Math.sqrt( (-2.0 * Math.log( w ) ) / w );
		dst[0] = x1 * w;
		dst[1] = x2 * w;
	}

	double Phillips(double A, double[] K, double[] wind)//Phillips�����׺���,AΪPhillips�����׳���,KΪ������,windΪ��������
	{
		double kSquare = K[0]*K[0]+K[1]*K[1];//������ƽ��,�����׹�ʽҪ��
		if ((kSquare-0.0)<0.0000001)
			return 0;

		double windSpeedSquare = wind[0]*wind[0]+wind[1]*wind[1];//���ٵ�ƽ��,�����׹�ʽҪ��
		double L = windSpeedSquare / GRAVITY;//����L,�����׹�ʽҪ��
		
		return A*(Math.exp(-1/(kSquare*L*L))/(kSquare*kSquare))*//Phillips�����׹�ʽ,���ĵ�
			((K[0]*wind[0]+K[1]*wind[1])*(K[0]*wind[0]+K[1]*wind[1]) / (kSquare*windSpeedSquare))*Math.exp(-Math.sqrt(kSquare)*0.2);
	}

	void InitH0HeightField()//ͨ��Phillips�����������ʱ�̵ĸ߶ȳ�h0
	{
		double[] gaussRandNum = new double[2];//������˹�����
		double root_of_Phillips;//Phillips����ֵ����
		double[] K_WaveVector = new double[2];//����������ˮ�˶�����������

		for (int xc = 0; xc < NUM_X_VERTEXS; xc++)
		{
			for (int zc = 0; zc < NUM_Z_VERTEXS; zc++)
			{
				K_WaveVector[0] = _K_WaveVectorArray[xc][zc][0] = 2.0*Math.PI*((double)xc - 0.5*NUM_X_VERTEXS) / (double)(NUM_X_VERTEXS*VERTEXS_DISTANCE);
				K_WaveVector[1] = _K_WaveVectorArray[xc][zc][1] = 2.0*Math.PI*((double)zc - 0.5*NUM_Z_VERTEXS) / (double)(NUM_Z_VERTEXS*VERTEXS_DISTANCE);
				_K_WaveVectorArray[xc][zc][3] = _K_WaveVectorArray[xc][zc][0]*_K_WaveVectorArray[xc][zc][0] + _K_WaveVectorArray[xc][zc][1]*_K_WaveVectorArray[xc][zc][1] ;
				_K_WaveVectorArray[xc][zc][2] = Math.sqrt(_K_WaveVectorArray[xc][zc][3]);

				Gauss(gaussRandNum);//������˹�����
							
				root_of_Phillips = Math.sqrt(Phillips(A_PHILLIPS, K_WaveVector ,WIND_PHILLIPS));

				_heightH0[xc][zc].real = (1.0)/Math.sqrt(2.0)*gaussRandNum[0]*root_of_Phillips;//��ʱ�̺���߶ȳ����ʽ,���ĵ�
				_heightH0[xc][zc].imag = (1.0)/Math.sqrt(2.0)*gaussRandNum[1]*root_of_Phillips;
			}
		}
	}

	public void CreateOcean()
	{
		float scale = 1f;//ƽ������ϵ��
		ArrayList tmpIndexList = new ArrayList();//��ʱ��������
		
		_simulationObj = new Simulation(NUM_TOTAL_VERTEXS);
		
		//tmpIndexList.clear();  //���
		for (int xc = 0; xc < NUM_X_VERTEXS; xc++) 
			for (int zc = 0; zc < NUM_Z_VERTEXS; zc++) 
			{
				_simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+0] = (float) (VERTEXS_DISTANCE*(float)(xc))*scale;
				_simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+1] = 0.0f;
				_simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+2] = (float) (VERTEXS_DISTANCE*(float)(zc))*scale;

				_simulationObj._vertexsNormalCoord[(xc+zc*NUM_X_VERTEXS)*3+0] = 0.0f;
				_simulationObj._vertexsNormalCoord[(xc+zc*NUM_X_VERTEXS)*3+1] = 1.0f;
				_simulationObj._vertexsNormalCoord[(xc+zc*NUM_X_VERTEXS)*3+2] = 0.0f;

				_simulationObj._newY[xc+zc*NUM_X_VERTEXS] = 0;
				_simulationObj._upSpeed[xc+zc*NUM_X_VERTEXS] = 0;

				if ((xc < NUM_X_VERTEXS-1) && (zc < NUM_Z_VERTEXS-1))
				{
					tmpIndexList.add(new Integer(xc+zc*NUM_X_VERTEXS));
					tmpIndexList.add(new Integer((xc+1)+zc*NUM_X_VERTEXS));
					tmpIndexList.add(new Integer((xc+1)+(zc+1)*NUM_X_VERTEXS));

					tmpIndexList.add(new Integer(xc+zc*NUM_X_VERTEXS));
					tmpIndexList.add(new Integer((xc+1)+(zc+1)*NUM_X_VERTEXS));
					tmpIndexList.add(new Integer(xc+(zc+1)*NUM_X_VERTEXS));
				}

			}

		//����������������Buffer
		_indicesBuffer = IntBuffer.allocate(tmpIndexList.size());
		for (int i = 0; i < tmpIndexList.size(); i++)
		{
			_indicesBuffer.put(i,((Integer)tmpIndexList.get(i)).intValue());
		}

		//������ʱ��������
		//tmpIndexList.clear();
		tmpIndexList = null;

		InitH0HeightField();//��ʼ����ʱ�̵ĺ���߶ȳ�
	}

	boolean Powerof2(int n,int[] m,int[] twopm)
	{
		if (n <= 1) {
			m[0] = 0;
			twopm[0] = 2;
			return(false);
		}

		m[0] = 1;
		twopm[0] = 2;

		do {
			m[0]++;
			twopm[0] *= 2;
		} while (2*twopm[0] <= n);

		if (twopm[0] != n) {
			return false;
		}
		else{
			return true;
		}
	}

	void FFT(int dir,int m,double[] x,double[] y)//���ٸ���Ҷ�任����
	{
	 	int nn,i1,k,i2,l,l1,l2;
	 	double c1,c2,tx,ty,t1,t2,u1,u2,z;
		int j = 0,i = 0;
		
		nn = 1 << m;//2��m�η�
	   
	  	i2 = nn >> 1;//nn/2
	  
	   for (i=0;i<nn-1;i++) {
	      if (i < j) {
	         tx = x[i];
	         ty = y[i];
	         x[i] = x[j];
	         y[i] = y[j];
	         x[j] = tx;
	         y[j] = ty;
	      }
	      k = i2;
	      while (k <= j) {
	         j -= k;
	         k >>= 1;
	      }
	      j += k;
	   }

	   c1 = -1.0;
	   c2 = 0.0;
	   l2 = 1;
	   for (l=0;l<m;l++) {
	      l1 = l2;
	      l2 <<= 1;
	      u1 = 1.0;
	      u2 = 0.0;
	      for (j=0;j<l1;j++) {
	         for (i=j;i<nn;i+=l2) {
	            i1 = i + l1;
	            t1 = u1 * x[i1] - u2 * y[i1];
	            t2 = u1 * y[i1] + u2 * x[i1];
	            x[i1] = x[i] - t1;
	            y[i1] = y[i] - t2;
	            x[i] += t1;
	            y[i] += t2;
	         }
	         z =  u1 * c1 - u2 * c2;
	         u2 = u1 * c2 + u2 * c1;
	         u1 = z;
	      }
	      c2 = Math.sqrt((1.0 - c1) / 2.0);
	      if (dir == 1)
	         c2 = -c2;
	      c1 = Math.sqrt((1.0 + c1) / 2.0);
	   }

	   if (dir == 1) {
	      for (i=0;i<nn;i++) {
	         x[i] /= (double)nn;
	         y[i] /= (double)nn;
	      }
	   }

	}
	
	void FFT2D(Height[][] c,int nx,int ny,int dir)//��ά���ٸ���Ҷ�任����
	{
	   int i,j;
	   int[] m = new int[1],twopm = new int[1];
	   double[] real;double[]imag;

	   //�б任
	   real = new double[nx];
	   imag = new double[nx];
	   
	   if (real == null || imag == null)
	      return;
	   if (!Powerof2(nx,m,twopm) || twopm[0] != nx)
	      return;
	   
	   for (j=0;j<ny;j++) {
	      for (i=0;i<nx;i++) {
	         real[i] = c[i][j].real;
	         imag[i] = c[i][j].imag;
	      }
	      
	      FFT(dir,m[0],real,imag);
	      
	      for (i=0;i<nx;i++) {
	         c[i][j].real = real[i];
	         c[i][j].imag = imag[i];
	      }
	   }
	   real = null;
	   imag = null;

	   //�б任
	   real = new double[ny];
	   imag = new double[ny];
	   
	   if (real == null || imag == null)
	      return;
	   if (!Powerof2(ny,m,twopm) || twopm[0] != ny)
	      return;
	   
	   for (i=0;i<nx;i++) {
	      for (j=0;j<ny;j++) {
	         real[j] = c[i][j].real;
	         imag[j] = c[i][j].imag;
	      }
	      
	      FFT(dir,m[0],real,imag);
	      
	      for (j=0;j<ny;j++) {
	         c[i][j].real = real[j];
	         c[i][j].imag = imag[j];
	      }
	   }
	   real = null;
	   imag = null;

	   return;
	}

	void UpdateHeight(float dtime, float actualTimePassed){
		double rate = 2.0 ;//Ƶ��
		double klength = 0.0;
		double virtualTimePassed = actualTimePassed*rate ;
		double wkt = 0.0 ;
		int dir = -1;
	
		for (int i=0;i<NUM_X_VERTEXS;i++){	
			for (int j=0;j<NUM_Z_VERTEXS;j++){	
				klength= _K_WaveVectorArray[i][j][2];
				wkt =  Math.sqrt(klength * GRAVITY) * virtualTimePassed;

				_heightVar[i][j].real = _heightH0[i][j].real*Math.cos(wkt) - _heightH0[i][j].imag*Math.sin(wkt) + _heightH0[NUM_X_VERTEXS - i-1][NUM_Z_VERTEXS - j-1].real*Math.cos(wkt) - _heightH0[NUM_X_VERTEXS - i-1][NUM_Z_VERTEXS - j-1].imag*Math.sin(wkt);
				_heightVar[i][j].imag = _heightH0[i][j].imag*Math.cos(wkt) + _heightH0[i][j].real*Math.sin(wkt) - _heightH0[NUM_X_VERTEXS - i-1][NUM_Z_VERTEXS - j-1].imag*Math.cos(wkt) - _heightH0[NUM_X_VERTEXS - i-1][NUM_Z_VERTEXS -j-1].real*Math.sin(wkt);
			}
		}
		
		FFT2D(_heightVar, NUM_X_VERTEXS, NUM_Z_VERTEXS, dir);//�Ժ���߶���FFT
	}

	void UpdateScene(float deltaTime, float timePassed)
	{
		int xc, zc;
		int ArrayPos ;
		
		//�µĸ߶�
		for ( xc = 0; xc < NUM_X_VERTEXS; xc++) 
		{
			for ( zc = 0; zc < NUM_Z_VERTEXS; zc++) 
			{
				ArrayPos = xc+zc*NUM_X_VERTEXS;
//				if ((xc==0) || (xc==NUM_X_OSCILLATORS-1) || (zc==0) || (zc==NUM_Z_OSCILLATORS-1))
//					;//��Ե����
//				else 
				{
					_simulationObj._newY[ArrayPos] = (float) (_heightVar[xc][zc].real * Math.pow(-1.0d,xc+zc));
				}	
			}		
		}

		//�µĸ߶ȷ��붥������
		for ( xc = 0; xc < NUM_X_VERTEXS; xc++) 
		{
			for ( zc = 0; zc < NUM_Z_VERTEXS; zc++) 
			{
				_simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+1] = _simulationObj._newY[xc+zc*NUM_X_VERTEXS];// =Oscillators[xc+zc*NUM_X_OSCILLATORS].newY;
			}
		}
		
		//�������ڶ���λ���������
		for ( xc = 0; xc < NUM_X_VERTEXS; xc++) 
		{
			for ( zc = 0; zc < NUM_Z_VERTEXS; zc++) 
			{
				C3dVector u,v;//��������
				C3dVector p1,p2;//��ʱ����

				if (xc > 0) p1 = Create3dVector(_simulationObj._vertexsCoord[(xc-1+zc*NUM_X_VERTEXS)*3+0],
										   _simulationObj._vertexsCoord[(xc-1+zc*NUM_X_VERTEXS)*3+1],
										   _simulationObj._vertexsCoord[(xc-1+zc*NUM_X_VERTEXS)*3+2]);
				else
							p1 = Create3dVector(_simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+0],
									   _simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+1],
									   _simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+2]);
				if (xc < NUM_X_VERTEXS-1) 
							p2 = Create3dVector(_simulationObj._vertexsCoord[(xc+1+zc*NUM_X_VERTEXS)*3+0],
									   _simulationObj._vertexsCoord[(xc+1+zc*NUM_X_VERTEXS)*3+1],
									   _simulationObj._vertexsCoord[(xc+1+zc*NUM_X_VERTEXS)*3+2]);
				else
							p2 = Create3dVector(_simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+0],
									   _simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+1],
									   _simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+2]);
				
				u = C3dVectorMinus(p2,p1); //�����ҵ�����
				
				if (zc > 0) p1 = Create3dVector(_simulationObj._vertexsCoord[(xc+(zc-1)*NUM_X_VERTEXS)*3+0],
						   _simulationObj._vertexsCoord[(xc+(zc-1)*NUM_X_VERTEXS)*3+1],
						   _simulationObj._vertexsCoord[(xc+(zc-1)*NUM_X_VERTEXS)*3+2]);
				else
							p1 = Create3dVector(_simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+0],
									   _simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+1],
									   _simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+2]);
				if (zc < NUM_Z_VERTEXS-1) 
							p2 = Create3dVector(_simulationObj._vertexsCoord[(xc+(zc+1)*NUM_X_VERTEXS)*3+0],
									   _simulationObj._vertexsCoord[(xc+(zc+1)*NUM_X_VERTEXS)*3+1],
									   _simulationObj._vertexsCoord[(xc+(zc+1)*NUM_X_VERTEXS)*3+2]);
				else
							p2 = Create3dVector(_simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+0],
									   _simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+1],
									   _simulationObj._vertexsCoord[(xc+zc*NUM_X_VERTEXS)*3+2]);
				
				v = C3dVectorMinus(p2,p1); //���µ��ϵ�����
				
				//����������������ó�������������λ��
				C3dVector normal = Normalize3dVector(CrossProduct(u,v));

				//�����������뷨������
				_simulationObj._vertexsNormalCoord[(xc+zc*NUM_X_VERTEXS)*3+0] =  normal.x;
				_simulationObj._vertexsNormalCoord[(xc+zc*NUM_X_VERTEXS)*3+1] =  normal.y;
				_simulationObj._vertexsNormalCoord[(xc+zc*NUM_X_VERTEXS)*3+2] =  normal.z;
				}
		}
	}

	public void Display(GL2 gl)
	{
		//FloatBuffer dirCoordBuffer = BufferUtil.newFloatBuffer(Oscillators._vertexsCoord.length);// = bbCoord.asFloatBuffer();
		_simulationObj._dirCoordBuffer.clear();
		for (int i = 0; i < _simulationObj._vertexsCoord.length; i++)
			_simulationObj._dirCoordBuffer.put(_simulationObj._vertexsCoord[i]);
		_simulationObj._dirCoordBuffer.rewind();
		
        //FloatBuffer dirNormalCoordBuffer = BufferUtil.newFloatBuffer(Oscillators._vertexsNormalCoord.length);// bbNormalCoord.asFloatBuffer();
		_simulationObj._dirNormalCoordBuffer.clear();
		for (int i = 0; i < _simulationObj._vertexsNormalCoord.length; i++)
        	_simulationObj._dirNormalCoordBuffer.put(_simulationObj._vertexsNormalCoord[i]);
        _simulationObj._dirNormalCoordBuffer.rewind();
        
        //FloatBuffer dirTextureCoordBuffer = BufferUtil.newFloatBuffer(Oscillators._textureCoord.length);// = bbCoord.asFloatBuffer();
        _simulationObj._dirTextureCoordBuffer.clear();
        for (int i = 0; i < _simulationObj._textureCoord.length; i++)
			_simulationObj._dirTextureCoordBuffer.put(_simulationObj._textureCoord[i]);
		_simulationObj._dirTextureCoordBuffer.rewind();
        //dirCoordBuffer.put(Oscillators.OscillatorCoord); 
       // dirCoordBuffer.position(0); 
      //  dirNormalCoordBuffer.put(Oscillators.OscillatorNormalCoord); 
    //    dirNormalCoordBuffer.position(0); 
      //  dirCoordBuffer.rewind();
       // dirNormalCoordBuffer.rewind();
        
//        ByteBuffer ibCoord = ByteBuffer.allocateDirect(NumIndices * 4);
//        IntBuffer dirIntBuffer = BufferUtil.newIntBuffer(Indices.capacity());
//        for (int i = 0; i < Indices.capacity(); i++)
//        	dirIntBuffer.put(Indices.get(i));
//        dirIntBuffer.rewind();
//        
////        dirIntBuffer.put(Indices);
//        dirIntBuffer.position(0);
//        int mVBO = 0;
//        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, mVBO);
//        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
// 	    gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
 	 //  gl.glEnableClientState(GL2.GL_ELEMENT_ARRAY_BUFFER);
//        gl.glEnable(GL2.GL_BLEND);//���û��
		//gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
	//	gl.glColor4f(1,1,1,0.95f);//ȫ����,45��Alpha���
	//	gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_DST_COLOR);
//			    gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		
	   // gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
	  //  gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

		
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, _simulationObj._dirCoordBuffer);
		gl.glNormalPointer(GL2.GL_FLOAT, 0, _simulationObj._dirNormalCoordBuffer);
		
		//gl.glIndexPointer(GL2.GL_UNSIGNED_INT, 0, dirIntBuffer);
		//gl.glPointSize(10f);
		
		
		
//		gl.glBegin(GL2.GL_POINTS);
//		for (int i = 0; i < NumIndices; i++) {
//			gl.glArrayElement(Indices.get(i));
//		}
//		gl.glEnd();
		//gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, _indicesBuffer.capacity());
		//��Ⱦ��ƽ��
		gl.glTexCoordPointer(4, GL2.GL_FLOAT, 0, _simulationObj._dirTextureCoordBuffer);
		_oceanTexture.bind();
		
		gl.glTranslatef(60, -2, -100);
		for (int i = 0;i < 10; i++) {
			for (int j = 0;j < 10;j++) {
				gl.glDrawElements(	GL2.GL_TRIANGLES, //��ͼģʽ
						_indicesBuffer.capacity(),//��������
						GL2.GL_UNSIGNED_INT, //��������Buffer����
						_indicesBuffer);//����Buffer
				gl.glTranslatef(0, 0, 15);
		}
			gl.glTranslatef(-15, 0, -150);
		}
	
			
		//_lightSimulation.SetLight(gl);
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_COLOR_CONTROL,GL2.GL_SEPARATE_SPECULAR_COLOR);
	}
	
	void UpdateTextureCoord() {
		int texIndex,vertexIndex;
		final float distance = 7.0f;
		final float WAVEWIDE = 100000;
		//final float delta3 = 1f;
		
		for (int x = 0; x < NUM_X_VERTEXS-1; x++) {
			for (int z = 0; z < NUM_Z_VERTEXS-1; z++) {
				vertexIndex = (z+x*NUM_X_VERTEXS)*3;
				texIndex = (z+x*NUM_X_VERTEXS)*4;
				
				_simulationObj._textureCoord[texIndex]=(_simulationObj._vertexsCoord[vertexIndex]+distance*_simulationObj._vertexsNormalCoord[vertexIndex]
				                           /_simulationObj._vertexsNormalCoord[vertexIndex+1])/WAVEWIDE;//texcoord_x
				
				_simulationObj._textureCoord[texIndex+1]=(_simulationObj._vertexsCoord[vertexIndex+2]+distance*_simulationObj._vertexsNormalCoord[vertexIndex+2]
				                           /_simulationObj._vertexsNormalCoord[vertexIndex+1])/((WAVEWIDE)*1.5f);//texcoord_z
				
				_simulationObj._textureCoord[texIndex+2]=(_simulationObj._vertexsCoord[vertexIndex+3]+distance*_simulationObj._vertexsNormalCoord[vertexIndex+3]
				                           /_simulationObj._vertexsNormalCoord[vertexIndex+4])/WAVEWIDE;//texcoord_x
				
				_simulationObj._textureCoord[texIndex+3]=(_simulationObj._vertexsCoord[vertexIndex+5]+distance*_simulationObj._vertexsNormalCoord[vertexIndex+5]
				                           /_simulationObj._vertexsNormalCoord[vertexIndex+4])/((WAVEWIDE)*1.5f);//texcoord_z
			}
		}
	}
	
	public void Init(GL2 gl) {
	    CreateOcean();
	    
	    _lightSimulation.SetLight(gl);
	    
	    gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
	    gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
	    gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

		gl.glEnable(GL2.GL_DEPTH_TEST);

		_oceanTexture = TextureLoader.load("data/���/��back - ����.bmp");
		
		_timer.schedule(_timerTask, 0, 100);
	}
	
}
