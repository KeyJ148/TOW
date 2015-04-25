package main.net;

import java.io.DataOutputStream;
import java.io.IOException;

import main.Game;
import main.Global;

public class ClientNetSend extends Thread{
	
	public DataOutputStream out;
	public Game game;
	
	public ClientNetSend(DataOutputStream out, Game game){
		this.out = out;
		this.game = game;
		Global.clientSend = this;
	}
	
	public void sendData(String str){
		try{
			this.out.writeUTF(str);
		} catch (IOException e){
			System.out.println("[ERROR] Send internet message");
		}
	}
	
	public void send4(){//Танк игрока уничтожен
		sendData("4 " + game.name);
		System.out.println("send4");
	}
}