package tow.game.client.login.logic;

import tow.game.client.ClientData;
import tow.game.client.lobby.LobbyServer;
import tow.game.client.login.gui.LoginWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostButtonListener implements ActionListener {

	private JTextField tfPortHost;
	public JTextField tfName;
	public LoginWindow lw;
		
	public HostButtonListener(JTextField tfPortHost, JTextField tfName, LoginWindow lw){
		this.tfPortHost = tfPortHost;
		this.tfName = tfName;
		this.lw = lw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae){
		lw.dispose();

		int port = Integer.parseInt(tfPortHost.getText());
		String name = tfName.getText();

		LoginDataChecker.checkName(name);
		LoginDataChecker.checkColor(ClientData.color);

        ClientData.name = name;
		new LobbyServer(port, name);
	}
}
