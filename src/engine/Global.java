package engine;

import engine.cycle.Engine;
import engine.inf.InfMain;
import engine.map.Room;
import engine.net.client.Ping;
import engine.net.client.TCPControl;
import engine.net.client.TCPRead;
import engine.setting.SettingStorage;
import game.client.Game;

public class Global {
	
	public static Engine engine; //Главный игровой поток
	public static Game game; //Главный объект игры вне движка
	public static Room room; //Текущая комната

	public static InfMain infMain; //Главный класс интерфейса
	public static SettingStorage setting;//Объект хранящий основный настройки

	public static TCPRead tcpRead; //Цикл считывания данных с сервера
	public static TCPControl tcpControl; //Хранит настройки и работает с сетью
	public static Ping pingCheck;//Объект для проверки пинга

}

