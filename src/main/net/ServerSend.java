package main.net;

import java.io.IOException;

import main.GameServer;

public class ServerSend extends Thread {
	
	public GameServer gameServer;
	public int id;
	
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
				}
					
			}
		} catch (IOException e){
			System.out.println("[ERROR] Send message!");
		}
	}
}
