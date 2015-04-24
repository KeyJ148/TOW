package main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import main.net.LinkCS;
import main.net.MessagePack;
import main.net.ServerNetThread;

public class GameServer {
	
	public final String PATH_MAP = "map";
	public final String PATH_IMAGE = "image";  
	
	public DataOutputStream[] out;
	public DataInputStream[] in;
	public ServerNetThread[] serverThread;
	public InetAddress[] inetAdr;
	public MessagePack[] messagePack;
	public int peopleMax;
	public int peopleNow;
	public int port;
	public int[] tankX;
	public int[] tankY;
	public boolean genTank = false;//закончена ли генерация танков?
	public String pathFull; //путь к карте
	public int widthMap;//размеры карты
	public int heightMap;
	public boolean[] connect;//подключение игроков
	public int disconnect;
	
	public GameServer() throws IOException{
		BufferedReader bReader = new BufferedReader (new InputStreamReader(System.in));
		int port;
		int peopleMax;
		String str;
		
		Global.linkCS = new LinkCS();
		Global.linkCS.initSprite();
		
		System.out.println("Port (Default 25566): ");
		str = bReader.readLine();
		if (str.equals("")){
			port = 25566;
		} else {
			port = Integer.parseInt(str);
		}
		this.port = port;
		
		System.out.println("Max people (Default 1): ");
		str = bReader.readLine();
		if (str.equals("")){
			peopleMax = 1;
		} else {
			peopleMax = Integer.parseInt(str);
		}
		this.peopleMax = peopleMax;
		
		this.out = new DataOutputStream[peopleMax];
		this.in = new DataInputStream[peopleMax];
		this.serverThread = new ServerNetThread[peopleMax];
		this.inetAdr = new InetAddress[peopleMax];
		this.messagePack = new MessagePack[peopleMax];
		this.tankX = new int[peopleMax];
		this.tankY = new int[peopleMax];
		this.connect = new boolean[peopleMax];
		this.disconnect = 0;
		for(int i=0;i<peopleMax;i++){
			connect[i] = false;
		}
		ServerSocket ServerSocket = new ServerSocket(port);
		this.peopleNow = 0;
		
		genTank();
		System.out.println("Server started.");
		
		while(peopleNow != peopleMax){
			Socket sock = ServerSocket.accept();
			inetAdr[peopleNow] = sock.getInetAddress();
			out[peopleNow] = new DataOutputStream(sock.getOutputStream());
			in[peopleNow] = new DataInputStream(sock.getInputStream());
			messagePack[peopleNow] = new MessagePack();
			System.out.println("New client.");
			serverThread[peopleNow] = new ServerNetThread(this, peopleNow, peopleMax);
			this.peopleNow++;	
		}
		ServerSocket.close();
		
		System.out.println("All users connected.");
		
		boolean mapDownAll;//все ли скачали карту
		do{
			mapDownAll = true;
			for(int i=0;i<peopleMax;i++){
				if (connect[i] == false){
					mapDownAll = false;
				}
			}
		}while(!mapDownAll);
		
		System.out.println("All users map download.");
		
		for(int i=0;i<peopleMax;i++){
			serverThread[i].mapDownAll = true;
		}
		
		System.out.println("All users game start.");
		
		conMessSend();	
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
	}
	
	public void conMessSend(){
		String str;
		try{
			while (true){
				for (int i=0; i<peopleMax;i++){
					if (messagePack[i].haveMessage()){
						str = (String) messagePack[i].get();
						for(int j=0;j<peopleMax;j++){
							if (j != i){
								out[j].writeUTF(str);
							}
						}
					}
				}
			}
		} catch (IOException e){
			System.out.println("Error send message!");
		}
	}
	
	public static void main (String args[]) throws IOException{
		new GameServer();
	}
}

