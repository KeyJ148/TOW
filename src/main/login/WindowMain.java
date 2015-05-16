package main.login;

import java.io.*;
import java.net.*;
import java.awt.*;

import javax.swing.*;

import main.Game;
import main.Global;
import main.net.ClientNetThread;

@SuppressWarnings("serial")
public class WindowMain extends JFrame {
	
	public final int hor;
	public final int vert;

	public Dialog d;
	public Game game;
	public ClientNetThread clientThread;
	
	public WindowMain(String windowName, Game game){
		super(windowName);
		Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.vert = sSize.height;
		this.hor = sSize.width;
		this.game = game;
		this.d = new LoginWindow(this,"Entry",this.hor,this.vert);
	}
	
	public void createConnection(Socket sock,String name) throws IOException{
		this.d.dispose();
		
		InputStream inS = sock.getInputStream();
		OutputStream outS = sock.getOutputStream();
		DataInputStream in = new DataInputStream(inS);
		DataOutputStream out = new DataOutputStream(outS);
		this.game.in = in;
		this.game.out = out;
		if (name.equals("")){
			this.game.name = Double.toString(Math.random()); 
		} else {
			if (name.indexOf(' ') == -1){
				this.game.name = name;
			} else {
				Global.error("Invalid name!");
				System.exit(0);
			}
		}
		
		this.clientThread = new ClientNetThread(in,out,sock);
		Global.clientThread = this.clientThread;
		
		game.start();
		setVisible(true);
	}
}

