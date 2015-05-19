package main.login;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import main.Global;
import main.net.ClientNetThread;

public class WindowMain extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public final int hor;
	public final int vert;

	public Dialog d;
	public ClientNetThread clientThread;
	
	Color c;
	
	public WindowMain(String windowName){
		super(windowName);
		Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.vert = sSize.height;
		this.hor = sSize.width;
		this.d = new LoginWindow(this,"Entry",this.hor,this.vert);
		this.c = Color.WHITE;
	}
	
	public void setColor(Color c){
		this.c = c;
	}
	
	public void createConnection(Socket sock,String name) throws IOException{
		this.d.dispose();
		
		InputStream inS = sock.getInputStream();
		OutputStream outS = sock.getOutputStream();
		DataInputStream in = new DataInputStream(inS);
		DataOutputStream out = new DataOutputStream(outS);
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
		
		this.clientThread = new ClientNetThread(in,out,sock);
		Global.clientThread = this.clientThread;
		Global.color = c;
		
		Global.game.start();
		setVisible(true);
	}
}

