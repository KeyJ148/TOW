package main.player;

import java.awt.event.*;

import main.*;
import main.player.gun.*;
import main.player.armor.*;

public class Player extends Obj{
	
	public int mouseX = 0;
	public int mouseY = 0;
	
	public int nameX;
	public int nameY;
	
	private boolean controlAtack = true;//можно ли стрелять из танка
	
	private int sendStep = 0;
	
	private Game game;
	
	private Gun gun;
	private String bullet;
	private Armor armor;
	
	public Player(double x, double y, double direction, Game game){
		super(x,y,0.0,direction,1,false,Global.player_sys,game);
		this.game = game;
		this.bullet = "DefaultBullet";
		this.armor = new DefaultArmor(this,game);
		this.gun = new DefaultGun(this,game);
		this.gun.setBullet1(bullet);
		
		game.addKeyListener(new KeyAdapter(){
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
			}
			public void keyReleased(KeyEvent e){
				if (armor.getControlMotion()){
					if (e.getKeyCode() == KeyEvent.VK_W){
						armor.setSpeed(0.0);
						armor.setRunUp(false);
					}
					if (e.getKeyCode() == KeyEvent.VK_A){
						armor.setTurnLeft(false);
					}
					if (e.getKeyCode() == KeyEvent.VK_D){
						armor.setTurnRight(false);
					}
					if (e.getKeyCode() == KeyEvent.VK_S){
						armor.setSpeed(0.0);
						armor.setRunDown(false);
					}
				}
			}
		});

		game.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (controlAtack){
					if (e.getModifiers() == InputEvent.BUTTON1_MASK){
						gun.attack1();
						gun.mousePress((byte) 1);
					}
					if (e.getModifiers() == InputEvent.BUTTON3_MASK){
						gun.attack2();
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

		game.addMouseMotionListener(new MouseMotionAdapter(){
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
		s =  "0 " + Math.round(getX())
			+ " " + Math.round(getY())
			+ " " + Math.round(armor.getDirection())
			+ " " + anim
			+ " " + game.name
			+ " " + Math.round(gun.getDirection())
			+ " " + Math.round(armor.getSpeed());
		return s;
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
	
	public boolean getControlAtack(){
		return controlAtack;
	}
	
	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}
	
	public void updateChildFinal(){
		//Отрисовка ника
		nameX = (int) Math.round(getXViewCenter()-game.name.length()*3.25); // lengthChar/2
		nameY = (int) getYViewCenter()-50;
		
		//Отправка данных о игроке
		sendStep++;
		if (sendStep == Global.setting.SEND_STEP_MAX){
			sendStep = 0;
			Global.clientSend.sendData(getData());
		}
	}
}