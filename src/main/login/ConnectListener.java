package main.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import main.Global;
import main.Render;
import main.lobby.LobbyWindow;
import main.net.ClientNetThread;

public class ConnectListener implements ActionListener {
	
	private JTextField tfIp;
	private JTextField tfPort;
	private JTextField tfName;
	private LoginWindow lw;
		
	ConnectListener(JTextField tfIp, JTextField tfPort, JTextField tfName, LoginWindow lw){ 
		this.tfIp = tfIp;
		this.tfPort = tfPort;
		this.tfName = tfName;
		this.lw = lw;
	}
	
	public void actionPerformed(ActionEvent ae){
		new LobbyWindow(false);
		//lw.dispose();
		//startConnect();
		//createMainWindow();
	} 
	
	public void startConnect(){
		String name = tfName.getText();
		if (name.equals("")){
			Global.name = Double.toString(Math.random()); 
		} else {
			if (name.indexOf(' ') == -1){
				Global.name = name;
			} else {
				Global.error("Invalid name!");
				System.exit(0);
			}
		}
		
		Global.clientThread = new ClientNetThread(tfIp.getText(), Integer.parseInt(tfPort.getText()));
	}
	
	public void createMainWindow(){
		Render redner = Global.game.render;
		redner.setPreferredSize(new Dimension(Global.setting.WIDTH_SCREEN, Global.setting.HEIGHT_SCREEN));
		
		JFrame frame = new JFrame(Global.setting.WINDOW_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(redner, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		
		Global.game.start();
		frame.setVisible(true);
		
		if (Global.setting.DEBUG_CONSOLE) System.out.println("Window create.");
	}
}

