package engine.net.server;

import engine.io.Logger;
import engine.setting.SettingStorage;
import game.server.Server;
import game.server.TCPServerRead;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
	//Присоединение клиентов
	public static int port;
	public static int peopleMax;
	public static int peopleNow;
	public static int disconnect = 0; //Не совмещать с peopleNow
	public static boolean maxPower;//Максимальная производительность и потребление
	//Сокеты
	public static DataInputStream[] in;
	public static DataOutputStream[] out;
	//Поток для приёма сообщений
	public static ServerRead[] serverRead;
	//Хранение принятых сообщений, очередь на обработку
	public static volatile MessagePack[] messagePack;
	//Кол-во отправленных сообщений клиенту
	public static int[] numberSend;
	//Объект сервера для реализации в игре
	public static Server server = new Server();

	public static void initSettings(String args[]){
		Logger.enable(Logger.Type.SERVER_INFO);
		Logger.enable(Logger.Type.SERVER_ERROR);
		Logger.enable(Logger.Type.MPS);

		try{
			BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
			String str;

			if (args.length > 0) {
				port = Integer.parseInt(args[0]);
			} else {
				System.out.print("Port (Default 25566): ");
				str = bReader.readLine();
				if (str.equals("")) {
					port = 25566;
				} else {
					port = Integer.parseInt(str);
				}
			}

			if (args.length > 1) {
				peopleMax = Integer.parseInt(args[1]);
			} else {
				System.out.print("Max people (Default 1): ");
				str = bReader.readLine();
				if (str.equals("")) {
					peopleMax = 1;
				} else {
					peopleMax = Integer.parseInt(str);
				}
			}

			if (args.length > 2) {
				maxPower = Boolean.valueOf(args[2]);
			} else {
				System.out.print("Max power (Default false): ");
				str = bReader.readLine();
				if (str.equals("t") || str.equals("T") || str.equals("True") || str.equals("true")) {
					maxPower = true;
				} else {
					maxPower = false;
				}
			}
		} catch (IOException e){
			Logger.println("Failed io text", Logger.Type.ERROR);
			System.exit(0);
		}
	}

	public static void waitConnect(){
		try {
			in = new DataInputStream[peopleMax];
			out = new DataOutputStream[peopleMax];
			serverRead = new ServerRead[peopleMax];
			messagePack = new MessagePack[peopleMax];
			numberSend = new int[peopleMax];

			SettingStorage setting = new SettingStorage();
			setting.init();

			ServerSocket ServerSocket = new ServerSocket(port);
			server.init();

			peopleNow = 0;

			Logger.println("Server started", Logger.Type.SERVER_INFO);

			while (peopleNow != peopleMax) {
				Socket sock = ServerSocket.accept();
				sock.setTcpNoDelay(setting.TCP_NODELAY);
				sock.setKeepAlive(setting.KEEP_ALIVE);
				sock.setSendBufferSize(setting.SEND_BUF_SIZE);
				sock.setReceiveBufferSize(setting.RECEIVE_BUF_SIZE);
				sock.setPerformancePreferences(setting.PREFERENCE_CON_TIME, setting.PREFERENCE_LATENCY, setting.PREFERENCE_BANDWIDTH);
				sock.setTrafficClass(setting.TRAFFIC_CLASS);

				in[peopleNow] = new DataInputStream(sock.getInputStream());
				out[peopleNow] = new DataOutputStream(sock.getOutputStream());
				messagePack[peopleNow] = new MessagePack(peopleNow);
				Logger.println("New client (" + (peopleNow + 1) + "/" + peopleMax + ")", Logger.Type.SERVER_INFO);
				serverRead[peopleNow] = new ServerRead(peopleNow);
				peopleNow++;
			}
			ServerSocket.close();

			Logger.println("All users connected", Logger.Type.SERVER_INFO);

			new AnalyzerThread().start();//Старт отдельного потока для анализатора
			processingData();//Старт бессконечно цикла с обработкой данных
		} catch (IOException e){
            Logger.println("Failed server start", Logger.Type.SERVER_ERROR);
			System.exit(0);
		}
	}

	public static void processingData(){
		TCPServerRead tcpServerRead = new TCPServerRead();
		server.startProcessingData();

		long lastUpdate = System.nanoTime();//Для update
		long startUpdate;
		while (disconnect != peopleMax) {
            startUpdate = System.nanoTime();
			server.update(System.nanoTime() - lastUpdate);
			lastUpdate = startUpdate; //Начало предыдущего update, чтобы длительность update тоже учитывалась

			for (int i = 0; i< GameServer.peopleMax; i++){
				String str = "";

				synchronized(GameServer.messagePack[i]) {//Защита от одновременной работы с массивом
					if (GameServer.messagePack[i].haveMessage()){//Если у игрока имеются сообщения
						str = GameServer.messagePack[i].get();//Читаем сообщение
					}
				}

				if (str.length() > 0){
					int type = Integer.parseInt(str.split(" ")[0]);
					String mes = str.substring(str.indexOf(" ")+1);
					tcpServerRead.read(i, type, mes);
				}

			}

			if (!GameServer.maxPower) try {Thread.sleep(0,1);} catch (InterruptedException e) {}
		}

		Logger.println("All user disconnect!", Logger.Type.SERVER_INFO);
		System.exit(0);
	}



	public static void send(int id, int type, String str){
		try {
			synchronized (GameServer.out[id]) {
				GameServer.out[id].flush();
				GameServer.out[id].writeUTF(type + " " + str);
			}
			numberSend[id]++; //Кол-во отправленных пакетов
		} catch (IOException e) {
			if (!GameServer.serverRead[id].disconnect) Logger.print("Send message failed", Logger.Type.SERVER_ERROR);
		}
	}

	public static void sendAllExceptId(int id, int type, String str){
		for(int i=0; i<peopleMax; i++){//Отправляем сообщение всем
			if (i != id){//Кроме игрока, приславшего сообщение
				GameServer.send(i, type, str);
			}
		}
	}
	
	public static void sendAll(int type, String str){
		for(int i=0; i<peopleMax; i++){//Отправляем сообщение всем
			GameServer.send(i, type, str);
		}
	}
	
	public static void main(String args[]){
		initSettings(args);
		waitConnect();
	}

}

