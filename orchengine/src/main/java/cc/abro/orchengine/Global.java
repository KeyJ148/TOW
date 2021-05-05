package cc.abro.orchengine;

import cc.abro.orchengine.audio.AudioPlayer;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.gui.CachedGuiPanelStorage;
import cc.abro.orchengine.implementation.GameInterface;
import cc.abro.orchengine.implementation.NetGameReadInterface;
import cc.abro.orchengine.implementation.NetServerReadInterface;
import cc.abro.orchengine.implementation.ServerInterface;
import cc.abro.orchengine.logger.AggregateLogger;
import cc.abro.orchengine.map.Location;
import cc.abro.orchengine.net.client.Ping;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.net.client.tcp.TCPRead;
import cc.abro.orchengine.net.client.udp.UDPControl;
import cc.abro.orchengine.net.client.udp.UDPRead;
import cc.abro.orchengine.resources.animations.AnimationStorage;
import cc.abro.orchengine.resources.audios.AudioStorage;
import cc.abro.orchengine.resources.sprites.SpriteStorage;

public class Global {

	public static Engine engine; //Главный игровой поток
	public static Location location; //Текущая комната

	public static AggregateLogger logger; //Объект для вывода лога в консоль и файл

	public static AudioPlayer audioPlayer; //Объект, воспроизводящий музыку и хранящий источники музыки

	public static AudioStorage audioStorage; //Объект хранящий звуки (буфферы OpenAL)
	public static SpriteStorage spriteStorage; //Объект хранящий спрайты
	public static AnimationStorage animationStorage; //Объект хранящий анимации
	public static CachedGuiPanelStorage cachedGuiPanelStorage;

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
}

