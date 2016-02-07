package tow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Title {
	
	private final Color defaultColor = Color.BLACK;
	private final int defaultSize = 12;
	private final int defaultFont = Font.PLAIN;
	
	private String str;//Что написано в строке
	private int x;//Коры
	private int y;
	private Color c;//Цвет надписи
	private int size;//Размер шрифта
	private int font;//Стиль шрифта
	
	public Title(int x, int y, String str){
		this.x = x;
		this.y = y;
		this.str = str;
		this.c = defaultColor;
		this.size = defaultSize;
		this.font = defaultFont;
	}
	
	public Title(int x, int y, String str, Color c){
		this(x, y, str);
		this.c = c;
	}
	
	public Title(int x, int y, String str, int size){
		this(x, y, str);
		this.size = size;
	}
	
	public Title(int x, int y, String str, Color c, int size, int font){
		this(x, y, str);
		this.c = c;
		this.size = size;
		this.font = font;
	}
	
	public void draw(Graphics2D g){
		AffineTransform at = new AffineTransform(); 
		g.setTransform(at);
		g.setColor(c);
		g.setFont(new Font(null,font,size));
		g.drawString(str,x,y);
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String getStr(){
		return str;
	}
	
	public Color getColor(){
		return c;
	}
	
	public int getSize(){
		return size;
	}
	
	public int getFont(){
		return font;
	}
	
}
