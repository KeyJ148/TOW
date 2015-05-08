package main.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import main.GameServer;
import main.Global;

public class ServerNetThread extends Thread{
	
	public String name;
	public boolean mapDownAll = false;//все ли скачали карту?
	
	private GameServer gameServer;
	
	private int id; //номер соединения в массиве в gameServer
	private int peopleMax;
	
	public ServerNetThread(GameServer gameServer, int id, int peopleMax) throws IOException{

		this.gameServer = gameServer;
		this.id = id;
		this.peopleMax = peopleMax;
		
		start();
	}
	
	public void run(){
		
		mapLoading();
		
		conMessTake();
	}
	
	public void mapLoading(){
		//если это первый загрузившийся поток -- создать проверщик скаваний
		gameServer.checkMapDownload();
		
		//отправка карты
		System.out.println("Loading map start.");
		String pathFull = this.gameServer.pathFull;
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(pathFull));
			String s;
			
			this.gameServer.out[id].writeUTF("6 ");//Начата отправка карты
			while (true){ 
				s = fileReader.readLine();
				if (s == null){
					break;
				}
				writeMap(s);
			}
			this.gameServer.out[id].writeUTF("8 ");//Закончена отправка карты
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Loading map end.");		
				
		//Считывание имени игрока и отправка данных
		//this.name = downloadNick();
		try {
			this.name = gameServer.in[id].readUTF();
		} catch (IOException e) {
		}
		System.out.println("Nickname: " + name);
		writeMap(this.gameServer.tankX[id] + " " + this.gameServer.tankY[id]);
		writeMap(this.peopleMax + " ");
		
		this.gameServer.connect[id] = true;
		do{
		}while(!mapDownAll);
	}
	
	public String downloadNick(){
		try {
			String s;
			do {
				s = this.gameServer.in[id].readUTF();
			}while(Integer.parseInt(Global.linkCS.parsString(s,1)) != -2);
			return s.substring(2);
		} catch (IOException e) {
			System.out.println("[ERROR] Method for download nickname");
			return "";
		}
	}

	private void writeMap(String s) {
		try {
			this.gameServer.out[id].writeUTF("7 " + s);
		} catch (IOException e) {
			System.out.println("[ERROR] Method for writeMap");
		}
	}

	public void conMessTake(){
		//Постоянный обмен данными (на TCP)
		//Только после подключения всех игроков
		String str;
		try{
			while (true){
				str = this.gameServer.in[id].readUTF();
				if (Integer.parseInt(Global.linkCS.parsString(str, 1)) >= 0){//Если сообщение для клиента
					synchronized(this.gameServer.messagePack[id]) {//Защита от одновременной работы с массивом
						this.gameServer.messagePack[id].add(str);
					}
				} else {//Если сообщение для сервера
					switch (Integer.parseInt(Global.linkCS.parsString(str, 1))){
						case -1: take1(); break;
						case -2: take2(str); break;
					}
				}
			}
		} catch (IOException e){
			System.out.println("[ERROR] Take message!");
			if ((gameServer.disconnect+1) == peopleMax){
				System.out.println("All user disconnect!");
				System.exit(0);
			} else {
				gameServer.disconnect++;
			}
		}
	}
	
	public void take1(){//Клиент готов к приёму карты
		gameServer.messagePack[id].clear();
		mapLoading();
	}
	
	public void take2(String str){//Клиент пингует сервер
		try {
			gameServer.out[id].writeUTF("9 " + Global.linkCS.parsString(str, 2));
		} catch (IOException e) {
			System.out.println("[ERROR] Check ping");
		}
	}
	
}