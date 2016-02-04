package tow.server;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import tow.Global;
import tow.lobby.LobbyWindow;
import tow.setting.SettingStorage;

public class GameServer {
	
	public final String PATH_MAP = "res/map";
	public final String PATH_IMAGE = "res/image";  

	//Присоединение клиентов
	public int port;
	public int peopleMax;
	public int peopleNow;
	public int disconnect;//Кол-во отключённых игроков, не совмещать с peopleNow
	public boolean maxPower;//Максимальная производительность и потребление
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
	//Загрузка объектов карты
	ArrayList<Integer> vecX = new ArrayList<Integer>();
	ArrayList<Integer> vecY = new ArrayList<Integer>();
	ArrayList<String> vecSprite = new ArrayList<String>();
	//Генерация танков
	public volatile boolean tankGenComplite = false;
	public int[] tankX;//Координаты танков игроков
	public int[] tankY;
	//Проверка загрузки карты
	public volatile boolean[] connect;//Метка, устанавливаемая клиентским потоком, о том что игрок скачал карту
	public CheckMapLoad cml;//Объект-поток для проверки загрузки карты всеми игроками (Проверка меток)
	//Связь с клиентом
	public static LobbyWindow lobbyWindow;//Объект клиента, которому нужно сообщить о начале коннекта
	public static boolean isClient = false;
	
	public GameServer(String args[]) throws IOException{
		BufferedReader bReader = new BufferedReader (new InputStreamReader(System.in));
		String str;
		
		Global.setting = new SettingStorage();
		Global.setting.init();
		Global.initSprite();
		if (args.length > 0){
			port = Integer.parseInt(args[0]);
		} else {
			System.out.print("Port (Default 25566): ");
			str = bReader.readLine();
			if (str.equals("")){
				port = 25566;
			} else {
				port = Integer.parseInt(str);
			}
		}
		if (args.length > 1){
			peopleMax = Integer.parseInt(args[1]);
		} else {
			System.out.print("Max people (Default 1): ");
			str = bReader.readLine();
			if (str.equals("")){
				peopleMax = 1;
			} else {
				peopleMax = Integer.parseInt(str);
			}
		}
		if (args.length > 2){
			maxPower = Boolean.valueOf(args[2]);
		} else {
			System.out.print("Max power (Default false): ");
			str = bReader.readLine();
			if (str.equals("t") || str.equals("T") || str.equals("True") || str.equals("true")){
				maxPower = true;
			} else {
				maxPower = false;
			}
		}
		
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
		
		if (isClient){
			lobbyWindow.connect();
		}
		GameServer.p("Server started.");
		
		while(peopleNow != peopleMax){
			Socket sock = ServerSocket.accept();
			sock.setTcpNoDelay(Global.setting.TCP_NODELAY);
			sock.setKeepAlive(Global.setting.KEEP_ALIVE);
			sock.setSendBufferSize(Global.setting.SEND_BUF_SIZE);
			sock.setReceiveBufferSize(Global.setting.RECEIVE_BUF_SIZE);
			sock.setPerformancePreferences(Global.setting.PREFERENCE_CON_TIME, Global.setting.PREFERENCE_LATENCY, Global.setting.PREFERENCE_BANDWIDTH);
			sock.setTrafficClass(Global.setting.TRAFFIC_CLASS);
			
			in[peopleNow] = new DataInputStream(sock.getInputStream());
			out[peopleNow] = new DataOutputStream(sock.getOutputStream());
			messagePack[peopleNow] = new MessagePack(peopleNow);
			GameServer.p("New client (" + (peopleNow+1) + "/" + peopleMax + ")");
			serverThread[peopleNow] = new ServerNetThread(this, peopleNow);
			serverSend[peopleNow] = new ServerSend(this, peopleNow);
			this.peopleNow++;	
		}
		ServerSocket.close();
		
		GameServer.p("All users connected.");
		
		mainThread();
	}
	
