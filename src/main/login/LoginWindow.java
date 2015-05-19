package main.login;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Label;
import java.awt.TextField;

import main.setting.ConfigReader;


@SuppressWarnings("serial")
public class LoginWindow extends Dialog{
	
	public final int horFrame = 400;
	public final int vertFrame = 150;
	public final String fileName = "login.properties";
	
	public String tf1Default = "";//ip
	public String tf2Default = "";//порт
	public String tf3Default = "";//ник
	
	WindowMain frame;
	
	public LoginWindow(WindowMain frame, String s, int hor, int vert){
		super(frame,s);
		setLayout(null);
		this.frame = frame;
		
		ConfigReader cr = new ConfigReader(fileName);
		tf1Default = cr.findString("ip");
		tf2Default = cr.findString("port");
		tf3Default = cr.findString("nickname");
		
		Label L1 = new Label("Host ip:", Label.RIGHT);
		L1.setBounds(20, 30, 70, 25); 
		add(L1);
		
		Label L2 = new Label("You name:", Label.RIGHT);
		L2.setBounds(20, 60, 70, 25);
		add(L2);
		
		Label L3 = new Label(":", Label.CENTER);
		L3.setBounds(200, 30, 10, 25);
		add(L3);
		
		TextField tf1 = new TextField(tf1Default, 30); 
		tf1.setBounds(100, 30, 100, 25);
		add(tf1);
		
		TextField tf2 = new TextField(tf2Default, 30); 
		tf2.setBounds(210, 30, 50, 25);
		add(tf2); 
		
		TextField tf3 = new TextField(tf3Default,30); 
		tf3.setBounds(100, 60, 160, 25);
		add(tf3); 
		
		Button b1 = new Button("Enter"); 
		b1.setBounds(100, 100, 100, 30);
		add(b1);
		
		addButtonColor(270,30,40,25,Color.RED);
		addButtonColor(310,30,40,25,Color.GREEN);
		addButtonColor(350,30,40,25,Color.BLUE);
		addButtonColor(270,55,40,25,Color.WHITE);
		addButtonColor(310,55,40,25,Color.YELLOW);
		addButtonColor(350,55,40,25,Color.ORANGE);
		addButtonColor(270,80,40,25,Color.PINK);
		addButtonColor(310,80,40,25,Color.MAGENTA);
		addButtonColor(350,80,40,25,Color.CYAN);
		addButtonColor(270,105,40,25,Color.LIGHT_GRAY);
		addButtonColor(310,105,40,25,Color.GRAY);
		addButtonColor(350,105,40,25,Color.DARK_GRAY);
		
		LoginListener ll = new LoginListener(tf1,tf2,tf3,frame);
		tf1.addActionListener(ll);
		tf2.addActionListener(ll);
		tf3.addActionListener(ll);
		b1.addActionListener(ll);
		
		setBounds(hor/2-horFrame/2,vert/2-vertFrame/2,horFrame,vertFrame);
		setVisible(true);
	}
	
	private void addButtonColor(int x, int y, int w, int h, Color c){
		Button buttonColor = new Button(); 
		buttonColor.setBounds(x, y, w, h);
		buttonColor.setBackground(c);
		buttonColor.addActionListener(new ColorListener(c,frame));
		add(buttonColor);
	}
	
}