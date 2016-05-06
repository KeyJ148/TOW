package tow.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import tow.lobby.LobbyWindow;

public class HostListener implements ActionListener{

	private JTextField tfPortHost;
	private ConnectListener cl;
		
	public HostListener(JTextField tfPortHost, ConnectListener cl){ 
		this.tfPortHost = tfPortHost;
		this.cl = cl;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae){
		cl.lw.dispose();
		cl.lbw = new LobbyWindow(true, cl, tfPortHost, cl.lw.map);
	}
}
