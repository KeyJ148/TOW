package engine.net.client;

import engine.Global;
import game.client.TCPGameRead;

import java.util.ArrayList;

public class TCPRead extends Thread{
	
	public volatile ArrayList<Message> messages = new ArrayList<>();

	@Override
	public void run(){
		//постоянный обмен данными
		//на TCP
		String str;
		while(true){
            str = Global.tcpControl.read();

			int type = Integer.parseInt(str.split(" ")[0]);
			String data = str.substring(str.indexOf(" ")+1);
			long timeReceipt = System.nanoTime();
            Message message = new Message(type, data, timeReceipt);

			synchronized (messages){
				messages.add(message); //Ждём update и там обрабатываем
			}
		}
	}
	
	public void update(){
		synchronized(messages){
			for (int i=0;i<messages.size();i++){
				Message message = messages.get(i);
				TCPGameRead.read(message);
			}

			messages.clear();
		}
	}
}
