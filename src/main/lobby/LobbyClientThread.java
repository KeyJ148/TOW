package main.lobby;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.DefaultListModel;

import main.Global;
import main.login.ConnectListener;

public class LobbyClientThread implements Runnable{
	
	public Thread thread;
	public ConnectListener cl;
	public DefaultListModel<String> listModel;
	public DataInputStream in;
	public DataOutputStream out;
	public Socket sock;
	
	public LobbyClientThread(ConnectListener cl, DefaultListModel<String> listModel){
		this.cl = cl;
		this.listModel = listModel;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		String ip = cl.tfIp.getText(); 
		int port = Integer.parseInt(cl.tfPort.getText())+1;
		try{
			Socket sock = new Socket(InetAddress.getByName(ip), port);
			InputStream inS = sock.getInputStream();
			OutputStream outS = sock.getOutputStream();
			DataInputStream in = new DataInputStream(inS);
			DataOutputStream out = new DataOutputStream(outS);
			this.in = in;
			this.out = out;
			this.sock = sock;
			connect();
		} catch(IOException e){
			Global.error("Client Lobby connect");
			System.exit(0);
		}
	}
	
	public void connect(){
		try {
			out.writeUTF(cl.tfName.getText());
			takeNames();
		} catch (IOException e) {
			Global.error("Client Lobby send name");
			System.exit(0);
		}
	}
	
	public void takeNames(){
		try {
			String name;
			do {
				name = in.readUTF();
				listModel.addElement(name);
			} while (!name.equals(" "));
			
			sock.close();
			cl.startGame();
		} catch (IOException e) {
			Global.error("Client Lobby take name");
			System.exit(0);
		}
	}
}
