package engine.net.client.tcp;

import engine.Global;
import engine.net.client.Message;
import game.client.NetGameRead;

import java.util.ArrayList;

public class TCPRead extends Thread{
	
	private volatile ArrayList<Message> messages = new ArrayList<>();

	@Override
	public void run(){
		//Постоянный обмен данными на TCP
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
				NetGameRead.readTCP(message);
			}

			messages.clear();
		}
	}
}
