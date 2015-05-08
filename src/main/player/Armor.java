package main.player;

import main.*;

public class Armor extends Obj {
	
	Game game;
	Player player;
	private double hp;
	private double hpMax;
	private double hpRegen;
	private double speedTankUp;
	private double speedTankDown;
	private double directionGunUp;
	private double directionTankUp;
	
	private int animSpeed;
	
	private boolean turnRight = false;
	private boolean turnLeft = false;
	private boolean controlMotion = true; //можно ли управлять танком
	public boolean controlMotionMouse = true; //можно ли управлять дулом
	private boolean runUp = false;//для столковений
	private boolean runDown = false;
	private boolean recoil = false;// в данынй момент танк отлетает от противника в рез. столкновения
	private boolean animOn = false;
	
	private int timer = 0; //таймер для отсёчта пройденных TPS
	private long coll_id = -1; //id объекта с которым происходит столкновение
	
	public Armor(Player player, Game game, Animation animation){
		super(player.getX(),player.getY(),0.0,player.getDirection(),0,true,animation,game);
		this.player = player;
		this.game = game;
		
		setCollObj(new String[] {"main.home.Home", "main.player.enemy.EnemyArmor"});
	}
	
	public void collReport(Obj obj){
		if (obj.getClass().getName().equals("main.home.Home")){
			setX(getXPrevious());
			setY(getYPrevious());
			setDirection(getDirectionPrevious());
		}
		if (obj.getClass().getName().equals("main.player.enemy.EnemyArmor")){
			if ((!recoil) || (obj.getId() != coll_id)){
				setX(getXPrevious());
				setY(getYPrevious());
				setDirection(getDirectionPrevious());
				controlMotion = false;
				recoil = true;
				timer = 0;
				coll_id = obj.getId();
				if (runUp){
					setSpeed(-getSpeed()/3);
					runUp = false;
					runDown = true;
				} else if (runDown){
					setSpeed(-getSpeed()/3);
					runDown = false;
					runUp = true;
				}
				if (turnLeft){
					turnLeft = false;
					turnRight = true;
				} else if (turnRight){
					turnRight = false;
					turnLeft = true;
				}
				
			}
		}
	}
	
	public void updateChildMid(){
		//для столкновений
		if (recoil){
			timer++;
			if (timer >= Game.TPS){
				recoil = false;
				turnRight = false;
				turnLeft = false;
				runUp = false;
				runDown = false;
				setSpeed(0.0);
				controlMotion = true;
				coll_id = -1;
			}
		}
		
		//для поворота
		if (recoil){
			if (turnLeft){
				setDirection(getDirection() + directionTankUp/3);
			}
			if (turnRight){
				setDirection(getDirection() - directionTankUp/3);
			}
		} else {
			if (turnLeft){
				setDirection(getDirection() + directionTankUp);
			}
			if (turnRight){
				setDirection(getDirection() - directionTankUp);
			}
		}
		
		//для анимации гусениц
		if ((getSpeed() != 0) && (!getAnimOn())){
			getAnimation().setFrameSpeed(animSpeed);
			setAnimOn(true);
		}
		if ((getSpeed() == 0) && (getAnimOn())){
			getAnimation().setFrameSpeed(-1);
			setAnimOn(false);
		}
		
		//следование player и пушки за броней
		player.setXcenter(getXcenter());
		player.setYcenter(getYcenter());
		player.getGun().setXcenter(getXcenter());
		player.getGun().setYcenter(getYcenter());
		
		//hp
		if(hp <= 0){
			Global.clientSend.send4();
			destroy();
			player.destroy();
			player.getGun().destroy();
		} else {
			if ((hp+hpRegen) > hpMax){
				hp = hpMax;
			} else {
				hp+=hpRegen;
			}
		}
	}
	
	public void setHp(double hp){
		this.hp = hp;
	}
	
	public void setHpMax(double hpMax){
		this.hpMax = hpMax;
	}
	
	public void setHpRegen(double hpRegen){
		this.hpRegen = hpRegen;
	}
	
	public void setSpeedTankUp(double speedTankUp){
		this.speedTankUp = speedTankUp;
	}
	
	public void setSpeedTankDown(double speedTankDown){
		this.speedTankDown = speedTankDown;
	}
	
	public void setDirectionGunUp(double directionGunUp){
		this.directionGunUp = directionGunUp;
	}
	
	public void setDirectionTankUp(double directionTankUp){
		this.directionTankUp = directionTankUp;
	}
	
	public void setAnimOn(boolean animOn){
		this.animOn = animOn;
	}
	
	public void setRunUp(boolean runUp){
		this.runUp = runUp;
	}
	
	public void setRunDown(boolean runDown){
		this.runDown = runDown;
	}
	
	public void setTurnRight(boolean turnRight){
		this.turnRight = turnRight;
	}
	
	public void setTurnLeft(boolean turnLeft){
		this.turnLeft = turnLeft;
	}
	
	public void setAnimSpeed(int animSpeed){
		this.animSpeed = animSpeed;
	}
	
	public double getHp(){
		return hp;
	}
	
	public double getHpMax(){
		return hpMax;
	}
	
	public double getHpRegen(){
		return hpRegen;
	}
	
	public double getSpeedTankUp(){
		return speedTankUp;
	}
	
	public double getSpeedTankDown(){
		return speedTankDown;
	}
	
	public double getDirectionGunUp(){
		return directionGunUp;
	}
	
	public double getDirectionTankUp(){
		return directionTankUp;
	}
	
	public int getAnimSpeed(){
		return animSpeed;
	}
	
	public boolean getAnimOn(){
		return animOn;
	}
	
	public boolean getControlMotion(){
		return controlMotion;
	}
	
	public boolean getControlMotionMouse(){
		return controlMotionMouse;
	}
}