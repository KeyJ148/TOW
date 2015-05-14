package main.net;

import java.io.IOException;

import main.GameServer;

public class ServerSend extends Thread {
	
	public GameServer gameServer;
	public int id;

	public ServerSend(GameServer gameServer,int id){
		this.gameServer = gameServer;
		this.id = id;
		start();
	}
	
	public void run(){
		try{
			String str;
			long t,numberSend;
			t = System.currentTimeMillis(); //ОТЛАДКА СЕРВЕРА
			numberSend = 0; //ОТЛАДКА СЕРВЕРА
			while (true){
				
				if (System.currentTimeMillis() > t+1000){ //ОТЛАДКА СЕРВЕРА
					t = System.currentTimeMillis(); //ОТЛАДКА СЕРВЕРА
					System.out.println("ID: " + id + "    MPS:" + numberSend); //ОТЛАДКА СЕРВЕРА
					numberSend = 0; //ОТЛАДКА СЕРВЕРА
				} //ОТЛАДКА СЕРВЕРА
				
				synchronized(gameServer.messagePack[id]) {//Защита от одновременной работы с массивом
					if (gameServer.messagePack[id].haveMessage()){//Если у игрока имеются сообщения				
						str = (String) gameServer.messagePack[id].get();//Читаем сообщение
						for(int j=0;j<gameServer.peopleMax;j++){//Отправляем сообщение всем
							if (j != id){//Кроме игрока, приславшего сообщение
								synchronized(gameServer.out[j]){
									gameServer.out[j].flush();
									gameServer.out[j].writeUTF(str);
								}
								numberSend++; //ОТЛАДКА СЕРВЕРА
							}
						}							
					}
				}
			}
		} catch (IOException e){
			System.out.println("[ERROR] Send message!");
		}
	}
}
