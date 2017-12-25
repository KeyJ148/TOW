package engine.net.client;

import engine.Global;
import game.client.TCPGameRead;

import java.util.ArrayList;

public class TCPRead extends Thread{
	
	public volatile ArrayList<String> messages = new ArrayList<String>();

	@Override
	public void run(){
		//постоянный обмен данными
		//на TCP
		String str;
		while(true){
            str = Global.tcpControl.read();
            if (Integer.parseInt(str.split(" ")[0]) != 1){//Если не пинг, то ждём update и там обрабатываем
				synchronized (messages){
					messages.add(str);
				}
			} else {//Пинг обрабатываем мгновенно
				take1(str);
			}
		}
	}
	
	public void update(){
		String str;
		synchronized(messages){
			
			for (int i=0;i<messages.size();i++){
				str = messages.get(i);

				int type = Integer.parseInt(str.split(" ")[0]);
				String mes = str.substring(str.indexOf(" ")+1);
				TCPGameRead.read(type, mes);
			}
			messages.clear();
			
		}
	}
	
	public void take1(String str){//Сервер вернул пинг
		Global.pingCheck.takePing();
	}
}
