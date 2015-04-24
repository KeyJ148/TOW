package main.login;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import main.WindowMain;

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

