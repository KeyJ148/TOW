package tow.player;

import tow.Global;
import tow.image.Animation;
import tow.obj.Obj;
import tow.setting.ConfigReader;

public class Armor extends Obj {
	Player player;
	private double hp;
	private double hpMax;
	private double hpRegen;//Кол-во реген хп в секунду
	private double speedTankUp;//Кол-во пикселей в секунду
	private double speedTankDown;
	private double directionGunUp;//Кол-во градусов в секунду
	private double directionTankUp;
	
	private int animSpeed;
	
	public String[] allowGun;
	
	public boolean turnRight = false;
	public boolean turnLeft = false;
	public boolean controlMotion = true; //можно ли управлять танком
	public boolean runUp = false;//для столковений
	public boolean runDown = false;
	private boolean recoil = false;// в данынй момент танк отлетает от противника в рез. столкновения
	private boolean animOn = false;
	
	private final String pathSetting = "armor/";
	
	private long timer = 0; //таймер для отсёчта пройденного времени при столкновение
	private long coll_id = -1; //id объекта с которым происходит столкновение
	
	public Armor(Player player, Animation animation){
		super(player.getX()-animation.getWidth()/2,player.getY()-animation.getHeight()/2,0.0,player.getDirection(),0,true,animation);
		this.player = player;
		
		setCollObj(new String[] {"tow.map.Home", "tow.player.enemy.EnemyArmor", "tow.player.Box"});
		
		loadData(getClass().getName());
	}
	
	public void collReport(Obj obj){
		if (obj.getClass().getName().equals("tow.player.Box")){
			Box box = (Box) obj;
			if (!box.collision){
				box.collisionPlayer();
				player.newEquipment(box.typeBox);
			}
		}
		
		if (obj.getClass().getName().equals("tow.map.Home")){
			setX(getXPrevious());
			setY(getYPrevious());
			setDirection(getDirectionPrevious());
		}
		
		if (obj.getClass().getName().equals("tow.player.enemy.EnemyArmor")){
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
	
	@Override
	public void updateChildMid(long delta){
		//для столкновений
		if (recoil){
			timer+=delta;
			if (timer > 300*1000*1000){//300 Милисекунд в наносекундах
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
				setDirection(getDirection() + (double) delta/1000000000*directionTankUp/3);//getDirection() + скорость поворота
			}
			if (turnRight){
				setDirection(getDirection() - (double) delta/1000000000*directionTankUp/3);
			}
		} else {
			if (turnLeft){
				setDirection(getDirection() + (double) delta/1000000000*directionTankUp);
			}
			if (turnRight){
				setDirection(getDirection() - (double) delta/1000000000*directionTankUp);
			}
		}
		
		//для анимации гусениц
		if ((getSpeed() != 0) && (!getAnimOn())){
			getAnimation().setFrameSpeed(animSpeed);
			setAnimOn(true);
		}
		if ((getSpeed() == 0) && (getAnimOn())){
			getAnimation().setFrameSpeed(0);
			setAnimOn(false);
		}
		
		//hp
		if(hp <= 0){
			Global.tcpSend.send4();
			destroy();
			player.destroy();
			player.getGun().destroy();
		} else {
			if ((hp+delta/1000000000*hpRegen) > hpMax){
				hp = hpMax;
			} else {
				hp+=delta/1000000000*hpRegen;
			}
		}
	}
	
	@Override
	public void updateChildFinal(long delta){//В финал, чтобы пушка не отставала от такна
		//следование player и пушки за броней
		player.setX(getX());
		player.setY(getY());
		Global.mapControl.update(player);//Необходимо делать после перемещения объекта
		player.getGun().setX(getX());
		player.getGun().setY(getY());
		Global.mapControl.update(player.getGun());//Уже прописано в obj.update, но на всякий
	}
	
	public void loadData(String fileName){
		ConfigReader cr = new ConfigReader(pathSetting + fileName.substring(fileName.lastIndexOf('.')+1) + ".properties");
		
		hpMax = cr.findDouble("HP_MAX");
		hp = hpMax;
		hpRegen = cr.findDouble("HP_REGEN");
		speedTankUp = cr.findDouble("SPEED_UP");
		speedTankDown = cr.findDouble("SPEED_DOWN");
		directionGunUp = cr.findDouble("DIRECTION_GUN_UP");
		directionTankUp = cr.findDouble("DIRECTION_TANK_UP");
		
		animSpeed = cr.findInteger("ANIMATION_SPEED");
		
		allowGun = player.parseAllow(cr.findString("ALLOW_GUN"));
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
	
	/*public boolean getControlMotionMouse(){
		return controlMotionMouse;
	}*/
}