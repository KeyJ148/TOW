package cc.abro.orchengine.net.server;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.exceptions.EngineException;
import cc.abro.orchengine.init.interfaces.NetServerReadInterface;
import cc.abro.orchengine.init.interfaces.ServerInterface;
import cc.abro.orchengine.net.server.readers.ServerReadUDP;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.ServerSocket;

@Log4j2
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

    public static void initSettings(String args[], ServerInterface server, NetServerReadInterface inetServerRead) {
        Thread.currentThread().setName("Server");
        GameServer.server = server;
        GameServer.inetServerRead = inetServerRead;

        try {
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
        } catch (IOException e) {
            log.error("Failed io text");
            throw new EngineException(e);
        }
    }

    public static void waitConnect() {
        try {
            connects = new Connect[peopleMax]; //Выделение места под массив подключений всех игроков

            ServerSocket serverSocketTCP = new ServerSocket(port);
            socketUDP = new DatagramSocket(port);
            /*socketUDP.setSendBufferSize(SettingsStorage.NETWORK.SEND_BUF_SIZE);
            socketUDP.setReceiveBufferSize(SettingsStorage.NETWORK.RECEIVE_BUF_SIZE);
            socketUDP.setTrafficClass(SettingsStorage.NETWORK.TRAFFIC_CLASS);*/
            socketUDP.setSendBufferSize(4096);
            socketUDP.setReceiveBufferSize(4096);
            socketUDP.setTrafficClass(24);

            server.init();

            peopleNow = 0;

            log.info("Server started");

            while (peopleNow != peopleMax) {
                connects[peopleNow] = ConnectFactory.createConnect(serverSocketTCP, socketUDP, peopleNow);
                peopleNow++;

                log.info("New client (" + peopleNow + "/" + peopleMax + ")");
            }
            serverSocketTCP.close();

            log.info("All users connected");

            //Запуск всех основных потоков
            for (Connect connect : connects) {
                connect.serverReadTCP.start(); //Старт потока считывания данных по TCP
            }
            new ServerReadUDP().start();//Старт потока считывания данных по UDP
            new AnalyzerThread().start();//Старт отдельного потока для анализатора
            processingData();//Старт бессконечно цикла с обработкой данных
        } catch (IOException e) {
            e.printStackTrace();
            log.fatal("Failed server start");
            throw new EngineException(e);
        }
    }

    public static void processingData() {
        server.startProcessingData();

        long lastUpdate = System.nanoTime();//Для update
        long startUpdate;
        while (disconnect != Math.max(peopleMax - 1, 1)) {
            startUpdate = System.nanoTime();
            server.update(System.nanoTime() - lastUpdate);
            lastUpdate = startUpdate; //Начало предыдущего update, чтобы длительность update тоже учитывалась

            for (int i = 0; i < GameServer.peopleMax; i++) {
                MessagePack.Message message = null;

                if (GameServer.connects[i].messagePack.haveMessage()) {//Если у игрока имеются сообщения
                    message = GameServer.connects[i].messagePack.get();//Читаем сообщение
                }

                if (message != null) {
                    if (message.inetType == MessagePack.Message.InetType.TCP) inetServerRead.readTCP(message);
                    if (message.inetType == MessagePack.Message.InetType.UDP) inetServerRead.readUDP(message);
                }
            }

            if (!GameServer.maxPower) try {
                Thread.sleep(0, 1);
            } catch (InterruptedException e) {
            }
        }

        log.info("All user disconnect!");
        Context.getService(Engine.class).stop();
    }

}

