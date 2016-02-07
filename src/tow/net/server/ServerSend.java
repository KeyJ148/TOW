package tow.net.server;

import java.io.IOException;

import tow.Global;

public class ServerSend extends Thread {
	
	public GameServer gameServer;
	public int id;
	
	public volatile int x = 0;//Позиция танка
	public volatile int y = 0;//Сообщения которого пересылаем
	
	public int numberSend = 0; //Стата сервера

	public ServerSend(GameServer gameServer,int id){
		this.gameServer = gameServer;
		this.id = id;
		start();
	}
	
	public void run(){
		try{
			String str;
			boolean haveMessage;
			while (true){
				
				haveMessage = false;
				str = "synchronized ServerSend";
				
				synchronized(gameServer.messagePack[id]) {//Защита от одновременной работы с массивом
					if (gameServer.messagePack[id].haveMessage()){//Если у игрока имеются сообщения				
						str = (String) gameServer.messagePack[id].get();//Читаем сообщение
						haveMessage = true;
						
						if (Integer.parseInt(Global.parsString(str, 1)) == 0){
							x = Integer.parseInt(Global.parsString(str, 2));
							y = Integer.parseInt(Global.parsString(str, 3));
						}
					}
				}
				
				if (haveMessage){
					for(int j=0;j<gameServer.peopleMax;j++){//Отправляем сообщение всем
						if (j != id){//Кроме игрока, приславшего сообщение
							synchronized(gameServer.out[j]){
								gameServer.out[j].flush();
								gameServer.out[j].writeUTF(str);
							}
							numberSend++; //Кол-во отправленных пакетов
						}
					}							
				} else {
					if (!gameServer.maxPower)
						try {Thread.sleep(0,1);} catch (InterruptedException e) {}
				}
					
			}
		} catch (IOException e){
			GameServer.error("Send message");
		}
	}
}
