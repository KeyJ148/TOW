package tow.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import tow.Global;
import tow.lobby.LobbyWindow;

public class ConnectListener implements ActionListener {
	
	public JTextField tfIp;
	public JTextField tfPort;
	public JTextField tfName;
	public LoginWindow lw;
	public LobbyWindow lbw;
		
	ConnectListener(JTextField tfIp, JTextField tfPort, JTextField tfName, LoginWindow lw){ 
		this.tfIp = tfIp;
		this.tfPort = tfPort;
		this.tfName = tfName;
		this.lw = lw;
	}
	
	public void actionPerformed(ActionEvent ae){
		lw.dispose();
		lbw = new LobbyWindow(false, this, null);
	}
	
	public void startGame(){
		lbw.dispose();
		saveData();
		//Global.mainFrame = new MainWindow();
		Global.game.start();
	}
	
	public void saveData(){
		String name = tfName.getText();
		if (name.equals("")){
			Global.name = Double.toString(Math.random()); 
		} else {
			if (name.indexOf(' ') == -1){
				Global.name = name;
				Global.ip = tfIp.getText();
				Global.port = Integer.parseInt(tfPort.getText());
			} else {
				Global.error("Invalid name!");
				System.exit(0);
			}
		}
	}
	
}

