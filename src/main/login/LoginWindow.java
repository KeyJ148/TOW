package main.login;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;

import main.setting.ConfigReader;


@SuppressWarnings("serial")
public class LoginWindow extends Frame{
	
	public final int horFrame = 400;
	public final int vertFrame = 150;
	public final String fileName = "login.properties";
	
	public String tf1Default = "";//ip
	public String tf2Default = "";//порт
	public String tf3Default = "";//ник
	public String tf4Default = "";//кол-во игроков на серве
	
	WindowMain frame;
	
	public LoginWindow(WindowMain frame, String s, int hor, int vert){
		super(s);
		setLayout(null);
		this.frame = frame;
		
		ConfigReader cr = new ConfigReader(fileName);
		tf1Default = cr.findString("IP");
		tf2Default = cr.findString("PORT");
		tf3Default = cr.findString("NICKNAME");
		tf4Default = cr.findString("SERVER_PEOPLE");
		
		Label L1 = new Label("Host ip:", Label.RIGHT);
		L1.setBounds(20, 30, 70, 25); 
		add(L1);
		
		Label L2 = new Label("You name:", Label.RIGHT);
		L2.setBounds(20, 60, 70, 25);
		add(L2);
		
		Label L3 = new Label(":", Label.CENTER);
		L3.setBounds(200, 30, 10, 25);
		add(L3);
		
		TextField tfIp = new TextField(tf1Default, 30); 
		tfIp.setBounds(100, 30, 100, 25);
		add(tfIp);
		
		TextField tf2 = new TextField(tf2Default, 30); 
		tf2.setBounds(210, 30, 50, 25);
		add(tf2); 
		
		TextField tf3 = new TextField(tf3Default,30); 
		tf3.setBounds(100, 60, 160, 25);
		add(tf3); 
		
		TextField tf4 = new TextField(tf4Default,30); 
		tf4.setBounds(225, 100, 20, 30);
		add(tf4); 
		
		Button b1 = new Button("Enter"); 
		b1.setBounds(20, 100, 100, 30);
		add(b1);
		
		Button b2 = new Button("Start"); 
		b2.setBounds(120, 100, 100, 30);
		add(b2);
		
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
		
		LoginListener ll = new LoginListener(tfIp,tf2,tf3,frame);
		tfIp.addActionListener(ll);
		tf2.addActionListener(ll);
		tf3.addActionListener(ll);
		b1.addActionListener(ll);
		
		StartServerListener ssl = new StartServerListener(tf2, tf4, ll);
		b2.addActionListener(ssl);
		tf4.addActionListener(ssl);
		
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