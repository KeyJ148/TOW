package main.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import main.Global;
import main.obj.Obj;
import main.player.armor.DefaultArmor;
import main.player.gun.DefaultGun;

public class Player extends Obj{
	
	public int mouseX = 0;
	public int mouseY = 0;
	
	private boolean controlAtack = true;//����� �� �������� �� �����
	
	private int sendStep = 0;
	
	private Gun gun;
	private String bullet;
	private Armor armor;
	
	public Player(double x, double y, double direction){
		super(x,y,0.0,direction,1,false,Global.player_sys);
		this.bullet = "DefaultBullet";
		this.armor = new DefaultArmor(this);
		this.gun = new DefaultGun(this);
		this.gun.setBullet1(bullet);
		
		setColor(Global.color);
		
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

		Global.game.render.addMouseListener(new MouseAdapter(){
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
		s =  "0 " + Math.round(getX())
			+ " " + Math.round(getY())
			+ " " + Math.round(armor.getDirection())
			+ " " + anim
			+ " " + Global.name
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
	
	public void setColor(Color c){
		armor.getImage().setColor(c);
		gun.getImage().setColor(c);
	}
	
	public void updateChildFinal(){
		//��������� ���� � ��
		int nameX = (int) Math.round(getXViewCenter()-Global.name.length()*3.25); // lengthChar/2
		int nameY = (int) getYViewCenter()-50;
		Global.game.render.addTitle(nameX, nameY, Global.name);
		
		Global.game.render.addTitle(1, 16, "HP: " +  Math.round(getArmor().getHp()) + "/" + Math.round(getArmor().getHpMax()), Color.BLACK, 20, Font.BOLD);
		
		//�������� ������ � ������
		sendStep++;
		if (sendStep == Global.setting.SEND_STEP_MAX){
			sendStep = 0;
			Global.clientSend.sendData(getData());
		}
	}
}