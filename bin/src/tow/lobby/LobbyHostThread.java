package tow.lobby;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextField;

import tow.Global;

public class LobbyHostThread implements Runnable{
	
	public Thread thread;
	public JTextField tfPortHost;
	public ServerSocket serverSocket;
	public ArrayList<DataInputStream> in = new ArrayList<DataInputStream>();
	public ArrayList<DataOutputStream> out = new ArrayList<DataOutputStream>();
	public ArrayList<String> names = new ArrayList<String>();
	
	public LobbyHostThread(JTextField tfPortHost){
		this.tfPortHost = tfPortHost;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(Integer.parseInt(tfPortHost.getText())+1);
		
			while(true){
				Socket sock = serverSocket.accept();
				in.add(new DataInputStream(sock.getInputStream()));
				out.add(new DataOutputStream(sock.getOutputStream()));
				newConnect();
			}
		} catch (NumberFormatException | IOException e) {
			//Загрытие сокета вызывает это исключение
		}
	}
	
	public void newConnect(){
		try {
			String name = in.get(in.size()-1).readUTF();
			
			for (int i=0; i < out.size(); i++){
				out.get(i).writeUTF(name);
				names.add(name);
			}
			
			for (int i=0; i < out.size()-1; i++){
				out.get(out.size()-1).writeUTF(names.get(i));
			}
		} catch (IOException e) {
			Global.error("Host Lobby take name");
			System.exit(0);
		}
	}
	
	public void startServer(){
		for (int i=0; i < out.size(); i++){
			try {
				out.get(i).writeUTF(" ");
			} catch (IOException e) {
				Global.error("Host Lobby send start game");
				System.exit(0);
			}
		}
	}
	
	public void close(){
		try {
			serverSocket.close();
		} catch (IOException e) {
			Global.error("Host Lobby close accept connect");
			System.exit(0);
		}
	}
}
