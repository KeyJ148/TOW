package main.login;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import main.Global;

public class LoginListener implements ActionListener {
	
	private TextField tfIp;
	private TextField tf2;
	private TextField tf3;
	private WindowMain frame;
		
	LoginListener(TextField tfIp, TextField tf2, TextField tf3, WindowMain frame){ 
		this.tfIp = tfIp;
		this.tf2 = tf2;
		this.tf3 = tf3;
		this.frame = frame;
	}
	
	public void actionPerformed(ActionEvent ae){
		startConnect();
	} 
	
	public void startConnect(){
		try{
			Socket sock = new Socket(InetAddress.getByName(tfIp.getText()),Integer.parseInt(tf2.getText()));
			frame.createConnection(sock,tf3.getText());
		} catch(IOException e){
			Global.error("Connection failed");
			System.exit(0);
		}
	}
}

