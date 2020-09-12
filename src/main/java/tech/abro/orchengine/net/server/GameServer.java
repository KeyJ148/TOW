package tech.abro.orchengine.net.server;

import tech.abro.orchengine.Global;
import tech.abro.orchengine.Loader;
import tech.abro.orchengine.implementation.NetServerReadInterface;
import tech.abro.orchengine.implementation.ServerInterface;
import tech.abro.orchengine.logger.Logger;
import tech.abro.orchengine.net.server.readers.ServerReadUDP;
import tech.abro.orchengine.resources.settings.SettingsStorage;
import tech.abro.orchengine.resources.settings.SettingsStorageHandler;

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
	public static ServerInterface server;
	public static NetServerReadInterface inetServerRead;

	public static void initSettings(String args[], ServerInterface server, NetServerReadInterface inetServerRead){
		GameServer.server = server;
		GameServer.inetServerRead = inetServerRead;

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
			Global.logger.println("Failed io text", Logger.Type.SERVER_ERROR);
			Loader.exit();
		}
	}

	public static void waitConnect(){
		try {
			connects = new Connect[peopleMax]; //Выделение места под массив подключений всех игроков
			try {
				SettingsStorageHandler.init();
			} catch (IOException e){
				e.printStackTrace();
				System.exit(0);
			}

			ServerSocket serverSocketTCP = new ServerSocket(port);
			socketUDP = new DatagramSocket(port);
			socketUDP.setSendBufferSize(SettingsStorage.NETWORK.SEND_BUF_SIZE);
			socketUDP.setReceiveBufferSize(SettingsStorage.NETWORK.RECEIVE_BUF_SIZE);
			socketUDP.setTrafficClass(SettingsStorage.NETWORK.TRAFFIC_CLASS);

			server.init();

			peopleNow = 0;

			Global.logger.println("Server started", Logger.Type.SERVER_INFO);

			while (peopleNow != peopleMax) {
				connects[peopleNow] = ConnectFactory.createConnect(serverSocketTCP, socketUDP, peopleNow);
				peopleNow++;

				Global.logger.println("New client (" + peopleNow + "/" + peopleMax + ")", Logger.Type.SERVER_INFO);
			}
			serverSocketTCP.close();

			Global.logger.println("All users connected", Logger.Type.SERVER_INFO);

			//Запуск всех основных потоков
			for (Connect connect : connects) {
				connect.serverReadTCP.start(); //Старт потока считывания данных по TCP
			}
			new ServerReadUDP().start();//Старт потока считывания данных по UDP
			new AnalyzerThread().start();//Старт отдельного потока для анализатора
			processingData();//Старт бессконечно цикла с обработкой данных
		} catch (IOException e){
			e.printStackTrace();
            Global.logger.println("Failed server start", Logger.Type.SERVER_ERROR);
			Loader.exit();
		}
	}

	public static void processingData(){
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

		Global.logger.println("All user disconnect!", Logger.Type.SERVER_INFO);
		Loader.exit();
	}

}