	public void genTank(){
		//Парсинг файла с картой
		String pathFull;
		File[] fList = new File(PATH_MAP).listFiles();
		int fListNum;
		vecX.clear();
		vecY.clear();
		vecSprite.clear();
		while (true) {
			fListNum = (int) Math.round(Math.random()*(fList.length-1));
			pathFull = PATH_MAP + "/" + fList[fListNum].getName().substring(0,fList[fListNum].getName().lastIndexOf('.')) + ".map";
			if (new File(pathFull).exists()){
				GameServer.p("Map: " + pathFull);
				try {
					BufferedReader fileReader = new BufferedReader(new FileReader(pathFull));
					String s;
					s = fileReader.readLine();
					this.widthMap = Integer.parseInt(Global.parsString(s,1));
					this.heightMap = Integer.parseInt(Global.parsString(s,2));

					while (true){ 
						s = fileReader.readLine();
						if (s == null){
							break;
						}
						
						vecX.add(Integer.parseInt(Global.parsString(s,1)));
						vecY.add(Integer.parseInt(Global.parsString(s,2)));
						vecSprite.add(Global.parsString(s,4));
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
		for(int i=0;i<this.peopleMax;i++){
			Point p = genObject(wTank, hTank);
			int x = (int) p.getX();
			int y = (int) p.getY();
			this.tankX[i] = x;
			this.tankY[i] = y;
			vecX.add(x);
			vecY.add(y);
			vecSprite.add("player_color");
		}
		tankGenComplite = true;
	}
	
	public Point genObject(int width, int height){
		double disTank = Math.sqrt(width*width + width*width)/2;
		boolean gen;
		int xRand,yRand,x,y,w,h;
		double disHome,disPointToHome,dxRand,dyRand;
		do{
			gen = false;
			dxRand = Math.random()*(widthMap-200)+100;//Если генерить сразу в инт - ошибка
			dyRand = Math.random()*(heightMap-200)+100;//позиция танка
			xRand = (int) dxRand;
			yRand = (int) dyRand;
			for(int i=0;i<vecX.size();i++){
				x = (int) vecX.get(i);//коры объекта
				y = (int) vecY.get(i);
				w = (int) Global.getSprite((String) vecSprite.get(i)).getWidth();//размеры объекта
				h = (int) Global.getSprite((String) vecSprite.get(i)).getHeight();
				disHome = Math.sqrt(w*w + h*h)/2;
				disPointToHome = Math.sqrt((x-xRand)*(x-xRand)+(y-yRand)*(y-yRand));
				if ((disHome+disTank+30) > (disPointToHome)){
					gen = true;
				}
			}
		} while(gen);
		return new Point(xRand, yRand);
	}
	
	public void mainThread(){
		long timeAnalysis = System.currentTimeMillis();//Время анализа MPS
		int timeAnalysisDelta = 1000;
		
		long timeBox = System.currentTimeMillis();//Время создания ящика
		int timeBoxDelta = 5000;
		int idBox = 0;
		
		while (true){
			if ((!isClient) && (System.currentTimeMillis() > timeAnalysis+timeAnalysisDelta)){//Анализ MPS
				timeAnalysis = System.currentTimeMillis();
				for (int i = 0; i < peopleMax; i++){
					System.out.print("ID:" + serverSend[i].id + " MPS:" + serverSend[i].numberSend + "     ");
					serverSend[i].numberSend = 0;
				}
				System.out.println();
			}
			
			if (System.currentTimeMillis() > timeBox+timeBoxDelta){//Созданиен ящиков
				timeBox = System.currentTimeMillis();
				createBox(idBox);
				idBox++;
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
		
	}
	
	public void createBox(int idBox) {
		for (int i = 0; i < vecSprite.size(); i++) {// Удаляем имеющиеся танки
			if (vecSprite.get(i).equals("player_color")) {
				vecSprite.remove(i);
				vecX.remove(i);
				vecY.remove(i);
			}
		}

		for (int i = 0; i < peopleMax; i++) {
			vecX.add(serverSend[i].x);
			vecY.add(serverSend[i].y);
			vecSprite.add("player_color");
		}
		
		int w = Global.box_armor.getWidth();
		int h = Global.box_armor.getHeight();
		Point p = genObject(w,h);
		
		Random rand = new Random();
		int typeBox = rand.nextInt(4);
		
		for (int i = 0; i < peopleMax; i++) {
			synchronized(out[i]) {//Защита от одновременной работы с массивом
				try {
					out[i].writeUTF("12 " + Math.round(p.getX()) + " " + Math.round(p.getY()) + " " + idBox + " " + typeBox);
				} catch (IOException e) {
					error("Send create box");
				}
			}
		}
	}
	
	public synchronized void checkMapDownload(){
		if (cml == null){
			cml = new CheckMapLoad(this);
		}
	}
	
	public static void error(String s){
		if (isClient){
			System.out.println("[SERVER][ERROR] " + s);
		} else {
			System.out.println("[ERROR] " + s);
		}
	}
	
	public static void p(String s){
		if (isClient){
			System.out.println("[SERVER] " + s);
		} else {
			System.out.println(s);
		}
	}
	
	public static void main(String args[]){
		try {
			new GameServer(args);
		} catch (IOException e) {
			Global.error("Start server failed");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void fromClient(String[] args, LobbyWindow lobbyWindow){
		GameServer.lobbyWindow  = lobbyWindow;
		GameServer.isClient = true;
		main(args);
	}
}

