package tow.engine2;

import tow.engine.cycle.Engine;
import tow.engine2.implementation.*;
import tow.engine.map.Room;
import tow.engine.net.client.Ping;
import tow.engine.net.client.tcp.TCPControl;
import tow.engine.net.client.tcp.TCPRead;
import tow.engine.net.client.udp.UDPControl;
import tow.engine.net.client.udp.UDPRead;

import java.io.File;

public class Global {

	public static long window; //ID окна игры для LWJGL
	public static Engine engine; //Главный игровой поток
	public static Room room; //Текущая комната

	public static TCPControl tcpControl; //Хранит настройки и работает с сетью по TCP протоколу
	public static TCPRead tcpRead; //Цикл считывания данных с сервера по TCP протоколу
	public static UDPControl udpControl; //Хранит настройки и работает с сетью по UDP протоколу
	public static UDPRead udpRead; //Цикл считывания данных с сервера по UDP протоколу
	public static Ping pingCheck;//Объект для проверки пинга

	/* Объекты реализуемые вне движка и передаваемые при старте */
	public static GameInterface game; //Главный объект игры
	public static ServerInterface server; //Главный объект сервера
	public static NetGameReadInterface netGameRead; //Объект для обработки сетевых сообщений на клиенте
	public static NetServerReadInterface netServerRead; //Объект для обработки сетевых сообщений на сервере
	public static StorageInterface storage; //Объект для хранения описания картинок, анимаций и звуков

	//Костыль, разбить на 2 функции, вынести в отдельный класс, не возвращать файл, а только стрим
	public static File getFile(String path){
		try {
			return new File(Thread.currentThread().getContextClassLoader().getResource(path).getFile());
		} catch (NullPointerException e){
			return new File("*");
		}
	}

}

