package game.client.login.logic;

import engine.io.Logger;
import game.client.ClientData;
import game.client.lobby.LobbyClient;
import game.client.login.gui.LoginWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectButtonListener implements ActionListener {
	
	public JTextField tfIp;
	public JTextField tfPort;
	public JTextField tfName;
	public LoginWindow lw;
		
	public ConnectButtonListener(JTextField tfIp, JTextField tfPort, JTextField tfName, LoginWindow lw){
		this.tfIp = tfIp;
		this.tfPort = tfPort;
		this.tfName = tfName;
		this.lw = lw;
	}

	@Override
	public void actionPerformed(ActionEvent ae){
		lw.dispose();

		String ip = tfIp.getText();
		int port = Integer.parseInt(tfPort.getText());
		String name = tfName.getText();

		if (name.indexOf(' ') != -1){
			Logger.println("Invalid name!", Logger.Type.ERROR);
			System.exit(0);
		}

		ClientData.name = name;
		new LobbyClient(ip, port, name);
	}
	
}

