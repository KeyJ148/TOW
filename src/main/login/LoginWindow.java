package main.login;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import main.Global;
import main.setting.ConfigReader;


@SuppressWarnings("serial")
public class LoginWindow extends JFrame{
	
	public final int horFrame = 400+7+7;//Развер окна
	public final int vertFrame = 235;
	
	//Стандартные настройки
	public final String fileName = "login.properties";//Файл хранения настроек
	
	public int defaultSound = 100;//громкость(Проценты)
	public String defaultWidth = "";//Ширина
	public String defaultHeight = "";//Высота
	
	public String defaultName = "";//ник
	public int defaultRed = 255;//цвет танка
	public int defaultGreen = 255;
	public int defaultBlue = 255;
	
	public String defaultIP = "";//ip 
	public String defaultPort = "";//порт
	public String defaultPortHost = "";//порт
	
	public Color colorTank;
	
	public LoginWindow(WindowMain frame, int hor, int vert){
		super("Login");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowListener() {
            public void windowClosing(WindowEvent event){ System.exit(0);}
            public void windowOpened(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
        });
		
		setBounds(hor/2-horFrame/2,vert/2-vertFrame/2,horFrame,vertFrame);
		
		//Загрузка из конфига
		ConfigReader cr = new ConfigReader(fileName);
		defaultSound = cr.findInteger("SOUND");
		defaultWidth = cr.findString("WIDTH");
		defaultHeight = cr.findString("HEIGHT");
		
		defaultName = cr.findString("NAME");
		defaultRed = cr.findInteger("RED");
		defaultGreen = cr.findInteger("GREEN");
		defaultBlue = cr.findInteger("BLUE");
		
		defaultIP = cr.findString("IP");
		defaultPort = cr.findString("PORT");
		defaultPortHost = cr.findString("PORT_HOST");
		
		
		//*********************************************
		//***************Верхняя часть*****************
		//*********************************************
		JButton bConnect = new JButton("Connect"); 
		bConnect.setBounds(3, 5, 90, 45);
		add(bConnect);
		
		JLabel LIp = new JLabel("IP:", Label.LEFT);
		LIp.setBounds(94, 5, 20, 20);
		add(LIp);
		
		JTextField tfIp = new JTextField(defaultIP, 30); 
		tfIp.setBounds(115, 4, 100, 20);
		add(tfIp);
		
		JLabel LPort = new JLabel("Port:", Label.LEFT);
		LPort.setBounds(94, 32, 30, 20);
		add(LPort);
		
		JTextField tfPort = new JTextField(defaultPort, 30); 
		tfPort.setBounds(126, 30, 45, 20);
		add(tfPort);
		
		JButton bHost = new JButton("Host"); 
		bHost.setBounds(3, 60, 90, 20);
		add(bHost);
		
		JLabel LPortHost = new JLabel("Port:", Label.LEFT);
		LPortHost.setBounds(94, 62, 30, 20);
		add(LPortHost);
		
		JTextField tfPortHost = new JTextField(defaultPortHost, 30); 
		tfPortHost.setBounds(126, 60, 45, 20);
		add(tfPortHost);
		
		
		//*********************************************
		//***************Нижняя часть******************
		//*********************************************
		JLabel LSound = new JLabel("Sounds:", Label.LEFT);
		LSound.setBounds(3, 87, 47, 20);
		add(LSound);
		
		JSlider SSound = new JSlider(JSlider.HORIZONTAL,0,100,defaultSound);
		SSound.setBounds(51, 87, 134, 20);
		add(SSound);
				
		JTextField tfSound = new JTextField(String.valueOf(defaultSound),30); 
		tfSound.setBounds(187, 87, 28, 20);
		add(tfSound);
				
		JLabel LRes = new JLabel("Resolution:", Label.LEFT);
		LRes.setBounds(3, 114, 65, 20);
		add(LRes);
				
		JTextField tfRes1 = new JTextField(defaultWidth, 30); 
		tfRes1.setBounds(71, 112, 40, 20);
		add(tfRes1);
				
		JLabel LX = new JLabel("x", Label.LEFT);
		LX.setBounds(113, 112, 8, 25);
		add(LX);
				
		JTextField tfRes2 = new JTextField(defaultHeight, 30); 
		tfRes2.setBounds(125, 112, 40, 20);
		add(tfRes2); 
				
		JButton bSetting = new JButton("Setting controls"); 
		bSetting.setBounds(3, 142, 212, 20);
		add(bSetting);
		
		
		//*********************************************
		//***************Правая часть******************
		//*********************************************
		JTextField tfName = new JTextField(defaultName, 30); 
		tfName.setBounds(219, 4, 178, 20);
		add(tfName);
		
		
		addButtonColor(219, 30, 20, 20, 255, 0, 0);
		addButtonColor(219, 50, 20, 20, 0, 255, 0);
		addButtonColor(219, 70, 20, 20, 0, 0, 225);
		addButtonColor(219, 90, 20, 20, 255, 255, 0);
		addButtonColor(219, 110, 20, 20, 255, 0, 255);
		
		addButtonColor(239, 30, 20, 20, 0, 125, 255);
		addButtonColor(239, 50, 20, 20, 255, 125, 0);
		addButtonColor(239, 70, 20, 20, 125, 0, 255);
		addButtonColor(239, 90, 20, 20, 0, 255, 255);
		addButtonColor(239, 110, 20, 20, 125, 255, 0);
		
		addButtonColor(356, 30, 20, 20, 50, 0 , 0);
		addButtonColor(356, 50, 20, 20, 0, 50, 0);
		addButtonColor(356, 70, 20, 20, 0, 0, 50);
		addButtonColor(356, 90, 20, 20, 50, 50, 0);
		addButtonColor(356, 110, 20, 20, 50, 0, 50);
		
		addButtonColor(376, 30, 20, 20, 0, 50, 50);
		addButtonColor(376, 50, 20, 20, 125, 50, 0);
		addButtonColor(376, 70, 20, 20, 10, 30, 65);
		addButtonColor(376, 90, 20, 20, 40, 81, 75);
		addButtonColor(376, 110, 20, 20, 70, 100, 30);
		
		JSlider SRed = new JSlider(JSlider.HORIZONTAL,0,255,defaultRed);
		SRed.setBounds(219, 135, 147, 20);
		add(SRed);
		
		JTextField tfRed = new JTextField(String.valueOf(defaultRed), 30); 
		tfRed.setBounds(368, 135, 28, 20);
		tfRed.setBackground(Color.RED);
		add(tfRed);
		
		JSlider SGreen = new JSlider(JSlider.HORIZONTAL,0,255,defaultGreen);
		SGreen.setBounds(219, 155, 147, 20);
		add(SGreen);
		
		JTextField tfGreen = new JTextField(String.valueOf(defaultGreen), 30); 
		tfGreen.setBounds(368, 155, 28, 20);
		tfGreen.setBackground(Color.GREEN);
		add(tfGreen);
		
		JSlider SBlue = new JSlider(JSlider.HORIZONTAL,0,255,defaultBlue);
		SBlue.setBounds(219, 175, 147, 20);
		add(SBlue);
		
		JTextField tfBlue = new JTextField(String.valueOf(defaultBlue), 30); 
		tfBlue.setBounds(368, 175, 28, 20);
		tfBlue.setBackground(new Color(20,90,225));
		add(tfBlue);
		
		//*********************************************
		//******************Общее**********************
		//*********************************************
		
		colorTank = new Color(Integer.parseInt(tfRed.getText()), Integer.parseInt(tfGreen.getText()), Integer.parseInt(tfBlue.getText()));
		/*
		ConnectListener ll = new ConnectListener(tfIp,tf2,tf3,frame);
		tfIp.addActionListener(ll);
		tf2.addActionListener(ll);
		tf3.addActionListener(ll);
		b1.addActionListener(ll);
		
		HostListener ssl = new HostListener(tf2, tf4, ll);
		b2.addActionListener(ssl);
		tf4.addActionListener(ssl);
		*/
		
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawLine(217+7,0+30,217+7,200+30);
		g.drawLine(0+7,55+30,217+7,55+30);
		g.drawLine(0+7,82+30,217+7,82+30);
		g.drawLine(0+7,164+30,217+7,164+30);
		
		Global.player_color.setDefaultImage();
		Global.player_color.setColor(colorTank);
		Global.player_color.draw(g, 307-8, 80);
	}
	
	private void addButtonColor(int x, int y, int w, int h, int red, int green, int blue){
		JButton buttonColor = new JButton(); 
		buttonColor.setBounds(x, y, w, h);
		Color c = new Color(red, green, blue);
		buttonColor.setBackground(c);
		buttonColor.addActionListener(new ColorListener(c, this));
		add(buttonColor);
	}
	
}