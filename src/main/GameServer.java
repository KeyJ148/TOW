package main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import main.net.CheckMapLoad;
import main.net.LinkCS;
import main.net.MessagePack;
import main.net.ServerNetThread;
import main.net.ServerSend;
import main.setting.SettingStorage;

public class GameServer {
	
	public final String PATH_MAP = "map";
	public final String PATH_IMAGE = "image";  

	//Присоединение клиентов
	public int port;
	public int peopleMax;
	public int peopleNow;
	public int disconnect;//Кол-во отключённых игроков, не совмещать с peopleNow
	//Отправка данных
	public ServerNetThread[] serverThread;
	public DataInputStream[] in;
	//Приём данных
	public ServerSend[] serverSend;
	public DataOutputStream[] out;
	//Хранение данных
	public volatile MessagePack[] messagePack;
	//Инициализация карты
	public String pathFull; //Путь к карте
	public int widthMap;//Размеры карты
	public int heightMap;
	//Генерация танков
	public volatile boolean tankGenComplite = false;
	public int[] tankX;//Координаты танков игроков
	public int[] tankY;
	//Проверка загрузки карты
	public volatile boolean[] connect;//Метка, устанавливаемая клиентским потоком, о том что игрок скачал карту
	public CheckMapLoad cml;//Объект-поток для проверки загрузки карты всеми игроками (Проверка меток)
	
	public GameServer() throws IOException{
		BufferedReader bReader = new BufferedReader (new InputStreamReader(System.in));
		int port;
		int peopleMax;
		String str;
		
		Global.setting = new SettingStorage();
		Global.setting.initFromFile();
		Global.linkCS = new LinkCS();
		Global.linkCS.initSprite();
		
		System.out.print("Port (Default 25566): ");
		str = bReader.readLine();
		if (str.equals("")){
			port = 25566;
		} else {
			port = Integer.parseInt(str);
		}
		this.port = port;
		
		System.out.print("Max people (Default 1): ");
		str = bReader.readLine();
		if (str.equals("")){
			peopleMax = 1;
		} else {
			peopleMax = Integer.parseInt(str);
		}
		this.peopleMax = peopleMax;
		
		
		this.serverThread = new ServerNetThread[peopleMax];
		this.in = new DataInputStream[peopleMax];
		this.serverSend = new ServerSend[peopleMax];
		this.out = new DataOutputStream[peopleMax];
		this.messagePack = new MessagePack[peopleMax];
		this.tankX = new int[peopleMax];
		this.tankY = new int[peopleMax];
		this.connect = new boolean[peopleMax];
		this.disconnect = 0;
		
		ServerSocket ServerSocket = new ServerSocket(port);
		this.peopleNow = 0;
		
		genTank();
		System.out.println("Server started.");
		
		while(peopleNow != peopleMax){
			Socket sock = ServerSocket.accept();
			in[peopleNow] = new DataInputStream(sock.getInputStream());
			out[peopleNow] = new DataOutputStream(sock.getOutputStream());
			messagePack[peopleNow] = new MessagePack();
			System.out.println("New client (" + (peopleNow+1) + "/" + peopleMax + ")");
			serverThread[peopleNow] = new ServerNetThread(this, peopleNow);
			serverSend[peopleNow] = new ServerSend(this, peopleNow);
			this.peopleNow++;	
		}
		ServerSocket.close();
		
		System.out.println("All users connected.");
		
		analysisTraffic();
	}
	
	public void genTank(){
		//Парсинг файла с картой
		String pathFull;
		File[] fList = new File(PATH_MAP).listFiles();
		int fListNum;
		Vector<Integer> vecX = new Vector<Integer>();
		Vector<Integer> vecY = new Vector<Integer>();
		Vector<String> vecSprite = new Vector<String>();
		while (true) {
			fListNum = (int) Math.round(Math.random()*(fList.length-1));
			pathFull = PATH_MAP + "/" + fList[fListNum].getName().substring(0,fList[fListNum].getName().lastIndexOf('.')) + ".map";
			if (new File(pathFull).exists()){
				System.out.println("Map: " + pathFull);
				try {
					BufferedReader fileReader = new BufferedReader(new FileReader(pathFull));
					String s;
					s = fileReader.readLine();
					this.widthMap = Integer.parseInt(Global.linkCS.parsString(s,1));
					this.heightMap = Integer.parseInt(Global.linkCS.parsString(s,2));

					while (true){ 
						s = fileReader.readLine();
						if (s == null){
							break;
						}
						
						vecX.add(Integer.parseInt(Global.linkCS.parsString(s,1)));
						vecY.add(Integer.parseInt(Global.linkCS.parsString(s,2)));
						vecSprite.add(Global.linkCS.parsString(s,4));
					}
					
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			} 
		}
		this.pathFull = pathFull;
		
		//генерация танков
		int wTank = Global.c_default.getWidth(0);
		int hTank = Global.c_default.getHeight(0);
		double disTank = Math.sqrt(wTank*wTank + hTank*hTank)/2;
		boolean gen;
		int xRand,yRand,x,y,w,h;
		double disHome,disPointToHome,dxRand,dyRand;
		for(int j=0;j<this.peopleMax;j++){
			do{
				gen = false;
				dxRand = Math.random()*(widthMap-200)+100;//Если генерить сразу в инт - ошибка
				dyRand = Math.random()*(heightMap-200)+100;//позиция танка
				xRand = (int) dxRand;
				yRand = (int) dyRand;
				for(int i=0;i<vecX.size();i++){
					x = (int) vecX.get(i);//коры объекта
					y = (int) vecY.get(i);
					w = (int) Global.linkCS.getSprite((String) vecSprite.get(i)).getWidth();//размеры объекта
					h = (int) Global.linkCS.getSprite((String) vecSprite.get(i)).getHeight();
					disHome = Math.sqrt(w*w + h*h)/2;
					disPointToHome = Math.sqrt((x-xRand)*(x-xRand)+(y-yRand)*(y-yRand));
					if ((disHome+disTank+30) > (disPointToHome)){
						gen = true;
					}
				}
			} while(gen);
			this.tankX[j] = xRand;
			this.tankY[j] = yRand;
			vecX.add(xRand);
			vecY.add(yRand);
			vecSprite.add("player_color");
		}
		tankGenComplite = true;
	}
	
	public void analysisTraffic(){
		long t = System.currentTimeMillis();
		
		while (true){
			if (System.currentTimeMillis() > t+1000){
				t = System.currentTimeMillis();
				for (int i = 0; i < peopleMax; i++){
					System.out.print("ID:" + serverSend[i].id + " MPS:" + serverSend[i].numberSend + "     ");
					serverSend[i].numberSend = 0;
				}
				System.out.println();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
		
	}
	
	public synchronized void checkMapDownload(){
		if (cml == null){
			cml = new CheckMapLoad(this);
		}
	}
	
	public void error(String s){
		System.out.println("[ERROR] " + s);
	}
	
	public static void main (String args[]) throws IOException{
		new GameServer();
	}
}

