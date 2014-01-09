package HCQ;

public class GameFight {
	public int firstAttack;//记录我船的攻击力
	public int secondAttack;//记录敌船的攻击力
	public int harmCount;//记录伤害值
	
	//设置我船的攻击力
	public void setFirstAttack(int tFirstAttack) {
		this.firstAttack = tFirstAttack;
	}
	
	//设置敌船的攻击力
	public void setSecondAttack(int tSecondAttack) {
		this.secondAttack = tSecondAttack;
	}
	
	//设置攻击力
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
