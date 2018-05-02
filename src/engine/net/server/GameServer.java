package engine.net.server;

import engine.Loader;
import engine.io.Logger;
import engine.net.server.readers.ServerReadUDP;
import engine.setting.SettingStorage;
import game.server.NetServerRead;
import game.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class GameServer {
	//Присоединение клиентов
	public static int port;
	public static int peopleMax;
	public static int peopleNow;
	public static int disconnect = 0; //Не совмещать с peopleNow
	public static boolean maxPower;//Максимальная производительность и потребление

	//Все данные о подключение конкретного клиента
	public static Connect[] connects;

	//Сокет для работы по UDP
	public static DatagramSocket socketUDP;

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
				str = bReader.readLine().toLowerCase();
				maxPower = (str.equals("t") || str.equals("true"));
			}
		} catch (IOException e){
			Logger.println("Failed io text", Logger.Type.ERROR);
			Loader.exit();
		}
	}

	public static void waitConnect(){
		try {
			connects = new Connect[peopleMax]; //Выделение места под массив подключений всех игроков
			SettingStorage.init();

			ServerSocket serverSocketTCP = new ServerSocket(port);
			socketUDP = new DatagramSocket(port);
			socketUDP.setSendBufferSize(SettingStorage.Net.SEND_BUF_SIZE);
			socketUDP.setReceiveBufferSize(SettingStorage.Net.RECEIVE_BUF_SIZE);
			socketUDP.setTrafficClass(SettingStorage.Net.TRAFFIC_CLASS);

			server.init();

			peopleNow = 0;

			Logger.println("Server started", Logger.Type.SERVER_INFO);

			while (peopleNow != peopleMax) {
				connects[peopleNow] = ConnectFactory.createConnect(serverSocketTCP, socketUDP, peopleNow);
				peopleNow++;

				Logger.println("New client (" + peopleNow + "/" + peopleMax + ")", Logger.Type.SERVER_INFO);
			}
			serverSocketTCP.close();

			Logger.println("All users connected", Logger.Type.SERVER_INFO);

			//Запуск всех основных потоков
			for (Connect connect : connects) {
				connect.serverReadTCP.start(); //Старт потока считывания данных по TCP
			}
			new ServerReadUDP().start();//Старт потока считывания данных по UDP
			new AnalyzerThread().start();//Старт отдельного потока для анализатора
			processingData();//Старт бессконечно цикла с обработкой данных
		} catch (IOException e){
			e.printStackTrace();
            Logger.println("Failed server start", Logger.Type.SERVER_ERROR);
			Loader.exit();
		}
	}

	public static void processingData(){
		NetServerRead inetServerRead = new NetServerRead();
		server.startProcessingData();

		long lastUpdate = System.nanoTime();//Для update
		long startUpdate;
		while (disconnect != peopleMax) {
            startUpdate = System.nanoTime();
			server.update(System.nanoTime() - lastUpdate);
			lastUpdate = startUpdate; //Начало предыдущего update, чтобы длительность update тоже учитывалась

			for (int i = 0; i< GameServer.peopleMax; i++){
				MessagePack.Message message = null;

				synchronized(GameServer.connects[i].messagePack) {//Защита от одновременной работы с массивом
					if (GameServer.connects[i].messagePack.haveMessage()){//Если у игрока имеются сообщения
						message = GameServer.connects[i].messagePack.get();//Читаем сообщение
					}
				}

				if (message != null) {
					if (message.inetType == MessagePack.Message.InetType.TCP) inetServerRead.readTCP(message);
					if (message.inetType == MessagePack.Message.InetType.UDP) inetServerRead.readUDP(message);
				}
			}

			if (!GameServer.maxPower) try {Thread.sleep(0,1);} catch (InterruptedException e) {}
		}

		Logger.println("All user disconnect!", Logger.Type.SERVER_INFO);
		Loader.exit();
	}
	
	public static void main(String args[]){
		initSettings(args);
		waitConnect();
	}

}

