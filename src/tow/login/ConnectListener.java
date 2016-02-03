package tow.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import tow.Global;
import tow.MainWindow;
import tow.lobby.LobbyWindow;
import tow.net.ClientNetThread;

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
		startConnect();
		Global.mainFrame = new MainWindow();
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
	
	
}

