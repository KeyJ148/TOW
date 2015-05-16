package main.login;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class LoginListener implements ActionListener {
	
	private TextField tf1;
	private TextField tf2;
	private TextField tf3;
	private WindowMain frame;
		
	LoginListener(TextField tf1, TextField tf2, TextField tf3, WindowMain frame){ 
		this.tf1 = tf1;
		this.tf2 = tf2;
		this.tf3 = tf3;
		this.frame = frame;
	}
	
	public void actionPerformed(ActionEvent ae){
		try{
			Socket sock = new Socket(InetAddress.getByName(tf1.getText()),Integer.parseInt(tf2.getText()));
			frame.createConnection(sock,tf3.getText());
		} catch(IOException e){
			System.out.println("Connection failed!");
			System.exit(0);
		}
	} 
}

