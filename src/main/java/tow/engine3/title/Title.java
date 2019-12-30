package tow.engine3.title;

import java.awt.*;


public class Title {

	public static final int BOLD = 0;

	public static final Color defaultColor = Color.black;
	public static final int defaultSize = 12;
	public static final int defaultFont = Font.PLAIN;

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

	public void draw(){
		//TODO LEGUI (git, gradle, demo in src: Demo->ExampleGUI)
		/*
		TrueTypeFont ttFont = FontManager.getFont(size, font);
		GL11.glLoadIdentity();
		ttFont.drawString((float) x, (float) y, str, c);*/
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