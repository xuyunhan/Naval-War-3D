package HCQ;

public class GameFight {
	public int firstAttack;//��¼�Ҵ��Ĺ�����
	public int secondAttack;//��¼�д��Ĺ�����
	public int harmCount;//��¼�˺�ֵ
	
	//�����Ҵ��Ĺ�����
	public void setFirstAttack(int tFirstAttack) {
		this.firstAttack = tFirstAttack;
	}
	
	//���õд��Ĺ�����
	public void setSecondAttack(int tSecondAttack) {
		this.secondAttack = tSecondAttack;
	}
	
	//���ù�����
	public int harm() {
		if (firstAttack > secondAttack) {
			harmCount = 10;
			return harmCount;
		}
		else {
			harmCount = 5;
			return harmCount;
		}
		
	}

}
