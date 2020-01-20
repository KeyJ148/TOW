package tow.game.client.login.gui;


import tow.engine.setting.ConfigReader;
import tow.game.client.ClientData;
import tow.game.client.login.logic.ConnectButtonListener;
import tow.game.client.login.logic.HostButtonListener;
import tow.game.client.login.logic.MapButtonListener;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {

	public static final String FRAME_NAME = "Login";
	public static final int FRAME_WIDTH = 400+7+7;//Размер окна
	public static final int FRAME_HEIGHT = 235;

	//Отображение танка
	public LoginPanel panel;
	public SpriteAWT spriteTank = new SpriteAWT("res/image/sys/sys_tank.png");

	//Стандартные настройки (из конфига)
	public String fileName = "game/login.properties";//Файл хранения настроек
	
	public int defaultSound = 100;//громкость(Проценты)
	public String defaultWidth = "";//Ширина
	public String defaultHeight = "";//Высота
	
	public String defaultName = "";//ник
	public int defaultRed = 0;//цвет танка
	public int defaultGreen = 0;
	public int defaultBlue = 0;
	
	public String defaultIP = "";//ip 
	public String defaultPort = "";//порт
	public String defaultPortHost = "";//порт

	public LoginWindow(){
		super(FRAME_NAME);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
		int wDisplay = sSize.width;
		int hDisplay = sSize.height;
		setBounds(wDisplay/2-FRAME_WIDTH/2, hDisplay/2-FRAME_HEIGHT/2, FRAME_WIDTH, FRAME_HEIGHT);

		loadFromConfig();
		createGUIElements();
		recolorTank(new Color(defaultRed, defaultGreen, defaultBlue));
		
		setResizable(false);
		setVisible(true);
	}

	public void recolorTank(Color c){
        ClientData.color = new tow.engine.image.Color(c.getRed(), c.getGreen(), c.getBlue());

        spriteTank.setDefaultImage();
		spriteTank.setColor(c);
        panel.repaint();
	}

	private void loadFromConfig(){
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
	}

	private void createGUIElements(){
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

		JButton bMap = new JButton("Map");
		bMap.setBounds(3, 167, 212, 20);
		add(bMap);


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

		addButtonColor(356, 30, 20, 20, 130, 0 , 0);
		addButtonColor(356, 50, 20, 20, 0, 130, 0);
		addButtonColor(356, 70, 20, 20, 0, 0, 130);
		addButtonColor(356, 90, 20, 20, 65, 65, 0);
		addButtonColor(356, 110, 20, 20, 65, 0, 65);

		addButtonColor(376, 30, 20, 20, 0, 65, 65);
		addButtonColor(376, 50, 20, 20, 125, 50, 0);
		addButtonColor(376, 70, 20, 20, 25, 40, 65);
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

		//Панель для отрисовки
		panel = new LoginPanel();
		panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		add(panel);

		//*********************************************
		//******************Общее**********************
		//*********************************************

		ConnectButtonListener cl = new ConnectButtonListener(tfIp, tfPort, tfName, this);
		bConnect.addActionListener(cl);
		tfIp.addActionListener(cl);
		tfPort.addActionListener(cl);

		HostButtonListener hl = new HostButtonListener(tfPortHost, tfName, this);
		bHost.addActionListener(hl);
		tfPortHost.addActionListener(hl);

		MapButtonListener ml = new MapButtonListener(this);
		bMap.addActionListener(ml);
	}
	
	private void addButtonColor(int x, int y, int w, int h, int red, int green, int blue){
		JButton buttonColor = new JButton(); 
		buttonColor.setBounds(x, y, w, h);
		Color c = new Color(red, green, blue);
		buttonColor.setBackground(c);
		buttonColor.addActionListener(new ColorButtonListener(c, this));
		add(buttonColor);
	}

	@SuppressWarnings("serial")
	class LoginPanel extends Panel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.BLACK);
			g.drawLine(217,0,217,FRAME_HEIGHT);
			g.drawLine(0,55,217,55);
			g.drawLine(0,82,217, 82);
			g.drawLine(0,164,217,164);

			spriteTank.draw(g, 292, 60);
		}
	}
	
}