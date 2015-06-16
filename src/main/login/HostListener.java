package main.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import main.GameServer;
import main.Global;
import main.lobby.LobbyWindow;

public class HostListener implements ActionListener, Runnable{
	
	private JTextField tfPortHost;
	private ConnectListener cl;
	private boolean connect = false;//«апустилс€ сервер? 
		
	public HostListener(JTextField tfPortHost, ConnectListener cl){ 
		this.tfPortHost = tfPortHost;
		this.cl = cl;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae){
		new LobbyWindow(true);
		//new Thread(this).start();
		while (!connect){
			try {
				Thread.sleep(0,1);
			} catch (InterruptedException e) {
				Global.error("Wait start server");
			}
		}
		cl.startConnect();
		cl.createMainWindow();
	}

	@Override
	public void run() {
		String[] args = {tfPortHost.getText(), "1"};//количество игроков
		GameServer.fromClient(args, this);
	} 
	
	public void connect(){
		connect = true;
	}

}
