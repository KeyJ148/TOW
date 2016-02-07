package tow.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import java.util.Vector;

import tow.Global;
import tow.obj.Obj;
import tow.player.armor.DefaultArmor;
import tow.player.armor.ElephantArmor;
import tow.player.armor.FortifiedArmor;
import tow.player.gun.BigGun;
import tow.player.gun.DefaultGun;
import tow.player.gun.DoubleGun;
import tow.player.gun.PowerGun;
import tow.setting.ConfigReader;

public class Player extends Obj{
	
	public int mouseX = 0;
	public int mouseY = 0;
	
	public boolean takeArmor = true;//���������� �� ������ ������������ �����
	public boolean takeGun = true;
	public boolean takeBullet = true;
	public boolean takeHealth = true;
	
	private boolean controlAtack = true;//����� �� �������� �� �����
	
	private long lastSend = 0;//����� � ��������� ��������
	
	private Gun gun;
	private String bullet;
	private Armor armor;
	
	public Player(double x, double y, double direction){
		super(x,y,0.0,direction,1,false,Global.player_sys);
		this.bullet = "DefaultBullet";
		this.armor = new DefaultArmor(this);
		this.gun = new DefaultGun(this);
		setColor();
		
		Global.game.render.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if (armor.getControlMotion()){
					if (e.getKeyCode() == KeyEvent.VK_W){
						armor.setSpeed(armor.getSpeedTankUp());
						armor.setRunUp(true);
						armor.setRunDown(false);
					}
					if (e.getKeyCode() == KeyEvent.VK_A){
						armor.setTurnLeft(true);
						armor.setTurnRight(false);
					}
					if (e.getKeyCode() == KeyEvent.VK_D){
						armor.setTurnRight(true);
						armor.setTurnLeft(false);
					}
					if (e.getKeyCode() == KeyEvent.VK_S){
						armor.setSpeed(armor.getSpeedTankDown());
						armor.setRunDown(true);
						armor.setRunUp(false);
					}
				}
				
				//������� ������� � ���������� �� ������ ������
				if (e.getKeyCode() == KeyEvent.VK_1){
					if (takeArmor){
						takeArmor = false;
					} else {
						takeArmor = true;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_2){
					if (takeGun){
						takeGun = false;
					} else {
						takeGun = true;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_3){
					if (takeBullet){
						takeBullet = false;
					} else {
						takeBullet = true;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_4){
					if (takeHealth){
						takeHealth = false;
					} else {
						takeHealth = true;
					}
				}
			}
			public void keyReleased(KeyEvent e){
				if (armor.getControlMotion()){
					if (e.getKeyCode() == KeyEvent.VK_W){
						if (armor.getSpeed() > 0) armor.setSpeed(0.0);//������� ��� �������: W press; S press; W release;
						armor.setRunUp(false);
					}
					if (e.getKeyCode() == KeyEvent.VK_A){
						armor.setTurnLeft(false);
					}
					if (e.getKeyCode() == KeyEvent.VK_D){
						armor.setTurnRight(false);
					}
					if (e.getKeyCode() == KeyEvent.VK_S){
						if (armor.getSpeed() < 0) armor.setSpeed(0.0);
						armor.setRunDown(false);
					}
				}
			}
		});

		Global.game.render.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (controlAtack){
					if (e.getModifiers() == InputEvent.BUTTON1_MASK){
						gun.mousePress((byte) 1);
					}
					if (e.getModifiers() == InputEvent.BUTTON3_MASK){
						gun.mousePress((byte) 2);
					}
				}
			}
			public void mouseReleased(MouseEvent e){
				if (e.getModifiers() == InputEvent.BUTTON1_MASK){
					gun.mouseReleas((byte) 1);
				}
				if (e.getModifiers() == InputEvent.BUTTON3_MASK){
					gun.mouseReleas((byte) 2);
				}
			}
		});

		Global.game.render.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e){
				if (armor.getControlMotionMouse()){
					mouseX = e.getX();
					mouseY = e.getY();
				}
			}
			
			public void mouseDragged(MouseEvent e){
				if (armor.getControlMotionMouse()){
					mouseX = e.getX();
					mouseY = e.getY();
				}
			}
		});
	}
	
	public String getData(){
		String s;
		int anim;
		if (armor.getAnimOn()){
			anim = armor.getAnimSpeed();
		} else {
			anim = -1;
		}
		s = Math.round(getX())
			+ " " + Math.round(getY())
			+ " " + Math.round(armor.getDirection())
			+ " " + anim
			+ " " + Global.name
			+ " " + Math.round(gun.getDirection())
			+ " " + Math.round(armor.getSpeed());
		return s;
	}
	
	public void setColor(){
		setColorArmor();
		setColorGun();
	}
	
	public void setColorArmor(){
		armor.getImage().setColor(Global.color);
	}
	
	public void setColorGun(){
		gun.getImage().setColor(Global.color);
	}
	
	public void newEquipment(int typeBox){
		switch (typeBox){
			case 0: if (takeArmor) newArmor(); break;
			case 1: if (takeGun) newGun(); break;
			case 2: if (takeBullet) newBullet(); break;
			case 3: if (takeHealth) getArmor().setHp(getArmor().getHp() + getArmor().getHpMax()*0.4); break;
		}
	}
	
	public void newArmor(){
		boolean runUp = armor.runUp;
		boolean runDown = armor.runDown;
		boolean turnRight = armor.turnRight;
		boolean turnLeft = armor.turnLeft;
		
		String armorNameNow = getArmor().getClass().getName();
		armorNameNow = armorNameNow.substring(armorNameNow.lastIndexOf(".")+1);
		
		Random rand = new Random();
		Armor newArmor;
		String newArmorName;
		while (true){
			newArmorName = getGun().allowArmor[rand.nextInt(getGun().allowArmor.length)];
			if (!newArmorName.equals(armorNameNow)) break;
		}
		switch(newArmorName){
			case "DefaultArmor": newArmor = new DefaultArmor(this); break;
			case "FortifiedArmor": newArmor = new FortifiedArmor(this); break;
			case "ElephantArmor": newArmor = new ElephantArmor(this); break;
			default: newArmor = new DefaultArmor(this); Global.error("Not find new armor name"); break;
		}
		
		double lastArmorDirection = getArmor().getDirection();
		double lastArmorHpPercent = getArmor().getHp()/getArmor().getHpMax();
		getArmor().destroy();
		setArmor(newArmor);
		getArmor().setDirection(lastArmorDirection);
		getArmor().setHp(lastArmorHpPercent * getArmor().getHpMax());
		getArmor().setRunUp(runUp);
		getArmor().setRunDown(runDown);
		getArmor().setTurnRight(turnRight);
		getArmor().setTurnLeft(turnLeft);
		if (runUp) getArmor().setSpeed(getArmor().getSpeedTankUp());
		if (runDown) getArmor().setSpeed(getArmor().getSpeedTankDown());
		setColorArmor();
		String armorName = getArmor().getClass().getName();
		Global.tcpSend.send14(armorName.substring(armorName.lastIndexOf(".")+1));
	}
	
	public void newGun(){
		String gunNameNow = getGun().getClass().getName();
		gunNameNow = gunNameNow.substring(gunNameNow.lastIndexOf(".")+1);
		
		Random rand = new Random();
		String newGunName;
		String[] allowGunForBullet;
		boolean end = false;
		do{
			while (true){
				newGunName = getArmor().allowGun[rand.nextInt(getArmor().allowGun.length)];
				if (!newGunName.equals(gunNameNow)) break;
			}
			ConfigReader cr = new ConfigReader(Bullet.pathSetting + getBullet() + ".properties");
			allowGunForBullet = parseAllow(cr.findString("ALLOW_GUN"));
			for (int i=0;i<allowGunForBullet.length;i++){
				if (newGunName.equals(allowGunForBullet[i])){
					end = true;
				}
			}
		} while(!end);
		
		Gun newGun;
		switch(newGunName){
			case "DefaultGun": newGun = new DefaultGun(this); break;
			case "DoubleGun": newGun = new DoubleGun(this); break;
			case "BigGun": newGun = new BigGun(this); break;
			case "PowerGun": newGun = new PowerGun(this); break;
			default: newGun = new DefaultGun(this); Global.error("Not find new gun name"); break;
		}
		
		
		double lastGunDirection = getGun().getDirection();
		getGun().destroy();
		setGun(newGun);
		getGun().setDirection(lastGunDirection);
		setColorGun();
		
		String gunName = getGun().getClass().getName();
		Global.tcpSend.send15(gunName.substring(gunName.lastIndexOf(".")+1));
	}
	
	public void newBullet(){
		Random rand = new Random();
		String newBullet;
		while (true){
			newBullet = getGun().allowBullet[rand.nextInt(getGun().allowBullet.length)];
			if (!newBullet.equals(bullet)) break;
		}
		bullet = newBullet;
	}
	
	@Override
	public void updateChildFinal(long delta){
		//��������� ���� � ��
		int nameX = (int) Math.round(getXViewCenter()-Global.name.length()*3.25); // lengthChar/2
		int nameY = (int) getYViewCenter()-50;
		Global.game.render.addTitle(nameX, nameY, Global.name);
		
		Global.game.render.addTitle(1, 16, "HP: " +  Math.round(getArmor().getHp()) + "/" + Math.round(getArmor().getHpMax()), Color.BLACK, 20, Font.BOLD);
		
		//�������� ������ � ������
		lastSend += delta;
		if (lastSend > Global.setting.SEND_MILLIS*1000000){
			lastSend = 0;
			Global.tcpSend.send0(getData());
		}
	}
	
	public String[] parseAllow(String allowString){
		Vector<String> allowVector = new Vector<String>();
		
		boolean end = false;
		int i = 0;
		String name;
		do{
			i++;
			name = Global.parsString(allowString,i);
			allowVector.add(name);
			if (name.equals(allowString.substring(allowString.lastIndexOf(" ")+1))){
				end = true;
			}
		} while(!end);
		
		return allowVector.toArray(new String[allowVector.size()]);
	}
	
	public Gun getGun(){
		return gun;
	}
	
	public String getBullet(){
		return bullet;
	}
	
	public Armor getArmor(){
		return armor;
	}
	
	public void setGun(Gun gun) {
		this.gun = gun;
	}

	public void setBullet(String bullet) {
		this.bullet = bullet;
	}

	public void setArmor(Armor armor) {
		this.armor = armor;
	}
	
	public boolean getControlAtack(){
		return controlAtack;
	}
	
	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}
}