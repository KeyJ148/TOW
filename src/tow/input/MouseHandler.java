package tow.input;

import org.lwjgl.input.Mouse;

import tow.Global;
import tow.image.Sprite;
import tow.image.TextureManager;

public class MouseHandler {
	
	public Sprite cursor;
	public int mouseX;
	public int mouseY;
	public boolean mouseDown1;
	public boolean mouseDown2;
	public boolean mouseDown3;
	
	public MouseHandler(){
		//Отключение стнадартного курсора
		Mouse.setGrabbed(true);
				
		//Добавление своего курсора
		cursor = new Sprite(TextureManager.cursor_aim);
		mouseX = Mouse.getX();
		mouseY = Global.game.render.getHeight()-Mouse.getY();
	}
	
	public void update(){
		mouseX = Mouse.getX();
		mouseY = Global.game.render.getHeight()-Mouse.getY();
		mouseDown1 = Mouse.isButtonDown(0);
		mouseDown2 = Mouse.isButtonDown(1);
		mouseDown3 = Mouse.isButtonDown(2);
			
	}
	
	public void draw(){
		cursor.draw(mouseX, mouseY, 0.0);
	}
}
