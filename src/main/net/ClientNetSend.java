package main.net;

import java.io.*;

import main.Game;
import main.Global;
import main.player.Player;

public class ClientNetSend extends Thread{
	
	public DataOutputStream out;
	public Game game;
	
	public ClientNetSend(Game game, DataOutputStream out){
		this.out = out;
		this.game = game;
		Global.clientSend = this;
		start();
	}
	
	public void sendData(String str){
		try{
			this.out.writeUTF(str);
		} catch (IOException e){
			System.out.println("Error internet message! (Send)");
		}
	}
	
	public void run(){
		try{
			String str;
			Player player = (Player) Global.player;
			while (true){
				Thread.sleep(0);
				str = player.getData();
				this.out.writeUTF(str);
			}
		} catch (IOException e){
			System.out.println("Error internet message! (Const send)");
		} catch (InterruptedException e){
			System.out.println("Thread error");
		}
	}
}