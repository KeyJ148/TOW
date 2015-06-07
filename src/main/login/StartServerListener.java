package main.login;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.GameServer;
import main.Global;

public class StartServerListener implements ActionListener, Runnable{
	
	private TextField tf2;
	private TextField tf4;
	private LoginListener ll;
	private boolean connect = false;//Запустился сервер? 
		
	public StartServerListener(TextField tf2, TextField tf4, LoginListener ll){ 
		this.tf2 = tf2;
		this.tf4 = tf4;
		this.ll = ll;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae){
		new Thread(this).start();
		while (!connect){
			try {
				Thread.sleep(0,1);
			} catch (InterruptedException e) {
				Global.error("Wait start server");
			}
		}
		ll.startConnect();
	}

	@Override
	public void run() {
		String[] args = {tf2.getText(), tf4.getText()};
		GameServer.fromClient(args, this);
	} 
	
	public void connect(){
		connect = true;
	}

}
