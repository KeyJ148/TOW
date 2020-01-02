package tow.engine;

import tow.engine.cycle.Engine;
import tow.engine.io.logger.AggregateLogger;
import tow.engine2.implementation.*;
import tow.engine.io.mouse.MouseHandler;
import tow.engine.io.keyboard.KeyboardHandler;
import tow.engine2.map.Room;
import tow.engine2.net.client.Ping;
import tow.engine2.net.client.tcp.TCPControl;
import tow.engine2.net.client.tcp.TCPRead;
import tow.engine2.net.client.udp.UDPControl;
import tow.engine2.net.client.udp.UDPRead;

public class Global {

	public static Engine engine; //Главный игровой поток
	public static Room room; //Текущая комната

	public static AggregateLogger logger; //Объект для вывода лога в консоль и файл
	public static MouseHandler mouse; //Объект хранящий события мыши и рисующий курсор на экране
	public static KeyboardHandler keyboard; //Объект хранящий события клавитуры

	//TODO: убрать в главный класс Network при рефакторинге сети
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
}

