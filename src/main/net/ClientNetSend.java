package main.net;

import java.io.DataOutputStream;
import java.io.IOException;

import main.Global;

public class ClientNetSend extends Thread{
	
	public DataOutputStream out;
	
	public ClientNetSend(DataOutputStream out){
		this.out = out;
		Global.clientSend = this;
	}
	
	public void sendData(String str){
		try{
			this.out.writeUTF(str);
		} catch (IOException e){
			System.out.println("Error internet message! (Send)");
		}
	}
}