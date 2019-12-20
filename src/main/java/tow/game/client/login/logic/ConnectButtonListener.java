package tow.game.client.login.logic;

import tow.game.client.ClientData;
import tow.game.client.lobby.LobbyClient;
import tow.game.client.login.gui.LoginWindow;

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

		LoginDataChecker.checkName(name);
		LoginDataChecker.checkColor(ClientData.color);

		ClientData.name = name;
		new LobbyClient(ip, port, name);
	}
	
}

